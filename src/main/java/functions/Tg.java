package functions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Tg {
    private static final List<BigDecimal> bernoullis = new ArrayList<>();
    public static double solve(double x, double delta) {
        int i = 1;
        double currentTg = equation(x, i).doubleValue();
        double next = 0;
        do {
            currentTg += next;
            i++;
            next = equation(x, i).doubleValue();
        } while (!(Math.abs(next) < delta/10));
        return currentTg;
    }

    private static BigDecimal equation(double x, int n) {
        BigDecimal upper = BigDecimal.valueOf(2).pow(2*n).multiply(BigDecimal.valueOf(2).pow(2*n).subtract(BigDecimal.ONE))
                .multiply(bernoulli(2*n).abs());
        BigDecimal lower = factorial(2*n);
        BigDecimal multiplier = BigDecimal.valueOf(x).pow(2*n - 1);
        return upper.divide(lower, 10, RoundingMode.DOWN).multiply(multiplier);
    }

    public static BigDecimal bernoulli(int n) {
        if(bernoullis.size() > n)
            return bernoullis.get(n);
        BigDecimal B = BigDecimal.ZERO;
        if (n == 0) {
            bernoullis.add(BigDecimal.ONE);
            return BigDecimal.ONE;
        }
        for (int i = 1; i <= n; i++) {
            BigDecimal coef = biCoefficient(n + 1, i + 1);
            BigDecimal _B = bernoulli(n - i);
            B = B.add(coef.multiply(_B));
        }
        B = B.multiply(BigDecimal.valueOf(-1)).divide(BigDecimal.valueOf(n + 1), 10, RoundingMode.DOWN);
        bernoullis.add(B);
        return B;
    }

    private static BigDecimal biCoefficient(int n, int k) {
        return factorial(n).divide((factorial(k).multiply(factorial(n - k))), 10, RoundingMode.DOWN);
    }

    private static BigDecimal factorial(int n) {
        BigDecimal P = BigDecimal.ONE;
        for (long i = 1; i <= n; i++) {
            P = P.multiply(BigDecimal.valueOf(i));
        }
        return P;
    }
}
