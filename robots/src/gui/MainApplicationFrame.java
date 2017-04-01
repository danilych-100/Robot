package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.Stack;

import javax.swing.*;

import log.Logger;
import serialize.*;
import serialize.Window;

public class MainApplicationFrame extends JFrame implements ISaverLoader
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private Map<String,Object> windowSettings;
    
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        try{
            load();
        } catch (FileNotFoundException e) {
            LogWindow logWindow = createLogWindow();
            addWindow(logWindow);

            GameWindow gameWindow = createGameWindow();
            addWindow(gameWindow);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setJMenuBar(new MenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                save();
            }
        });
    }

    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
    protected GameWindow createGameWindow()
    {
        GameWindow gameWindow = new GameWindow(this);
        gameWindow.setSize(400,  400);
        return gameWindow;
    }
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    @Override
    public void save() {
        Object[] options = {"Да", "Отмена"};
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        if (JOptionPane.showOptionDialog(this, "Выйти?",
                "Подтверждение выхода", 1,
                1, null, options, null) == JOptionPane.OK_OPTION) {
            WindowsSerializer windowsSerializer = new WindowsSerializer();
            for (JInternalFrame window : desktopPane.getAllFrames()) {
                Window windowSet = new Window(window.getTitle(), window.getLocation(), window.getSize());
                windowsSerializer.addSavingWindow(windowSet);
            }
            try {
                windowsSerializer.saveWindowSettings();
            } catch (IOException ignored) {

            }
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    @Override
    public void load() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(WindowsSerializer.settingsName);
        ObjectInputStream settingsObject = new ObjectInputStream(fileIn);

        WindowsSerializer windowsSerializer = (WindowsSerializer) settingsObject.readObject();

        Stack<Window> robotCoordSettings = new Stack<>();
        GameWindow gameWindow = null;
        for(Window window : windowsSerializer.getSavingWindows()){
            switch (window.name){
                case "Протокол работы":
                    LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
                    logWindow.setLocation(window.point);
                    logWindow.setSize(window.dimension);
                    Logger.debug("Протокол работает");
                    addWindow(logWindow);
                    break;
                case "Игровое поле":
                    gameWindow = new GameWindow(this);
                    gameWindow.setLocation(window.point);
                    gameWindow.setSize(window.dimension);
                     if(!robotCoordSettings.isEmpty()){
                        Window currentRobotCoordSetting = robotCoordSettings.pop();
                        gameWindow.getRobotCoordinateWindow().setLocation(currentRobotCoordSetting.point);
                        gameWindow.getRobotCoordinateWindow().setSize(currentRobotCoordSetting.dimension);
                        gameWindow.getRobotCoordinateWindow().pack();
                        //robotCoordSettings.clear();
                    }
                    addWindow(gameWindow);
                    break;
                case "Координаты робота":
                    if(gameWindow==null){
                        robotCoordSettings.add(new Window("Координаты робота",window.point,window.dimension));
                    }
                    else {
                        gameWindow.getRobotCoordinateWindow().setLocation(window.point);
                        gameWindow.getRobotCoordinateWindow().setSize(window.dimension);
                        gameWindow.getRobotCoordinateWindow().pack();
                    }
            }
        }
    }
}
