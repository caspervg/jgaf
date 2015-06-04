package net.caspervg.jgaf;

import java.util.List;

public class Solution<O extends Organism> {
    Number bestFitness;
    O bestOrganism;
    List<O> finalPopulation;

    public Solution(Number bestFitness, O bestOrganism, List<O> finalPopulation) {
        this.bestFitness = bestFitness;
        this.bestOrganism = bestOrganism;
        this.finalPopulation = finalPopulation;
    }

    public Number getBestFitness() {
        return bestFitness;
    }

    public O getBestOrganism() {
        return bestOrganism;
    }

    public List<O> getFinalPopulation() {
        return finalPopulation;
    }
}
