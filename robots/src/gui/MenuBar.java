package gui;

import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 * Created by lucky on 11.03.2017.
 */
public class MenuBar extends JMenuBar {

    private MainApplicationFrame mainFrame;

    public MenuBar(MainApplicationFrame frame){
        mainFrame = frame;
        generateMenuBar();
    }

    private void setUpMenuItemNewLogWindow(JMenu menu){
        JMenuItem menuItem = new JMenuItem("Новое окно лога");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("newLog");
        menuItem.addActionListener((event) -> {
            LogWindow logWindow = mainFrame.createLogWindow();
            mainFrame.addWindow(logWindow);
        });
        menu.add(menuItem);
    }
    private void setUpMenuItemNewGameWindow(JMenu menu){
        JMenuItem menuItem = new JMenuItem("Новое окно игры");
        menuItem.setMnemonic(KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("newGame");
        menuItem.addActionListener((event) -> {
            GameWindow gameWindow = mainFrame.createGameWindow();
            mainFrame.addWindow(gameWindow);
        });
        menu.add(menuItem);
    }
    private void setUpMenuItemExit(JMenu menu){
        JMenuItem menuItem = new JMenuItem("Выйти");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener((event) -> {
            exitEvent();
        });
        menu.add(menuItem);
    }
    private void exitEvent(){
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
    }
    private void generateMenuBar()
    {
        JMenu menu = new JMenu("Файл");
        menu.setMnemonic(KeyEvent.VK_D);
        this.add(menu);
        //Set up the first menu item.
        setUpMenuItemNewLogWindow(menu);
        setUpMenuItemNewGameWindow(menu);
        //Set up the second menu item.
        setUpMenuItemExit(menu);
        JMenu lookAndFeelMenu = setUpLookAndFeelMenu();
        JMenu testMenu = setUpTestMenu();
        this.add(lookAndFeelMenu);
        this.add(testMenu);
    }
    private JMenu setUpLookAndFeelMenu(){
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                mainFrame.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }
        return lookAndFeelMenu;
    }
    private JMenu setUpTestMenu(){
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
