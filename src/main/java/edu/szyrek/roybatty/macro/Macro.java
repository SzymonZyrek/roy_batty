package edu.szyrek.roybatty.macro;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.macro.entry.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;

@Slf4j
public class Macro {
    @Getter
    private ArrayList<MacroEntry> entries;

    public Macro(final ArrayList<MacroEntry> entries)
    {
        this.entries = entries;
    }

    public void printMacro()
    {
        for (final MacroEntry entry: entries)
        {
            log.error(entry.writeAsString());
        }
    }

    public void saveMacroFile(final String filePath)
    {
        try (final PrintWriter pointsWriter = new PrintWriter(filePath, RoyBatty.FILE_ENCODING))
        {
            for (final MacroEntry entry: entries)
            {
                pointsWriter.println(entry.writeAsString());
            }
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            RoyBatty.logException("Error saving macro at path: "+filePath, ex);
        }
    }

    public static Macro loadMacroFile(final String filePath)
    {
        final ArrayList<MacroEntry> entries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                switch (line.charAt(0))
                {
                    case 'M':
                        entries.add(new MouseEntry(line));
                        break;
                    case 'L':
                        entries.add(new LeftClickEntry(line));
                        break;
                    case 'l':
                        entries.add(new LeftReleaseEntry(line));
                        break;
                    case 'R':
                        entries.add(new RightClickEntry(line));
                        break;
                    case 'r':
                        entries.add(new RightReleaseEntry(line));
                        break;
                    case 'K':
                        entries.add(new KeyPressEntry(line));
                        break;
                    case 'k':
                        entries.add(new KeyReleaseEntry(line));
                        break;
                    case 'S':
                        entries.add(new ScrollEntry(line));
                        break;
                    default:
                        RoyBatty.logError("Uknown macro entry: "+line);
                }
            }
            final Macro newMacro = new Macro(entries);
            newMacro.printMacro();
            RoyBatty.logInfo("MACRO FROM FILE: "+filePath);
            return newMacro;
        }
        catch (FileNotFoundException ex)
        {
            RoyBatty.logException("Unable to open macro file at path: " + filePath, ex);
        }
        catch (IOException ex)
        {
            RoyBatty.logException("Error reading macro file at path: " + filePath, ex);
        }
        return null;
    }
}
