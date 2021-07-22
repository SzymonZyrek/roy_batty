package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

import java.util.concurrent.CompletableFuture;

public class MacroAssignment implements Assignment
{
    private final Macro macro;
    private Player macroPlayer;

    public MacroAssignment(final Macro macro)
    {
        this.macro = macro;
    }

    @Override
    public void run()
    {
        macroPlayer = new Player(this.macro);
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
        this.macroPlayer.stop();
    }
}
