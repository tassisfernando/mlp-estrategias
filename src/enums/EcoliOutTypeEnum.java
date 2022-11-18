package enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static math.MathUtils.UM_DESLOCADO;
import static math.MathUtils.ZERO_DESLOCADO;

public enum EcoliOutTypeEnum {
    CP(new double[] {ZERO_DESLOCADO, ZERO_DESLOCADO, ZERO_DESLOCADO}),
    IM(new double[] {ZERO_DESLOCADO, ZERO_DESLOCADO, UM_DESLOCADO}),
    IMS(new double[] {ZERO_DESLOCADO, UM_DESLOCADO, ZERO_DESLOCADO}),
    IML(new double[] {ZERO_DESLOCADO, UM_DESLOCADO, UM_DESLOCADO}),
    IMU(new double[] {UM_DESLOCADO, ZERO_DESLOCADO, ZERO_DESLOCADO}),
    OM(new double[] {UM_DESLOCADO, ZERO_DESLOCADO, UM_DESLOCADO}),
    OML(new double[] {UM_DESLOCADO, UM_DESLOCADO, ZERO_DESLOCADO}),
    PP(new double[] {UM_DESLOCADO, UM_DESLOCADO, UM_DESLOCADO});

    private final double[] value;

    EcoliOutTypeEnum(double[] value) {
        this.value = value;
    }

    public static double[] getValuebyName(String name) {
        List<EcoliOutTypeEnum> found = Arrays.stream(values()).filter(item -> item.name().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        return found.isEmpty() ? null : found.get(0).value;
    }
}
