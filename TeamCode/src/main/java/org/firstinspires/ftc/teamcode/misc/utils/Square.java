package org.firstinspires.ftc.teamcode.misc.utils;

public class Square {
    Point topLeft;
    Point bottomRight;
    Point topRight;
    Point bottomLeft;
    public Square(Point topLeft, Point bottomRight){
        this.topLeft = topLeft;
        topRight = new Point(bottomRight.getX(), topLeft.getY());
        this.bottomRight = bottomRight;
        bottomLeft = new Point(topLeft.getX(), bottomRight.getY());
    }
    public Square(Point topLeft, Point bottomLeft, Point topRight, Point bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
    }
    public Point getTopLeft(){
        return topLeft;
    }
    public Point getBottomRight(){
        return bottomRight;
    }
    public Point getTopRight(){
        return topRight;
    }
    public Point getBottomLeft(){
        return bottomLeft;
    }
    public Point getCenter(){
        return new Point((topLeft.getX() + bottomRight.getX())/2, (topLeft.getY() + bottomRight.getY())/2);
    }
    public double getWidth(){
        return topLeft.distanceTo(topRight);
    }

}
