package gui;

import java.awt.*;

/**
 * Created by lucky on 25.03.2017.
 */
public class Robot {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    private volatile int targetPositionX = 150;
    private volatile int targetPositionY = 100;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.006;


    public int getTargetPositionX() {
        return targetPositionX;
    }
    public int getTargetPositionY() {
        return targetPositionY;
    }

    public static double getMaxVelocity() {
        return maxVelocity;
    }
    public static double getMaxAngularVelocity() {
        return maxAngularVelocity;
    }


    public double getRobotPositionX(){
        return robotPositionX;
    }
    public double getRobotPositionY(){
        return robotPositionY;
    }
    public double getRobotDirection(){
        return robotDirection;
    }

    public void setRobotPositionX(double robotPositionX){
        this.robotPositionX = robotPositionX;
    }
    public void setRobotPositionY(double robotPositionY){
        this.robotPositionY = robotPositionY;
    }
    public void setRobotDirection(double robotDirection){
        this.robotDirection = robotDirection;
    }

    public void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }
    public double distanceToGoal()
    {
        double diffX = robotPositionX - targetPositionX;
        double diffY = robotPositionY - targetPositionY;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }


}
