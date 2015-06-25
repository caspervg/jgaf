package net.caspervg.jgaf;

import net.caspervg.jgaf.step.Provider;
import net.caspervg.jgaf.step.breeder.BasicBreederFactory;
import net.caspervg.jgaf.step.killer.BasicKillerFactory;
import net.caspervg.jgaf.step.provider.ProviderBuilder;
import net.caspervg.jgaf.step.selector.TournamentSelectorFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class AlgorithmTest {

    @Test
    public void testAlgorithm() throws Exception {
        String optimum = "1111000000000000000000000000000000000000000000000000000000001111";

        Provider<Integer, String> provider = ProviderBuilder.<Integer, String>aProvider()
                .withGoal(new Goal.Maximum())
                .withFitter(organism -> {
                    if (organism.length() != optimum.length()) return -1;

                    int fitness = 0;
                    for (int i = 0; i < optimum.length(); i++) {
                        if (organism.charAt(i) == optimum.charAt(i)) {
                            fitness++;
                        }
                    }

                    return fitness;
                })
                .withCreator(arguments -> {
                    Set<String> organisms = new HashSet<>();
                    while (organisms.size() < arguments.populationSize()) {
                        String organism = "";
                        for (int i = 0; i < optimum.length(); i++) {
                            organism += (Math.random() > 0.5) ? "1" : "0";
                        }
                        organisms.add(organism);
                    }
                    return new Population.Default<>(organisms);
                })
                .withMutator((arguments, organism) -> {
                    String mutated = "";
                    Random random = new Random();
                    for (int i = 0; i < organism.length(); i++) {
                        if (random.nextDouble() >= arguments.maximumMutationAmount().doubleValue()) {
                            if (organism.charAt(i) == '1') mutated += "0";
                            else mutated += "1";
                        } else {
                            mutated += organism.charAt(i);
                        }
                    }
                    return mutated;
                })
                .withSelectorFactory(new TournamentSelectorFactory<>())
                .withCrosser(parents -> {
                    Random random = new Random();
                    int divider = random.nextInt(parents.get(0).length());
                    String ch1 = "";
                    String ch2 = "";
                    for (int i = 0; i < parents.get(0).length(); i++) {
                        if (i < divider) {
                            ch1 += parents.get(0).charAt(i);
                            ch2 += parents.get(1).charAt(i);
                        } else {
                            ch1 += parents.get(1).charAt(i);
                            ch2 += parents.get(0).charAt(i);
                        }
                    }
                    ArrayList<String> children = new ArrayList<>(2);
                    children.add(ch1);
                    children.add(ch2);
                    return children;
                })
                .withBreederFactory(new BasicBreederFactory<>())
                .withKillerFactory(new BasicKillerFactory<>())
                .withOptimizerFactory(new OptimizerFactory<>())
                .build();

        Solution<String> solution = new GeneticAlgorithm.Default<String>().run(new Arguments.Default(), provider);

        assertEquals(new Arguments.Default().populationSize(), solution.getFinalPopulation().size());

        System.out.println("FIT  : " + solution.getBestFitness());
        System.out.println("BEST : " + solution.getBestOrganism());
        System.out.println("OPTI : " + optimum);
    }

}
