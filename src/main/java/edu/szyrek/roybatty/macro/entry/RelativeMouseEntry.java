package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;

public class RelativeMouseEntry extends MouseEntry {
    public RelativeMouseEntry(int x, int y, int time) {
        super(x, y, time);
    }

    public RelativeMouseEntry(String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter()
    {
        return 'm';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        final Point start = MouseInfo.getPointerInfo().getLocation();
        bot.mouseMove(start.x + this.x, start.y + this.y);
    }

}
