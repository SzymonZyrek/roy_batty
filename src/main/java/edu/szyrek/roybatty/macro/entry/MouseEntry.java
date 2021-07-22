package edu.szyrek.roybatty.macro.entry;

import edu.szyrek.roybatty.RoyBatty;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MouseEntry extends BaseEntry implements MacroEntry{
    protected int x,y;

    protected MouseEntry(int time) {
        super(time);
    }

    public MouseEntry(final int x, final int y, final int time)
    {
        super(time);
        this.x = x;
        this.y = y;
    }

    @Override
    public String writeAsString() {
        return super.writeAsString() + " " + this.x + " " + this.y + " " + this.time;
    }

    public MouseEntry(final String fromString) {
        super(fromString);
        if (!fromString.startsWith(this.getLetter()+" "))
        {
            throw new IllegalArgumentException("wrong constructor string for "+this.getClass().getSimpleName()+": "+fromString);
        }
        final String moveMacroString = fromString.substring(2);
        final List<String> values = Arrays.asList(moveMacroString.split(" "));

        if (values.size() != 3)
        {
            throw new IllegalArgumentException("wrong constructor string for "+this.getClass().getSimpleName()+": "+fromString);
        }

        this.x = Integer.parseInt(values.get(0));
        this.y = Integer.parseInt(values.get(1));
        this.time = Integer.parseInt(values.get(2));
    }

    @Override
    public void performEntry(final Robot bot) {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e) {
            RoyBatty.logError(e.getMessage());
            e.printStackTrace();
        }
        bot.mouseMove(this.x, this.y);
    }
}
