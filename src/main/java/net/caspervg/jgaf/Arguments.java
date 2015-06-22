package net.caspervg.jgaf;

/**
 * Provides arguments to use in the genetic algorithm execution
 */
public interface Arguments {

    /**
     * Size of the population to use. By default, this is {@code 1000}
     *
     * @return Size of the population
     */
    default int populationSize() {
        return 1000;
    }

    /**
     * Number of iterations to run the algorithm. By default, this is {@code 1000}
     *
     * @return Number of iterations
     */
    default int numIterations() {
        return 1000;
    }

    /**
     * Maximum amount of mutation to apply to an organism. By default, this is {@code 0.1D}
     *
     * @return Maximum amount of mutation
     */
    default Number maximumMutationAmount() {
        return 0.1D;
    }

    /**
     * Size of the breeding pool. By default, this is {@code populationSize() / 10}
     *
     * @return Size of the breeding pool
     */
    default int breedingPoolSize() {
        return populationSize() / 10;
    }

    /**
     * Size of the killing pool. By default, this is {@code breedingPoolSize()}
     *
     * @return Size of the killing pool
     */
    default int killingPoolSize() {
        return breedingPoolSize();
    }

    /**
     * Goal of the genetic algoritm. By default, this is {@link net.caspervg.jgaf.Goal.Maximum}.
     * This means that the algorithm will strive to find an organism with the highest possible
     * fitness (maximisation).
     *
     * @return Goal of the algorithm
     */
    default Goal goal() {
        return new Goal.Maximum();
    }

    /**
     * Default set of arguments, using all the defaults outlined in the {@link Arguments} interface
     */
    class Default implements Arguments {
    }
}
