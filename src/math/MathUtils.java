package math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.exp;

public class MathUtils {

    public static double ZERO_DESLOCADO = 0.005;
    public static double UM_DESLOCADO = 0.995;
    public static Double sig(Double u) {
        return 1 / (1 + exp(-u));
    }
}
