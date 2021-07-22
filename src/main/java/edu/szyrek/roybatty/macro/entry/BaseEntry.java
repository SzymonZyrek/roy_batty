package edu.szyrek.roybatty.macro;

public abstract class BaseMacro implements MacroEntry {
    protected int time;

    public BaseMacro(String fromString) {}

    public BaseMacro(int time)
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
