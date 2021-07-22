import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jnativehook.GlobalScreen;
import org.jnativehook.dispatcher.SwingDispatchService;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

public class Recorder implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener {
    public final static int RECORD_BUTTON = NativeKeyEvent.VC_O;
    public final static int PLAY_BUTTON = NativeKeyEvent.VC_P;
    public final static String FILE_ENCODING = "UTF-8";

    private volatile boolean recording = false;
    private RecorderGUI gui;
    private Long lastEventTime;

    private ArrayList<Point> macroPoints = new ArrayList<>();
    private ArrayList<Long> times = new ArrayList<>();


    public Recorder(final RecorderGUI gui) {
        this.gui = gui;
        GlobalScreen.setEventDispatcher(new SwingDispatchService());
    }

    public boolean isRecording() {
        return recording;
    }

    public void printMacro() {
        for (int i = 0; i < macroPoints.size(); i++) {
            Point p = macroPoints.get(i);
            Long t = times.get(i);
            System.out.println("L " + p.getX() + " " + p.getY() + " " + t);
        }
    }

    public void startRecording() {
        gui.btnStartRecording.setText("Stop Recording");
        recording = (true);
        macroPoints = new ArrayList<>();
        times = new ArrayList<>();
        lastEventTime = System.currentTimeMillis();
    }

    public void stopRecording() {
        gui.btnStartRecording.setText("Start Recording");
        recording = (false);
        printMacro();
        gui.logInfo("MACRO IN MEMORY");
    }

    public void loadMacroFile(final String filePath) {
        try (BufferedReader bufferedPointsReader = new BufferedReader(new FileReader(filePath))) {
            macroPoints = new ArrayList<>();
            times = new ArrayList<>();

            String line;
            while ((line = bufferedPointsReader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                String mBtn = st.nextToken();
                macroPoints.add(new Point(
                        (int) Double.parseDouble(st.nextToken()),
                        (int) Double.parseDouble(st.nextToken()))
                );
                times.add(Long.parseLong(st.nextToken()));
            }
            printMacro();
            gui.logInfo("MACRO FROM FILE: "+filePath);
        } catch (FileNotFoundException ex) {
            gui.logError("Unable to open macro file at path: " + filePath);
        } catch (IOException ex) {
            gui.logError("Error reading macro file at path: " + filePath);
        }
    }

    public void saveMacroFile(final String filePath) {
        if (macroPoints == null || macroPoints.size() ==0) {
            gui.logError("Record or load something first!");
        }
        try (PrintWriter pointsWriter = new PrintWriter(filePath, FILE_ENCODING)) {
            for (int i = 0; i < macroPoints.size(); i++) {
                Point p = macroPoints.get(i);
                Long t = times.get(i);
                pointsWriter.println("L " + p.getX() + " " + p.getY() + " " + t);
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == RECORD_BUTTON && !recording) {
            startRecording();
        } else if (e.getKeyCode() == RECORD_BUTTON) {
            stopRecording();
        }
        if (e.getKeyCode() == PLAY_BUTTON && recording) {
            gui.logError("Stop recording first!");
        } else if (e.getKeyCode() == PLAY_BUTTON && !recording) {
            if (gui.getMacroPlayer()!=null) {
                gui.getMacroPlayer().running = !gui.getMacroPlayer().running;
            }
            if (gui.getMacroPlayer() == null || gui.getMacroPlayer().running) {
                if (macroPoints == null || macroPoints.size() ==0) {
                    gui.logError("Record or load something first!");
                }
                final Player macroPlayer = new Player(macroPoints, times);
                macroPlayer.running = true;
                Thread t1 = new Thread(macroPlayer, "T1");
                gui.setMacroPlayer(macroPlayer);
                t1.start();
            } else {
                gui.getMacroPlayer().stop();
            }
        }
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent e) {
        if (recording)
        {
            macroPoints.add(e.getPoint());
            final Long nowTime = System.currentTimeMillis();
            times.add(nowTime-lastEventTime);
            lastEventTime = nowTime;
        }
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent e) {
        if (recording)
        {
            macroPoints.add(MouseInfo.getPointerInfo().getLocation());
            times.add(System.currentTimeMillis());
        }
    }

    public void nativeKeyPressed(NativeKeyEvent e) {

    }

    public void nativeKeyTyped(NativeKeyEvent e) {

    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {

    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {

    }
}
