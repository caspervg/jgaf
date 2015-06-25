package net.caspervg.jgaf.organism;

import net.caspervg.jgaf.Arguments;
import net.caspervg.jgaf.Organism;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SameStringOrganism implements Organism<SameStringOrganism, Integer> {
    
    private static final String OPTIMUM = "1111000000000000000000000000000000000000000000000000000000001111";
    private String genome;
    private Random random;
    
    public SameStringOrganism(String genome) {
        this.genome = genome;
        this.random = new Random();
    }

    public String getGenome() {
        return genome;
    }

    @Override
    public void mutate(Arguments arguments) {
        String mutated = "";
        Random random = new Random();
        for (int i = 0; i < genome.length(); i++) {
            if (random.nextDouble() >= arguments.maximumMutationAmount().doubleValue()) {
                if (genome.charAt(i) == '1') mutated += "0";
                else mutated += "1";
            } else {
                mutated += genome.charAt(i);
            }
        }
        this.genome = mutated;
    }

    @Override
    public Integer fitness() {
        if (genome.length() != OPTIMUM.length()) return -1;

        int fitness = 0;
        for (int i = 0; i < OPTIMUM.length(); i++) {
            if (genome.charAt(i) == OPTIMUM.charAt(i)) {
                fitness++;
            }
        }

        return fitness;
    }

    @Override
    public List<SameStringOrganism> cross(List<SameStringOrganism> crossers) {
        int divider = random.nextInt(genome.length());
        String ch1 = "";
        String ch2 = "";
        for (int i = 0; i < genome.length(); i++) {
            if (i < divider) {
                ch1 += genome.charAt(i);
                ch2 += crossers.get(0).getGenome().charAt(i);
            } else {
                ch1 += crossers.get(0).getGenome().charAt(i);
                ch2 += genome.charAt(i);
            }
        }
        ArrayList<SameStringOrganism> children = new ArrayList<>(2);
        children.add(new SameStringOrganism(ch1));
        children.add(new SameStringOrganism(ch2));
        return children;
    }

    @Override
    public String toString() {
        return "SameStringOrganism{" +
                "genome='" + genome + '\'' +
                '}';
    }
}
