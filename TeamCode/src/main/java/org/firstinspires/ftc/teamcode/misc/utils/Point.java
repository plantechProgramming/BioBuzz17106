package org.firstinspires.ftc.teamcode.misc.utils;

import android.util.Pair;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.List;

public class Point {
    private double x;
    private double y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public double getX(){
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public double distanceTo(Point p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }

    public static Point pairToPoint(Pair<Double, Double> xyPair){
        return new Point(xyPair.first, xyPair.second);
    }

    public static boolean isPointInRect(Point p, Point bottomLeft, double width, double height, double rotateAngle, Pose2D rPose){ // can be rotated rect
        Point relPointRobotCords = new Point(p.getX() - rPose.getX(DistanceUnit.CM), p.getY() - rPose.getY(DistanceUnit.CM));
        Point relBottomLeftRobotCords = new Point(bottomLeft.getX() - rPose.getX(DistanceUnit.CM), bottomLeft.getY() - rPose.getY(DistanceUnit.CM));
        Point rotatedPoint = pairToPoint(PoseFunctions.rotation2D(relPointRobotCords.getX(), relPointRobotCords.getY(), rotateAngle));
        Point rotatedBottomLeft = pairToPoint(PoseFunctions.rotation2D(relBottomLeftRobotCords.getX(), relBottomLeftRobotCords.getY(), rotateAngle));
        return isPointInRect(rotatedPoint, rotatedBottomLeft, width, height);
    }
    public static boolean isPointInRect(Point p, Point bottomLeft, double width, double height){ // not rotated rect
        return p.getX()<bottomLeft.getX()+width && p.getX()>bottomLeft.getX() &&
                p.getY()<bottomLeft.getY()+height && p.getY()>bottomLeft.getY();
    }

    public static Point transformPoint(Point p, double dis, double deg){
        double rad = Math.toRadians(deg);
        return new Point(p.getX()+dis*Math.cos(rad), p.getY()+dis*Math.sin(rad));
    }
    public static void sortPointsByDisToPoint(Point p, List<Point> points){
        if(p == null || points == null){
            return;
        }
        points.sort((o1, o2) -> {
            double dist1 = o1.distanceTo(p);
            double dist2 = o2.distanceTo(p);
            return Double.compare(dist1, dist2);
        });
    }
    public boolean inYourHalfOfField(){
        if(x > -PoseFunctions.LEN_FIELD/2 && x < PoseFunctions.LEN_FIELD/2){
            if(Alliance.get() == Alliance.RED){
                return y > 0;
            }
            else{
                return y < 0;
            }
        }
        return false;
    }

    @Override
    public String toString(){

        return "(" + x + ", " + y + ")";
    }
}
