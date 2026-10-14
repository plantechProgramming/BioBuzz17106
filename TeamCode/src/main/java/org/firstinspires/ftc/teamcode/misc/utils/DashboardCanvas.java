package org.firstinspires.ftc.teamcode.misc.utils;

import static org.firstinspires.ftc.teamcode.misc.utils.Extras.ROBOT_SIZE;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.List;

public class DashboardCanvas {
    private TelemetryPacket telemetryPacket;

    public DashboardCanvas() {
        telemetryPacket = new TelemetryPacket();
    }
    public DashboardCanvas addPreciseRobot(Pose2D pose) {
        double x = pose.getX(DistanceUnit.INCH);
        double y = pose.getY(DistanceUnit.INCH);
        double heading = pose.getHeading(AngleUnit.RADIANS);
        telemetryPacket.fieldOverlay()
                .setStroke("#FF0000")
                .setStrokeWidth(1)
                .setTranslation(x, y)
                .setRotation(heading)
                .strokeRect(-ROBOT_SIZE.second/2, -ROBOT_SIZE.first/2,
                        ROBOT_SIZE.second, ROBOT_SIZE.first);
        return this;
    }
    private static final double ROBOT_RADIUS = Math.min(ROBOT_SIZE.first, ROBOT_SIZE.second)/2; // in inches
    // the robot is represented as a circle and the angle is shown by a line from the center of the robot
    public DashboardCanvas addRobotAsCircle(Pose2D pose){
        return addRobotAsCircle(pose, "#FF0000");
    }
    public DashboardCanvas addRobotAsCircle(Pose2D pose, String color){
        double x = pose.getX(DistanceUnit.INCH);
        double y = pose.getY(DistanceUnit.INCH);
        double heading = pose.getHeading(AngleUnit.RADIANS);

        double endX = ROBOT_RADIUS/1.5*Math.cos(heading)+x; // the 1.5 is to make the line not go all the way
        double endY = ROBOT_RADIUS/1.5*Math.sin(heading)+y; // the 1.5 is to make the line not go all the way

        telemetryPacket.fieldOverlay()
                .setStroke(color)
                .strokeCircle(x, y, ROBOT_RADIUS)
                .strokeLine(x, y, endX, endY);
        return this;
    }

    public DashboardCanvas addDot(Point p, String color, double radius){
        telemetryPacket.fieldOverlay()
                .setStroke(color)
                .fillCircle(p.getX(), p.getY(), radius);
        return this;
    }

    public DashboardCanvas addGameElement(GameElement element, Point point){
        addDot(point, element.getColor().getColorHex(), element.getRadiusInches());
        return this;
    }
    public DashboardCanvas addGameElements(List<GameElement> elements, List<Point> points){
        int smallestListSize = Math.min(elements.size(), points.size());
        for(int i = 0; i < smallestListSize; i++){
            addGameElement(elements.get(i), points.get(i));
        }
        return this;
    }
    public DashboardCanvas addGameElements(GameElement element, List<Point> points){
        for(int i = 0; i < points.size(); i++){
            addGameElement(element, points.get(i));
        }
        return this;
    }

    public void draw(){
        FtcDashboard.getInstance().sendTelemetryPacket(telemetryPacket);
    }
}
