package edu.szyrek.roybatty.macro;

import edu.szyrek.roybatty.RoyBatty;
import edu.szyrek.roybatty.RoyBattyConfig;
import edu.szyrek.roybatty.macro.entry.*;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Slf4j
public class Macro {
    @Getter
    @Setter
    private String name;
    @Getter
    private ArrayList<MacroEntry> entries;

    public Macro(final String name, final ArrayList<MacroEntry> entries)
    {
        this.name = name;
        this.entries = entries;
    }

    public void printMacro()
    {
        for (final MacroEntry entry: entries)
        {
            log.error(entry.writeAsString());
        }
    }

    public void saveMacroFile(final String fileName)
    {
        final String filePath = RoyBattyConfig.getConfig().getMacrosPath() + File.separator + fileName;

        try (final PrintWriter pointsWriter = new PrintWriter(filePath, RoyBattyConfig.getConfig().getFileEncoding()))
        {
            for (final MacroEntry entry: entries)
            {
                pointsWriter.println(entry.writeAsString());
            }
            RoyBatty.registerMacro(fileName);
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            RoyBatty.logException("Error saving macro at path: "+filePath, ex);
        }
    }

    public static Macro loadMacroFile(final String fileName)
    {
        final String filePath = RoyBattyConfig.getConfig().getMacrosPath() + File.separator + fileName;
        final ArrayList<MacroEntry> entries = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath)))
        {
            String err = "File "+filePath+ " doesn't exist!";
            RoyBatty.logError(err);
            return null;
        }
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
            final Macro newMacro = new Macro(fileName, entries);
            newMacro.printMacro();
            RoyBatty.logInfo("ACTIVE: "+filePath);
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
