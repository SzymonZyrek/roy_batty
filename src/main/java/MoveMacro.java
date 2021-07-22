import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveMacro implements MacroEntry {
    private int x,y;
    private int time;

    public MoveMacro(final int x, final int y, final int time)
    {
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    public String writeAsString() {
        return "M " + this.x + " " + this.y + " " + this.time;
    }

    public static MacroEntry fromString(final String fromString) {
        if (!fromString.startsWith("M "))
        {
            return null;
        }
        final String moveMacroString = fromString.substring(2);
        final List<String> values = Arrays.asList(moveMacroString.split(" "));

        if (values.size() != 3)
        {
            RoyBatty.logError("Failed to load line: "+fromString);
            return null;
        }

        int x = Integer.parseInt(values.get(0));
        int y = Integer.parseInt(values.get(1));
        int time = Integer.parseInt(values.get(2));
p
        return new MoveMacro(x, y, time);
    }

    @Override
    public void performEntry(final Robot bot) {
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e) {
            RoyBatty.logError(e.getMessage());
            e.printStackTrace();
        }
        bot.mouseMove(this.x, this.y);
    }
}
