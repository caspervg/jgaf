package net.caspervg.jgaf.step.breeder;

import net.caspervg.jgaf.step.Crosser;
import net.caspervg.jgaf.step.StepFactory;

public class BasicBreederFactory<O> implements StepFactory<BasicBreeder<O>, O> {

    @SuppressWarnings("unchecked")
    @Override
    public BasicBreeder<O> newInstance(Object... arguments) {
        if (arguments.length < 1 || !(arguments[0] instanceof Crosser)) {
            throw new IllegalArgumentException("Need at least an instance of Crosser<O>");
        }

        return new BasicBreeder<>((Crosser<O>) arguments[0]);
    }
}
