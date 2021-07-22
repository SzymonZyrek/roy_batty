package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;

public class MacroAssignment implements Assignment
{
    @Getter
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

    @SneakyThrows
    public CompletableFuture<Integer> getFuture()
    {
        while (this.macroPlayer == null)
        {
            Thread.sleep(100);
        }
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
