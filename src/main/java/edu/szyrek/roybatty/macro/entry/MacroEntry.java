package edu.szyrek.roybatty.macro.entry;

import java.awt.*;

public interface MacroEntry
{
    String writeAsString();
    void performEntry(Robot bot);
}
