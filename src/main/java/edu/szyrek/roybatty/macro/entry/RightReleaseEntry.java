package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;
import java.awt.event.InputEvent;

public class RightReleaseEntry extends MouseEntry
{
    public RightReleaseEntry(int x, int y, int time)
    {
        super(x, y, time);
    }

    public RightReleaseEntry(final String fromString)
    {
        super(fromString);
    }

    @Override
    protected char getLetter()
    {
        return 'r';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.mouseMove(this.x, this.y);
        bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }
}
