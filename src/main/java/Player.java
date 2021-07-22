import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;

public class Player implements Runnable {
    final ArrayList<Point> macroPoints;
    final ArrayList<Long> times;

    volatile boolean running = false;

    public Player(final ArrayList<Point> mP, final ArrayList<Long> times) {
        this.macroPoints = mP;
        this.times = times;
    }

    @Override
    public void run() {
        try {
            final Robot bot = new Robot();
            while (running) {
                for (int i = 0; i < macroPoints.size() && running; i++) {
                    final Point p = macroPoints.get(i);
                    final Long t = times.get(i);
                    Thread.sleep(t);
                    bot.mouseMove(p.x, p.y);
                }
            }

        } catch (AWTException|InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        running = false;
    }
}