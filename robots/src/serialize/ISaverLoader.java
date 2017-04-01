package serialize;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by lucky on 11.03.2017.
 */
public interface ISaverLoader {
    public void save();
    public void load() throws IOException, ClassNotFoundException;
}
