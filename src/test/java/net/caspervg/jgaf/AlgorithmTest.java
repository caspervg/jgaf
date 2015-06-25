package net.caspervg.jgaf;

import net.caspervg.jgaf.organism.SameStringOrganism;
import net.caspervg.jgaf.population.ListPopulation;
import net.caspervg.jgaf.step.Provider;
import net.caspervg.jgaf.step.provider.BasicProviderBuilder;
import net.caspervg.jgaf.step.selector.TournamentSelector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {

    @Test
    public void testAlgorithm() throws Exception {
        String optimum = "1111000000000000000000000000000000000000000000000000000000001111";

        Provider<SameStringOrganism, Integer> provider = BasicProviderBuilder.<SameStringOrganism, Integer>aProvider()
                .withGoal(new Goal.Minimum())
                .withOptimizer(new Optimizer<>(new Goal.Minimum()))
                .withCreator(arguments -> {
                    Set<SameStringOrganism> organisms = new HashSet<>();
                    while (organisms.size() < arguments.populationSize()) {
                        String genome = "";
                        for (int i = 0; i < optimum.length(); i++) {
                            genome += (Math.random() > 0.5) ? "1" : "0";
                        }
                        organisms.add(new SameStringOrganism(genome));
                    }
                    return new ListPopulation<>(new TournamentSelector<>(5), new ArrayList<>(organisms));
                }).build();

        Solution<SameStringOrganism> solution = new GeneticAlgorithm.Default<>(new Arguments.Default(), provider).run();

        assertEquals(new Arguments.Default().populationSize(), solution.getFinalPopulation().size());

        System.out.println("FIT  : " + solution.getBestFitness());
        System.out.println("BEST : " + solution.getBestOrganism());
        System.out.println("OPTI : " + optimum);
    }

}
