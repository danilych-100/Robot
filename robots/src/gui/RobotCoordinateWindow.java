package gui;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by lucky on 13.03.2017.
 */
public class RobotCoordinateWindow extends JInternalFrame implements Observer {

    private TextArea m_coordinate;
    private Robot robot = new Robot();
    private JPanel panel;

    private double timer = 5;

    public RobotCoordinateWindow(RobotMovingModel robotModel){
        super("Координаты робота",true,true,true,true);

        robotModel.addObserver(this);

        m_coordinate = new TextArea("");
        m_coordinate.setSize(100, 100);
        m_coordinate.append("X_coord:  "+robot.getRobotPositionX());
        m_coordinate.append("\r\n");
        m_coordinate.append("Y_coord:  "+robot.getRobotPositionY());
        m_coordinate.append("\r\n");
        m_coordinate.append("DistanceToGoal: "+robot.distanceToGoal());
        m_coordinate.setEditable(false);
        panel= new JPanel(new BorderLayout());
        panel.add(m_coordinate, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    public void updateRobotCoordinate() {
        panel.remove(m_coordinate);
        m_coordinate.setVisible(false);

        TextArea newTextArea = new TextArea();
        newTextArea.setSize(m_coordinate.getSize());
        newTextArea.setLocation(m_coordinate.getLocation());
        newTextArea.append("X_coord:  "+robot.getRobotPositionX());
        newTextArea.append("\r\n");
        newTextArea.append("Y_coord:  "+robot.getRobotPositionY());
        newTextArea.append("\r\n");
        newTextArea.append("DistanceToGoal: "+robot.distanceToGoal());
        panel.add(newTextArea);
        m_coordinate=newTextArea;
    }

    @Override
    public void update(Observable o, Object arg) {
        robot = (Robot) arg;
        if(timer>0){
            timer--;
            return;
        }
        else {
            timer = 5;
            updateRobotCoordinate();
        }

    }

}
