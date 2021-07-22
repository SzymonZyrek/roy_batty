package edu.szyrek.roybatty.macro.entry;

public abstract class BaseEntry implements MacroEntry
{
    protected int time;
    public BaseEntry(String fromString) {}
    public BaseEntry(int time)
    {
        this.time = time;
    }
    protected abstract char getLetter();

    @Override
    public String writeAsString() {
        return "" + this.getLetter();
    }
}
