package org.firstinspires.ftc.teamcode.subsystems.camera;

import static org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions.disBetweenPoses;

import android.util.Pair;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.ColorResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.misc.InitComponents;
import org.firstinspires.ftc.teamcode.misc.utils.AngleFunctions;
import org.firstinspires.ftc.teamcode.misc.utils.Extras;
import org.firstinspires.ftc.teamcode.misc.utils.GameElement;
import org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.misc.utils.Ray;
import org.firstinspires.ftc.teamcode.misc.utils.filters.PoseLowPass;
import org.firstinspires.ftc.teamcode.misc.utils.Point;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Limelight {
    Limelight3A ll;
    PoseLowPass poseLowPass = new PoseLowPass(0.01, 0.01);
    private List<DetectorResult> detectorResults;
    public Limelight() {
        ll = InitComponents.ll;
    }

    public Pose3D getLatestBotpose() throws NullPointerException{ // LLCords
        LLResult result = ll.getLatestResult();
        if (result != null) {
            if (result.isValid()) {
                poseLowPass.update(PoseFunctions.pose3DToPose2D(result.getBotpose(), AngleUnit.DEGREES));
                return result.getBotpose();
            }
        }
        throw new NullPointerException("No valid apriltag found");
    }
    public void updateDetectorResult() {
        LLResult result = ll.getLatestResult();
        if (result != null && result.isValid()) {
            detectorResults = result.getDetectorResults();
        }
        else{
            detectorResults = null;
        }
    }
    public boolean isDetectorResultGood(){
        return detectorResults != null;
    }

    public Pose2D getFilteredBotPose() throws NullPointerException{
        getLatestBotpose();
        return poseLowPass.get();
    }


    private Pose2D getPose2DFromXYAngle(double xDeg, double yDeg, GameElement element){ // the angle x and y to the target, x and y angle in deg
        double llForwardOffset = 17;// cm from lens to middle of robot
        double llHeight = 20; // cm
        double llRight = -11;
        double artifactHeight = element.getUnit().toCm(element.getRadiusInches()); // approximately in cm
        double heightDiff = llHeight - artifactHeight;

        double xRad = Math.toRadians(xDeg);
        double yRad = Math.toRadians(yDeg);

        double groundDis = heightDiff / Math.tan(yRad); // from cam to target
        double actualY = Math.cos(xRad) * groundDis;
        double actualX = Math.sin(xRad) * groundDis;
        return new Pose2D(DistanceUnit.CM,actualX-llRight,actualY-llForwardOffset,AngleUnit.DEGREES, xDeg);
    }

    public List<Pose2D> getDetectorResultAsPose2D(){ // relative to robot
        List<Pose2D> XYlist = new ArrayList<>();
        List<GameElement> elements = getDetectorResultElements();
        for(int i=0; i<detectorResults.size(); i++){
            DetectorResult detectorResult = detectorResults.get(i);
            GameElement element = elements.get(i);
            XYlist.add(getPose2DFromXYAngle(detectorResult.getTargetXDegrees(),
                    detectorResult.getTargetYDegrees(), element));
        }
        return XYlist;
    }
    public List<Pose2D> getRotatedDetectorResults(double robotAngle){
        List<Pose2D> rotatedDetections = new ArrayList<>();
        for(Pose2D detectionPose : getDetectorResultAsPose2D()){
            double detectionAngle = detectionPose.getHeading(AngleUnit.DEGREES);
            double absAngle = AngleFunctions.convertToWrapAroundAngle(detectionAngle + robotAngle);
            Pair<Double, Double> rotatedXY = PoseFunctions.rotation2D(detectionPose.getX(DistanceUnit.CM),
                    detectionPose.getY(DistanceUnit.CM), robotAngle);
            rotatedDetections.add(new Pose2D(DistanceUnit.CM, rotatedXY.first, rotatedXY.second, AngleUnit.DEGREES, absAngle));
        }
        return rotatedDetections;
    }

    public List<Pose2D> getAbsoluteDetectorResult(Pose2D robotPose){ // ftc coords
        List<Pose2D> absoluteDetections = new ArrayList<>();
        for(Pose2D pose : getRotatedDetectorResults(robotPose.getHeading(AngleUnit.DEGREES))){
            double absPoseX = -pose.getY(DistanceUnit.CM) + robotPose.getX(DistanceUnit.CM);
            double absPoseY = pose.getX(DistanceUnit.CM) + robotPose.getY(DistanceUnit.CM);
            absoluteDetections.add(new Pose2D(DistanceUnit.CM, absPoseX, absPoseY, AngleUnit.DEGREES, pose.getHeading(AngleUnit.DEGREES)));
        }
        return absoluteDetections;
    }

    public List<GameElement> getDetectorResultElements(){
        List<String> detectionNames = Extras.I_To_T_ForList(detectorResults, DetectorResult::getClassName);
        List<GameElement> detectionElements = Extras.I_To_T_ForList(detectionNames, GameElement::fromString);
        return detectionElements;
    }

    // --------------------------blobs-------------------------------------- old bad code

    @Deprecated
    public List<List<Pose2D>> getBlobs(List<Pose2D> artifactPoses, List<GameElement> gameElements){
        final double MAX_NEIGHBOR_GAP = 3; // not including the radius, cm
        List<List<Pose2D>> blobs = new ArrayList<>();

        for(int i=0; i<artifactPoses.size(); i++){
            List<Pose2D> currBlob = new ArrayList<>();
            Pose2D fromArtifactPose = artifactPoses.get(i);
            GameElement fromGameElement = gameElements.get(i);
            currBlob.add(fromArtifactPose);
            for(int j=0; j<artifactPoses.size(); j++){
                Pose2D toArtifactPose = artifactPoses.get(j);
                GameElement toGameElement = gameElements.get(j);
                double maxNeighborDis = fromGameElement.getRadiusCm()+toGameElement.getRadiusCm()+MAX_NEIGHBOR_GAP;
                if (PoseFunctions.areEqualPoses(toArtifactPose, fromArtifactPose)){
                    continue;
                }
                if(disBetweenPoses(fromArtifactPose, toArtifactPose) < maxNeighborDis){
                    currBlob.add(toArtifactPose);
                }
            }
            blobs.add(currBlob);
        }
        return blobs;
    }

    @Deprecated
    public List<List<Pose2D>> getBiggestBlobs(List<Pose2D> artifactPoses, List<GameElement> gameElements){ // the biggest blobs of artifacts
        List<List<Pose2D>> blobs = getBlobs(artifactPoses, gameElements);
        List<List<Pose2D>> biggestBlobs = new ArrayList<>();
        int artifactsInTheBiggestBlob = 1;

        for(List<Pose2D> blob : blobs){
            if (blob.size() > artifactsInTheBiggestBlob){
                artifactsInTheBiggestBlob = blob.size();
            }
        }
        long minNumOfArtifactsInABlob = Math.round((double)(artifactsInTheBiggestBlob + 1) / 2);
        for(List<Pose2D> blob : blobs){
            if(blob.size() >= minNumOfArtifactsInABlob){
                biggestBlobs.add(blob);
            }
        }
        return biggestBlobs;
    }

    @Deprecated
    public Pose2D getBestPoseInBlob(List<Pose2D> artifactPoses, Pose2D rPose){
        //TODO: make better
        Pose2D bestArtifact = artifactPoses.get(0);
        for(Pose2D artifactPose : artifactPoses){
            if(disBetweenPoses(artifactPose, rPose) > disBetweenPoses(bestArtifact, rPose)){
                bestArtifact = artifactPose;
            }
        }
        return bestArtifact;
    }

    @Deprecated
    public Pose2D getBestPoseForPickup(Pose2D rPose){ // the best end point of a path that intakes the most amount of artifacts
        List<Pose2D> artifactPoses = getAbsoluteDetectorResult(rPose);
        List<GameElement> gameElements = getDetectorResultElements();
        List<List<Pose2D>> biggestBlobs = getBiggestBlobs(artifactPoses, gameElements);
        Pose2D bestTargetPose = getBestPoseInBlob(biggestBlobs.get(0), rPose);

        for(List<Pose2D> blob : biggestBlobs){
            Pose2D currTargetPose = getBestPoseInBlob(blob, rPose);
            if(disBetweenPoses(currTargetPose, rPose) < disBetweenPoses(bestTargetPose, rPose)){
                bestTargetPose = currTargetPose;
            }
        }
        return bestTargetPose;
    }
    // -------------------------rays----------------------------
    public Pair<List<Point>, List<GameElement>> getFilteredDetections(List<Pose2D> detections, List<GameElement> gameElements){
        List<Point> filteredDetections = new ArrayList<>();
        List<GameElement> filteredGameElements = new ArrayList<>();
        List<Point> points = Extras.I_To_T_ForList(detections, PoseFunctions::pose2DToPoint);
        for(int i=0;i<points.size();i++){
            if(points.get(i).inYourHalfOfField()){
                filteredDetections.add(points.get(i));
                filteredGameElements.add(gameElements.get(i));
            }
        }
        return new Pair<>(filteredDetections, filteredGameElements);
    }
    public Ray getRay(Pose2D rPose, double rayAngle){
        final double RAY_WIDTH = 40; // cm
        final double RAY_HEIGHT = 400; // cm
        Pair<List<Point>, List<GameElement>> filteredDetections = getFilteredDetections(
                getAbsoluteDetectorResult(rPose), getDetectorResultElements());
        List<GameElement> gameElements = filteredDetections.second;
        List<Point> elementsPoints = filteredDetections.first;
        Point.sortPointsByDisToPoint(PoseFunctions.pose2DToPoint(rPose), elementsPoints);
        Ray ray = new Ray(new ArrayList<>());
        for(int i=0; i<elementsPoints.size(); i++){
            Point bottomLeftRect = Point.transformPoint(PoseFunctions.pose2DToPoint(rPose), RAY_WIDTH/2, rayAngle);
            if(Point.isPointInRect(elementsPoints.get(i), bottomLeftRect, RAY_WIDTH, RAY_HEIGHT, rayAngle, rPose)){
                if(!GameElement.isGameElementGoodForPickup(gameElements.get(i))){
                    break;
                }
                if(ray.size() == 4){
                    break;
                }
                ray.addElement(new Pair<>(elementsPoints.get(i), gameElements.get(i)));
            }
        }
        return ray;
    }

    public Pair<Ray, Double> getBestRay(Pose2D rPose){
        Ray bestRay = null;
        double bestScore = Integer.MIN_VALUE;
        double bestAngle = Integer.MIN_VALUE;
        for(double i=0;i<360;i += 0.5){
            Ray currRay = getRay(rPose, i);
            if(currRay.size() != 0){
                double currScore = getScore(currRay, currRay.getRayDis(PoseFunctions.pose2DToPoint(rPose)));
                if(currScore > bestScore){
                    bestScore = currScore;
                    bestRay = new Ray(currRay.getRay());
                    bestAngle = i;
                }
            }
        }
        return new Pair<>(bestRay, bestAngle);
    }
    public Point getBestPointForPickUp(Pose2D rPose){
        final double robotLength = 43; // cm
        Pair<Ray, Double> bestRay = getBestRay(rPose);
        System.out.println("best ray: " + bestRay.first.getRay());
        Point bestPoint = Point.transformPoint(bestRay.first.getFurthestPoint(PoseFunctions.pose2DToPoint(rPose)), robotLength/2, bestRay.second);
        return  bestPoint;
    }
    public double getScore(Ray ray, double rayDis){
        final double POLLEN_VALUE = 1;
        final double NECTAR_VALUE = 1.8; // pollen / nectar; (for tilt)
        double score = disToValue(rayDis);
        for(Pair<Point, GameElement> element : ray.getRay()){
            if(element.second == GameElement.POLLEN){
                score += POLLEN_VALUE;
            }
            else{ // the color of nectar doesn't matter because in the ray there should only be the nectar type we can pick up
                score += NECTAR_VALUE;
            }
        }
        return score;
    }

    public double disToValue(double dis){
        return (-0.0065*dis+1);
    }


}
