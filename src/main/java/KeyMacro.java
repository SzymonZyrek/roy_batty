import java.awt.*;
import java.util.Arrays;
import java.util.List;

public abstract class KeyMacro extends BaseMacro implements MacroEntry {
    protected int key;

    public KeyMacro(int key, int time)
    {
        super(time);
        this.key = key;
    }

    @Override
    public String writeAsString() {
        return super.writeAsString() + " " + this.key + " " + this.time;
    }

    public KeyMacro(final String fromString) {
        super(0);
        if (!fromString.startsWith(this.getLetter() + " "))
        {
            throw new IllegalArgumentException("wrong constructor string for "+this.getClass().getSimpleName()+": "+fromString);
        }
        final String moveMacroString = fromString.substring(2);
        final List<String> values = Arrays.asList(moveMacroString.split(" "));

        if (values.size() != 2)
        {
            throw new IllegalArgumentException("wrong constructor string for "+this.getClass().getSimpleName()+": "+fromString);
        }

        this.key = Integer.parseInt(values.get(0));
        this.time = Integer.parseInt(values.get(1));
    }

    public int getKey()
    {
        return key;
    }
}
