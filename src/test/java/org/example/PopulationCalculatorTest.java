package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PopulationCalculatorTest {
    @Test
    void testPower() {
        assertEquals(8.0, PopulationCalculator.power(2, 3), 0.0001);
        assertEquals(9.0, PopulationCalculator.power(3, 2), 0.0001);
        assertEquals(4.5947, PopulationCalculator.power(2, 2.2), 0.0001);
    }

    @Test
    void testRoot() {
        assertEquals(3.0, PopulationCalculator.root(27, 3), 0.01);
        assertEquals(4.0, PopulationCalculator.root(16, 2), 0.01);
    }

    @Test
    void testExp() {
        assertEquals(Math.exp(1), PopulationCalculator.exp(1), 0.01);
        assertEquals(Math.exp(0), PopulationCalculator.exp(0), 0.0001);
    }

    @Test
    void testLog() {
        assertThrows(ArithmeticException.class, () -> PopulationCalculator.log(1));
        assertTrue(PopulationCalculator.log(Math.exp(1)) > 0.99 && PopulationCalculator.log(Math.exp(1)) < 1.01);
    }

    @Test
    void testCalculateFuturePopulation() {
        double result = PopulationCalculator.calculateFuturePopulation(1000, 1.05, 10);
        assertTrue(result > 1600 && result < 1629); // Approx 1628.89
    }

    @Test
    void testCalculateFuturePopulationThrows() {
        assertThrows(ArithmeticException.class, () -> PopulationCalculator.calculateFuturePopulation(1000, 1.0, 5));
        assertThrows(ArithmeticException.class, () -> PopulationCalculator.calculateFuturePopulation(1000, 0.9, 5));
    }

    @Test
    void testCalculateGrowthRate() {
        double r = PopulationCalculator.calculateGrowthRate(1628.89, 1000, 10);
        assertTrue(r > 0.049 && r < 0.051); // approx 5%
    }

    @Test
    void testCalculateTime() {
        double t = PopulationCalculator.calculateTime(1628.89, 1000, 1.05);
        assertTrue(t > 9.9 && t < 10.1);
    }

    @Test
    void testCalculateTimeThrows() {
        assertThrows(ArithmeticException.class, () -> PopulationCalculator.calculateTime(1628.89, 1000, 1.0));
        assertThrows(ArithmeticException.class, () -> PopulationCalculator.calculateTime(1628.89, 1000, 0.9));
    }

}
