package net.caspervg.jgaf;

/**
 * Provides the solution of a genetic algorithm
 *
 * @param <O> Type of the organism
 */
public class Solution<O> {
    Number bestFitness;
    O bestOrganism;
    Population<O> finalPopulation;

    /**
     * Creates a new solution
     *
     * @param bestFitness Best fitness that was found
     * @param bestOrganism Best organism that was found
     * @param finalPopulation Final population
     */
    public Solution(Number bestFitness, O bestOrganism, Population<O> finalPopulation) {
        this.bestFitness = bestFitness;
        this.bestOrganism = bestOrganism;
        this.finalPopulation = finalPopulation;
    }

    /**
     * Returns the best fitness that was found
     *
     * @return Best fitness
     */
    public Number getBestFitness() {
        return bestFitness;
    }

    /**
     * Returns the best organism that was found
     *
     * @return Best organism
     */
    public O getBestOrganism() {
        return bestOrganism;
    }

    /**
     * Returns the final population
     *
     * @return Final population
     */
    public Population<O> getFinalPopulation() {
        return finalPopulation;
    }
}
