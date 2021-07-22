package edu.szyrek.roybatty.macro.entry;

import java.util.Arrays;
import java.util.List;

public abstract class KeyEntry extends BaseEntry implements MacroEntry {
    protected int key;

    public KeyEntry(int key, int time)
    {
        super(time);
        this.key = key;
    }

    public KeyEntry(final String fromString)
    {
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

    @Override
    public String writeAsString()
    {
        return super.writeAsString() + " " + this.key + " " + this.time;
    }
}
