package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

import java.util.concurrent.CompletableFuture;

public class MacroAssignment implements Assignment
{
    private final Macro macro;
    private Player macroPlayer;
    private boolean repeat;

    public MacroAssignment(final Macro macro, final boolean repeat)
    {
        this.macro = macro;
        this.repeat = repeat;
    }

    @Override
    public void run()
    {
        macroPlayer = new Player(this.macro, repeat);
        macroPlayer.setRunning(true);
        this.macroPlayer.setFuture(new CompletableFuture<>());
        macroPlayer.start();
    }

    public CompletableFuture<Integer> getFuture()
    {
        return this.macroPlayer.getFuture();
    }

    public void stop()
    {
        if (this.macro != null)
        {
            this.macroPlayer.stop();
        }
    }
}
