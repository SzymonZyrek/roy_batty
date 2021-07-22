package edu.szyrek.roybatty.player;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.macro.entry.MacroEntry;
import lombok.Getter;
import lombok.Setter;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.CompletableFuture;

public class Player implements Runnable
{
    private final Macro macro;
    @Getter
    @Setter
    private volatile boolean running = false;
    @Getter
    @Setter
    private CompletableFuture<Integer> future;
    private boolean repeat;

    public Player(final Macro macro)
    {
        this.macro = macro;
    }

    public void stop()
    {
        setRunning(false);
    }

    public void start()
    {
        start(false);
    }

    public void start(final boolean repeat)
    {
        this.repeat = repeat;
        setRunning(true);
        final Thread macroThread = new Thread(this);
        macroThread.start();
    }

    @Override
    public void run()
    {
        try
        {
            final Robot bot = new Robot();
            while (running)
            {
                for (final MacroEntry entry: macro.getEntries())
                {
                    if (!running)
                        break;
                    entry.performEntry(bot);
                }
                if (!repeat)
                {
                    running = false;
                }
            }
            if (this.future != null)
            {
                this.future.complete(0);
            }
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
}