package net.caspervg.jgaf;

public interface Arguments {

    default long populationSize() {
        return 1000L;
    }

    default long numIterations() {
        return 1000;
    }

    default double maximumMutationAmount() {
        return 0.1D;
    }

    default long breedingPoolSize() {
        return populationSize() / 10;
    }

    default long tradingPoolSize() {
        return breedingPoolSize() / 2;
    }

    default long killingPoolSize() {
        return breedingPoolSize();
    }

    class Default implements Arguments {
    }
}
