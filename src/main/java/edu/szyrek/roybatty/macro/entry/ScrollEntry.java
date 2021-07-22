package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ScrollEntry extends BaseEntry implements MacroEntry
{
    private int amount;

    public ScrollEntry(int amount, int time)
    {
        super(time);
        this.amount = amount;
    }

    public ScrollEntry(final String fromString)
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

        this.amount = Integer.parseInt(values.get(0));
        this.time = Integer.parseInt(values.get(1));
    }

    @Override
    protected char getLetter() {
        return 'S';
    }

    @Override
    public String writeAsString()
    {
        return super.writeAsString() + " " + this.amount + " " + this.time;
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.mouseWheel(this.amount);
    }
}
