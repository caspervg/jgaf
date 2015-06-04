package net.caspervg.jgaf.step;

import net.caspervg.jgaf.Organism;

public interface Fitter {
    <O extends Organism> double calculate(O organism);
}
