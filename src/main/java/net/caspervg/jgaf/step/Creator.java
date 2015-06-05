package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;

import java.util.List;

public interface Creator<O> {
    List<O> create(Arguments arguments);
}
