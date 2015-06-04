package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;

import java.util.List;

public interface Creator {
    <O extends Organism> List<O> create(Arguments arguments);
}
