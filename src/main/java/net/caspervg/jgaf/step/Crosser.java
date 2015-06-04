package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Organism;

import java.util.List;

public interface Crosser {

    <O extends Organism> List<O> cross(O father, O mother);

}
