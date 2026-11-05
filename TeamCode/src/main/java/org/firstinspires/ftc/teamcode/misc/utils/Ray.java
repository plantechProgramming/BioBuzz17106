package org.firstinspires.ftc.teamcode.misc.utils;

import android.util.Pair;

import java.util.List;

public class Ray {
    List<Pair<Point, GameElement>> ray;
    public Ray(List<Pair<Point, GameElement>> ray){
        this.ray = ray;
    }
    public List<Pair<Point, GameElement>> getRay(){
        return ray;
    }

    public void addElement(Pair<Point, GameElement> element){
        ray.add(element);
    }
    public int size(){
        return ray.size();
    }
    public double getRayDis(Point orig){
        Point furthestPoint = getFurthestPoint(orig);
        return furthestPoint.distanceTo(orig);
    }

    public Point getFurthestPoint(Point orig){
        Point furthestPoint = null;
        for(Pair<Point, GameElement> element : ray){
            if(furthestPoint == null || element.first.distanceTo(orig) > furthestPoint.distanceTo(orig)){
                furthestPoint = element.first;
            }
        }
        return furthestPoint;

    }

}
