package gui;

import java.util.ArrayList;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lucky on 14.03.2017.
 */
public class RobotMovingModel extends java.util.Observable {
    private ArrayList<Observer> observers;
    private final Robot robot = new Robot();

    private final Timer m_timer = initTimer();

    private static Timer initTimer()
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    public RobotMovingModel(){
        observers = new ArrayList<>();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
    }

    private static double distance(double x1, double y1, double x2, double y2)
    {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    private static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }
    private static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update(this,robot);
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, Robot.getMaxVelocity());
        angularVelocity = applyLimits(angularVelocity, -Robot.getMaxAngularVelocity(), Robot.getMaxAngularVelocity());
        double newX = robot.getRobotPositionX() + velocity / angularVelocity *
                (Math.sin(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.sin(robot.getRobotDirection()));
        if (!Double.isFinite(newX))
        {
            newX = robot.getRobotPositionX() + velocity * duration * Math.cos(robot.getRobotDirection());
        }
        double newY = robot.getRobotPositionY()  - velocity / angularVelocity *
                (Math.cos(robot.getRobotDirection()  + angularVelocity * duration) -
                        Math.cos(robot.getRobotDirection()));
        if (!Double.isFinite(newY))
        {
            newY = robot.getRobotPositionY() + velocity * duration * Math.sin(robot.getRobotDirection());
        }
        robot.setRobotPositionX(newX);
        robot.setRobotPositionY(newY);
        double newDirection = asNormalizedRadians(robot.getRobotDirection() + angularVelocity * duration);
        robot.setRobotDirection(newDirection);
        notifyObservers();
    }

    protected void onModelUpdateEvent()
    {
        double distance = distance(robot.getTargetPositionX(), robot.getTargetPositionY(),
                robot.getRobotPositionX(), robot.getRobotPositionY() );
        if (distance < 0.5)
        {
            return;
        }
        double velocity = Robot.getMaxVelocity();
        double angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY() ,robot.getTargetPositionX(), robot.getTargetPositionY());
        double angularVelocity = 0;
        if (angleToTarget > robot.getRobotDirection())
        {
            angularVelocity = Robot.getMaxAngularVelocity();
        }
        if (angleToTarget < robot.getRobotDirection())
        {
            angularVelocity = -Robot.getMaxAngularVelocity();
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    private static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += (3*Math.PI+Math.PI/2);
        }
        while (angle >= 2*Math.PI)
        {
            angle -= (3*Math.PI+Math.PI/2);
        }
        return angle;
    }

}
