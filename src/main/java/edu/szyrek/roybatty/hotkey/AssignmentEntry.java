package edu.szyrek.roybatty.hotkey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AssignmentEntry {
    @Getter
    @Setter
    private Hotkey hotkey;
    @Getter
    @Setter
    private String macroName;
    @Getter
    @Setter
    private boolean repeat;
    @Getter
    @Setter
    private boolean active;
}
