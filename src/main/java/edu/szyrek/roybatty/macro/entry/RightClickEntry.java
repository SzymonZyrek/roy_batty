package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;

public class RightClickEntry extends MouseEntry
{
    public RightClickEntry(final int x, final int y, final int time)
    {
        super(x, y, time);
    }

    public RightClickEntry(final String fromString)
    {
        super(fromString);
    }

    @Override
    protected char getLetter()
    {
        return 'R';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.mouseMove(this.x, this.y);
        bot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
    }
}
