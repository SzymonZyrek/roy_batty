import java.awt.*;
import java.awt.event.InputEvent;

public class RightReleaseMacro extends MouseMacro {
    public RightReleaseMacro(int x, int y, int time) {
        super(x, y, time);
    }

    public RightReleaseMacro(final String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter() {
        return 'r';
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
        bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
    }
}
