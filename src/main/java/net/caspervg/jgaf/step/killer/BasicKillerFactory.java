package net.caspervg.jgaf.step.killer;

import net.caspervg.jgaf.step.Killer;
import net.caspervg.jgaf.step.StepFactory;

public class BasicKillerFactory<O> implements StepFactory<Killer<O>, O> {

    @Override
    public Killer<O> newInstance(Object... arguments) {
        return new BasicKiller<>();
    }
}
