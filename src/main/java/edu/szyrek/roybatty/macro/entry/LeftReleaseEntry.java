package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;

public class LeftReleaseEntry extends MouseEntry
{

    public LeftReleaseEntry(int x, int y, int time)
    {
        super(x, y, time);
    }

    public LeftReleaseEntry(final String fromString)
    {
        super(fromString);
    }

    @Override
    protected char getLetter()
    {
        return 'l';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.mouseMove(this.x, this.y);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
