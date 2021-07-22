package edu.szyrek.roybatty.hotkey;

import edu.szyrek.roybatty.macro.Macro;
import edu.szyrek.roybatty.player.Player;

public class MacroAssignment implements Assignment {
    private final Macro macro;

    public MacroAssignment(final Macro macro)
    {
        this.macro = macro;
    }

    @Override
    public void run()
    {
        Player macroPlayer = new Player(this.macro);
        macroPlayer.setRunning(true);
        Thread macroThread = new Thread(macroPlayer);
        macroThread.start();
    }
}
