package net.caspervg.jgaf;

import org.junit.Test;

import static org.junit.Assert.*;

public class GoalTest {

    @Test
    public void testCompare() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertEquals(0, maximumGoal.compare(3.0, 3.0));
        assertEquals(1, maximumGoal.compare(4.0, 3.0));
        assertEquals(-1, maximumGoal.compare(3.0, 4.0));

        Goal minimumGoal = new Goal.Minimum();
        assertEquals(0, minimumGoal.compare(3.0, 3.0));
        assertEquals(-1, minimumGoal.compare(4.0, 3.0));
        assertEquals(1, minimumGoal.compare(3.0, 4.0));
    }

    @Test
    public void testWorst() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertEquals(new Double(3.0), maximumGoal.worst(3.0, 3.0));
        assertEquals(new Double(2.0), maximumGoal.worst(2.0, 4.0));
        assertEquals(new Double(1.0), maximumGoal.worst(5.0, 1.0));

        Goal minimumGoal = new Goal.Minimum();
        assertEquals(new Double(3.0), minimumGoal.worst(3.0, 3.0));
        assertEquals(new Double(4.0), minimumGoal.worst(2.0, 4.0));
        assertEquals(new Double(5.0), minimumGoal.worst(5.0, 1.0));
    }

    @Test
    public void testBest() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertEquals(new Double(3.0), maximumGoal.best(3.0, 3.0));
        assertEquals(new Double(4.0), maximumGoal.best(2.0, 4.0));
        assertEquals(new Double(5.0), maximumGoal.best(5.0, 1.0));

        Goal minimumGoal = new Goal.Minimum();
        assertEquals(new Double(3.0), minimumGoal.best(3.0, 3.0));
        assertEquals(new Double(2.0), minimumGoal.best(2.0, 4.0));
        assertEquals(new Double(1.0), minimumGoal.best(5.0, 1.0));
    }

    @Test
    public void testBetter() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertFalse(maximumGoal.better(3.0, 3.0));
        assertFalse(maximumGoal.better(2.0, 3.0));
        assertTrue(maximumGoal.better(3.0, 2.0));
        
        assertTrue(maximumGoal.betterOrEqual(3.0, 3.0));
        assertFalse(maximumGoal.betterOrEqual(2.0, 3.0));
        assertTrue(maximumGoal.betterOrEqual(3.0, 2.0));

        Goal minimumGoal = new Goal.Minimum();
        assertFalse(minimumGoal.better(3.0, 3.0));
        assertTrue(minimumGoal.better(2.0, 3.0));
        assertFalse(minimumGoal.better(3.0, 2.0));

        assertTrue(minimumGoal.betterOrEqual(3.0, 3.0));
        assertTrue(minimumGoal.betterOrEqual(2.0, 3.0));
        assertFalse(minimumGoal.betterOrEqual(3.0, 2.0));
    }

    @Test
    public void testWorse() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertFalse(maximumGoal.worse(3.0, 3.0));
        assertTrue(maximumGoal.worse(2.0, 3.0));
        assertFalse(maximumGoal.worse(3.0, 2.0));

        assertTrue(maximumGoal.worseOrEqual(3.0, 3.0));
        assertTrue(maximumGoal.worseOrEqual(2.0, 3.0));
        assertFalse(maximumGoal.worseOrEqual(3.0, 2.0));

        Goal minimumGoal = new Goal.Minimum();
        assertFalse(minimumGoal.worse(3.0, 3.0));
        assertFalse(minimumGoal.worse(2.0, 3.0));
        assertTrue(minimumGoal.worse(3.0, 2.0));

        assertTrue(minimumGoal.worseOrEqual(3.0, 3.0));
        assertFalse(minimumGoal.worseOrEqual(2.0, 3.0));
        assertTrue(minimumGoal.worseOrEqual(3.0, 2.0));
    }

    @Test
    public void testOpposite() throws Exception {
        Goal maximumGoal = new Goal.Maximum();
        assertTrue(maximumGoal.opposite() instanceof Goal.Minimum);

        Goal minimumGoal = new Goal.Minimum();
        assertTrue(minimumGoal.opposite() instanceof Goal.Maximum);
    }
}