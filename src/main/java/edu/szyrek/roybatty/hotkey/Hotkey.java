package edu.szyrek.roybatty.hotkey;

import lombok.Getter;

import java.util.Objects;
import java.util.Set;

public class Hotkey
{
    @Getter
    private Set<Integer> codes;

    public Hotkey(final Set<Integer> codes)
    {
        this.codes = codes;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (o == null || !o.getClass().isAssignableFrom(Hotkey.class))
        {
            return false;
        }
        else
        {
            Hotkey other = (Hotkey) o;
            return this.codes.containsAll(other.getCodes());
        }
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(this.codes);
    }
}
