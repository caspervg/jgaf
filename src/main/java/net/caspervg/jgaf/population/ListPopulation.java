package net.caspervg.jgaf.population;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;
import net.caspervg.jgaf.Population;
import net.caspervg.jgaf.step.Selector;

import java.util.*;

public class ListPopulation<O extends Organism<O, F>, F extends Number & Comparable> extends AbstractPopulation<O, F, List<O>> {

    private List<O> organisms;

    /**
     * Creates a new, empty ListPopulation that will use the given Selector
     * for selecting organisms to kill <b>and</b> breed.
     *
     * @see Population
     */
    public ListPopulation(Selector<O> selector) {
        this(selector, new ArrayList<>());
    }

    public ListPopulation(Selector<O> selector, List<O> starting) {
        super(selector);
        this.organisms = starting;
    }

    /**
     * Creates a new, empty ListPopulation that will use given Selectors
     * for selecting organisms to breed and kill, respectively.
     *
     * @param breedingSelector Selector to use for breeding
     * @param killingSelector Selector to use for killing
     */
    public ListPopulation(Selector<O> breedingSelector, Selector<O> killingSelector) {
        super(breedingSelector, killingSelector);
        this.organisms = new ArrayList<>();
    }

    /**
     * Copy constructor for an AbstractPopulation.
     *
     * @param population Population to copy
     */
    public ListPopulation(AbstractPopulation<O, F, Collection<O>> population) {
        super(population);
        this.organisms = new ArrayList<>(population.getAll());
    }

    @Override
    protected List<O> organisms() {
        return organisms;
    }

    @Override
    public O get(int index) {
        return organisms().get(index);
    }

    /*
     * Algorithm-related methods
     */

    @Override
    public void breed(Arguments arguments) {
        List<O> bred = new ArrayList<>();
        List<O> selected = new ArrayList<>(getBreedingSelector().select(arguments, this, arguments.goal()));

        for (int i = 0; i < arguments.breedingPoolSize(); i+=2) {
            O father = selected.get(i);
            O mother = selected.get(i+1);
            List<O> children = father.cross(mother);

            bred.addAll(children);
        }

        this.organisms.addAll(bred);
    }

    @Override
    public void kill(Arguments arguments) {
        List<O> selected = new ArrayList<>(getBreedingSelector().select(arguments, this, arguments.goal().opposite()));

        Set<O> organisms = new HashSet<>(organisms());
        organisms.removeAll(selected);

        this.organisms = new ArrayList<>(organisms);
    }

}
