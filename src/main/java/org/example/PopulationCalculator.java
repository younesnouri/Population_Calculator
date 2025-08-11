package org.example;

public class PopulationCalculator {
    public static double power(double base, double exponent) {
        double result = 1.0;
        for (int i = 0; i < (int) exponent; i++) {
            result *= base;
        }
        double fraction = exponent - (int) exponent;
        if (fraction > 0) {
            result *= exp(fraction * log(base));
        }
        return result;
    }

    public static double root(double a, double n) {
        return exp(log(a) / n);
    }

    public static double log(double x) {
        if (x <= 1) {
            throw new ArithmeticException("Log input must be > 1.");
        }
        double result = 0.0;
        double y = (x - 1) / (x + 1);
        for (int i = 1; i < 1000; i += 2) {
            double term = 1.0 / i;
            result += term * power(y, i);
        }
        return 2 * result;
    }

    public static double exp(double x) {
        double result = 1.0;
        double term = 1.0;
        for (int i = 1; i < 30; i++) {
            term *= x / i;
            result += term;
        }
        return result;
    }

    /**
     * Calculate projected population A = a * b^x
     */
    public static double calculateFuturePopulation(double a, double b, double x) {
        if (b <= 1.0) throw new ArithmeticException("Growth factor cannot be 1 or less.");
        return a * power(b, x);
    }

    /**
     * Calculate growth rate r = (A/a)^(1/x) - 1
     */
    public static double calculateGrowthRate(double aa, double a, double x) {
        return root(aa / a, x) - 1;
    }

    /**
     * Calculate time x = log(A/a) / log(b)
     */
    public static double calculateTime(double aa, double a, double b) {
        if (b <= 1.0) throw new ArithmeticException("Growth factor cannot be 1 or less.");
        return log(aa / a) / log(b);
    }
}
