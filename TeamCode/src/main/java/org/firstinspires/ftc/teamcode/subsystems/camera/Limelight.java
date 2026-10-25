package org.firstinspires.ftc.teamcode.subsystems.camera;

import static org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions.disBetweenPoses;

import android.util.Pair;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.ColorResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.DetectorResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.teamcode.misc.InitComponents;
import org.firstinspires.ftc.teamcode.misc.utils.AngleFunctions;
import org.firstinspires.ftc.teamcode.misc.utils.Extras;
import org.firstinspires.ftc.teamcode.misc.utils.GameElement;
import org.firstinspires.ftc.teamcode.misc.utils.PoseFunctions;
import org.firstinspires.ftc.teamcode.misc.utils.filters.PoseLowPass;

import java.util.ArrayList;
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

    // --------------------------blobs--------------------------------------

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
}
