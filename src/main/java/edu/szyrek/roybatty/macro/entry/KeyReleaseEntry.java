package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;
import java.awt.*;

public class KeyReleaseEntry extends KeyEntry implements MacroEntry
{
    public KeyReleaseEntry(int key, int time)
    {
        super(key, time);
    }

    public KeyReleaseEntry(String string)
    {
        super(string);
    }

    protected char getLetter()
    {
        return 'k';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.keyRelease(key);
    }
}
