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
    private volatile boolean running;
    @Getter
    @Setter
    private CompletableFuture<Integer> future;
    private boolean repeat;

    public Player(final Macro macro, final boolean repeat)
    {
        this.macro = macro;
        this.repeat = repeat;
    }

    public Player(final Macro macro)
    {
        this(macro, false);
    }

    public void stop()
    {
        setRunning(false);
    }

    public void start()
    {
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