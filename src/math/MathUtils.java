package math;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.exp;

public class MathUtils {

    public static Double sig(Double u) {
        return 1 / (1 + exp(-u));
    }

    public static Double round(Double num) {
        return BigDecimal.valueOf(num).setScale(0, RoundingMode.HALF_UP).doubleValue();
    }
}
