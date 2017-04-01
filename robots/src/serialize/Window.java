package serialize;

import java.awt.*;
import java.io.Serializable;

/**
 * Created by lucky on 13.03.2017.
 */
public class Window implements Serializable{
    public String name;
    public Point point;
    public Dimension dimension;

    public Window(String name,Point point,Dimension dimension){
        this.name=name;
        this.point=point;
        this.dimension=dimension;
    }
}
