package edu.szyrek.roybatty.hotkey;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import edu.szyrek.roybatty.KeyMappings;
import lombok.Getter;

import java.beans.Transient;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Hotkey
{
    @Getter
    private Set<Integer> codes;

    public Hotkey(final Set<Integer> codes)
    {
        this.codes = codes;
    }

    @JsonCreator
    public Hotkey(final String encoded)
    {
        this.codes = Arrays.stream(encoded.split("\\+")).map(e-> KeyMappings.textToJnativeCodes(e)).collect(Collectors.toSet());
    }

    @JsonValue
    @Override
    public String toString()
    {
        return this.codes.stream().map(e->KeyMappings.nativeCodesToText(e)).collect(Collectors.joining("+")).toString();
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

    @JsonIgnore
    @Transient
    public boolean isValid()
    {
        for (final Integer code: codes)
        {
            if (KeyMappings.nativeCodesToText(code) == "")
            {
                return false;
            }
        }
        return true;
    }
}
