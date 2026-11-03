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

    public double getAngle(){
        return Math.atan2(bottomRight.getY() - topLeft.getY(), topLeft.getX() - bottomRight.getX());
    }
    public boolean isPointInRect(Point p){
        Point center = getCenter();
        Point relativeCenter = new Point(p.getX() - center.getX(), p.getY() - center.getY());
        Point rotatedPoint = Point.pairToPoint(PoseFunctions.rotation2D(relativeCenter.getX(), relativeCenter.getY(), getAngle()));
        return isPointInNotRotatedRect(rotatedPoint);
    }

    public boolean isPointInNotRotatedRect(Point p){
        return p.getX() > topLeft.getX() && p.getX() < topRight.getX()
                && p.getY() > bottomRight.getY() && p.getY() < topRight.getY();
    }

}
