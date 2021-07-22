package edu.szyrek.roybatty.macro.entry;

import lombok.SneakyThrows;

import java.awt.*;

public class KeyPressEntry extends KeyEntry implements MacroEntry
{
    public KeyPressEntry(int key, int time)
    {
        super(key, time);
    }

    public KeyPressEntry(String string)
    {
        super(string);
    }

    protected char getLetter()
    {
        return 'K';
    }

    @Override
    @SneakyThrows
    public void performEntry(final Robot bot)
    {
        Thread.sleep(this.time);
        bot.keyPress(key);
    }
}
