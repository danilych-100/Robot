package serialize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucky on 11.03.2017.
 */
public class WindowsSerializer implements Serializable{
    private static final long serialVersionUID = 8392323835134579169L;

    public static final String settingsName = "windowsSettings.txt";

    private ArrayList<Window> savingWindows;

    public WindowsSerializer(){
        savingWindows = new ArrayList<>();
    }

    public void addSavingWindow(Window window){
        savingWindows.add(window);
    }

    public ArrayList<Window> getSavingWindows(){
        return savingWindows;
    }
    public void saveWindowSettings() throws IOException {
        try {
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(settingsName));
            outStream.writeObject(this);
            outStream.close();
        } catch (IOException e) {

        }
    }


}
