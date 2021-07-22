package edu.szyrek.roybatty.macro;

import java.awt.*;

public interface MacroEntry {
    String writeAsString();
    void performEntry(Robot bot);
}
