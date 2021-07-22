import com.sun.xml.internal.rngom.parse.host.Base;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public abstract class BaseMacro implements MacroEntry {
    protected int time;

    public BaseMacro(String fromString) {}

    public BaseMacro(int time)
    {
        this.time = time;
    }

    protected char getLetter() {
        return 'M';
    }

    @Override
    public String writeAsString() {
        return "" + this.getLetter();
    }
}
