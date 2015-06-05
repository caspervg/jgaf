package net.caspervg.jgaf.step;

import java.util.List;

public interface Crosser<O> {
    List<O> cross(O father, O mother);
}
