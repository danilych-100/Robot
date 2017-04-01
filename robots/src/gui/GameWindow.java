package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import static com.sun.deploy.util.SessionState.save;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    private final RobotMovingModel robotMovingModel;
    private RobotCoordinateWindow robotCoordinateWindow;

    public GameWindow(MainApplicationFrame mainFrame)
    {
        super("Игровое поле", true, true, true, true);

        robotMovingModel = new RobotMovingModel();

        m_visualizer = new GameVisualizer(robotMovingModel);
        robotCoordinateWindow = new RobotCoordinateWindow(robotMovingModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        mainFrame.addWindow(robotCoordinateWindow);
        pack();

        addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                try {
                    robotCoordinateWindow.setClosed(true);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {

            }

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {

            }
        });
    }

    public RobotCoordinateWindow getRobotCoordinateWindow(){
        return robotCoordinateWindow;
    }

}
