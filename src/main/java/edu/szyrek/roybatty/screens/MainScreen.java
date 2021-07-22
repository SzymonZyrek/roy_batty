package edu.szyrek.roybatty.screens;

import javax.swing.*;

public class MainScreen extends JTabbedPane {
    public MainScreen()
    {
        final RecorderScreen recorderScreen = new RecorderScreen();
        final ImageIcon icon = new ImageIcon("test.png");
        addTab("Record", icon, recorderScreen,
                "Record and play macros");
    }
}
