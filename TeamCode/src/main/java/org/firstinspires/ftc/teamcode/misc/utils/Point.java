package org.firstinspires.ftc.teamcode.misc.utils;

import android.util.Pair;

public class Point {
    public double x;
    public double y;
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

    @Override
    public String toString(){

        return "(" + x + ", " + y + ")";
    }
}
