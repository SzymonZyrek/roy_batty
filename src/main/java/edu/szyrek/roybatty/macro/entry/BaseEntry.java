package edu.szyrek.roybatty.macro.entry;

public abstract class BaseEntry implements MacroEntry {
    protected int time;

    public BaseEntry(String fromString) {}

    public BaseEntry(int time)
    {
        this.time = time;
    }

    protected char getLetter() {
        return 'M';
    }

    @Override
    public String writeAsString() {
        return "" + this.getLetter();
    }
}
