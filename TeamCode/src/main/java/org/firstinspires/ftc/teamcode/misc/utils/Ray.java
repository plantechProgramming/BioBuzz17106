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
    public double size(){
        return ray.size();
    }

}
