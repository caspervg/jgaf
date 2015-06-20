package net.caspervg.jgaf.step;

@FunctionalInterface
public interface StepFactory<T extends Step<O>, O> {
    T newInstance(Object... arguments);
}
