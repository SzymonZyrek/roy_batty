import java.awt.*;
import java.awt.event.InputEvent;

public class LeftClickMacro extends MouseMacro {

    public LeftClickMacro(int x, int y, int time) {
        super(x, y, time);
    }

    public LeftClickMacro(final String fromString) {
        super(fromString);
    }

    @Override
    protected char getLetter() {
        return 'L';
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
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }
}
