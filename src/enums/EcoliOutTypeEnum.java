package enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EcoliOutTypeEnum {
    CP(new double[] {0, 0, 0}),
    IM(new double[] {0, 0, 1}),
    IMS(new double[] {0, 1, 0}),
    IML(new double[] {0, 1, 1}),
    IMU(new double[] {1, 0, 0}),
    OM(new double[] {1, 0, 1}),
    OML(new double[] {1, 1, 0}),
    PP(new double[] {1, 1, 1});

    private final double[] value;

    EcoliOutTypeEnum(double[] value) {
        this.value = value;
    }

    public double[] getValue() {
        return value;
    }

    public static double[] getValuebyName(String name) {
        List<EcoliOutTypeEnum> found = Arrays.stream(values()).filter(item -> item.name().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        return found.isEmpty() ? null : found.get(0).value;
    }
}
