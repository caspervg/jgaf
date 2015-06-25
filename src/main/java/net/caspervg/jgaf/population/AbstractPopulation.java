package net.caspervg.jgaf.population;

import net.caspervg.jgaf.Organism;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractPopulation<O extends Organism<O, F>, F extends Number & Comparable, C extends Collection<O>> implements Population<O> {

    private Selector<O> breedingSelector;
    private Selector<O> killingSelector;

    public AbstractPopulation(Selector<O> allSelector) {
        this(allSelector, allSelector);
    }

    public AbstractPopulation(Selector<O> breedingSelector, Selector<O> killingSelector) {
        this.breedingSelector = breedingSelector;
        this.killingSelector = killingSelector;
    }

    public AbstractPopulation(AbstractPopulation population) {
        this.breedingSelector = population.getBreedingSelector();
        this.killingSelector = population.getKillingSelector();
    }

    protected final Selector<O> getBreedingSelector() {
        return this.breedingSelector;
    }

    protected final Selector<O> getKillingSelector() {
        return this.killingSelector;
    }

    protected abstract C organisms();

    @Override
    public void add(O o) {
        organisms().add(o);
    }

    @Override
    public void addAll(Collection<? extends O> os) {
        organisms().addAll(os);
    }

    @Override
    public O get(int index) {
        throw new UnsupportedOperationException("This population does not support indexed retrieval");
    }

    @Override
    public Collection<O> getAll() {
        return new ArrayList<>(organisms());
    }

    @Override
    public Iterator<O> iterator() {
        return getAll().iterator();
    }

    @Override
    public void remove(O o) {
        organisms().remove(o);
    }

    @Override
    public void removeAll(Collection<? extends O> os) {
        organisms().removeAll(os);
    }

    @Override
    public int size() {
        return organisms().size();
    }

    @Override
    public Stream<O> stream() {
        return organisms().stream();
    }
}
