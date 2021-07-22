import java.io.*;
import java.util.ArrayList;

public class Macro {
    private ArrayList<MacroEntry> entries;

    public Macro(final ArrayList<MacroEntry> entries)
    {
        this.entries = entries;
    }

    public void printMacro() {
        for (final MacroEntry entry: entries) {
            System.out.println(entry.writeAsString());
        }
    }

    public void saveMacroFile(final String filePath) {
        try (PrintWriter pointsWriter = new PrintWriter(filePath, RoyBatty.FILE_ENCODING)) {
            for (final MacroEntry entry: entries) {
                pointsWriter.println(entry.writeAsString());
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
    }

    public static Macro loadMacroFile(final String filePath) {
        ArrayList<MacroEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                switch (line.charAt(0))
                {
                    case 'M':
                        entries.add(new MouseMacro(line));
                        break;
                    case 'L':
                        entries.add(new LeftClickMacro(line));
                        break;
                    case 'l':
                        entries.add(new LeftReleaseMacro(line));
                        break;
                    case 'R':
                        entries.add(new RightClickMacro(line));
                        break;
                    case 'r':
                        entries.add(new RightReleaseMacro(line));
                        break;
                    case 'K':
                        entries.add(new KeyPressMacro(line));
                        break;
                    case 'k':
                        entries.add(new KeyReleaseMacro(line));
                        break;
                    default:
                        RoyBatty.logError("Uknown macro entry: "+line);
                }
            }
            final Macro newMacro = new Macro(entries);
            newMacro.printMacro();
            RoyBatty.logInfo("MACRO FROM FILE: "+filePath);
            return newMacro;
        } catch (FileNotFoundException ex) {
            RoyBatty.logError("Unable to open macro file at path: " + filePath);
        } catch (IOException ex) {
            RoyBatty.logError("Error reading macro file at path: " + filePath);
        }
        return null;
    }

    public ArrayList<MacroEntry> getEntries() {
        return this.entries;
    }
}
