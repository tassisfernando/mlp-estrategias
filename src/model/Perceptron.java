package model;

import math.MathUtils;

import java.util.Random;

import static java.lang.System.arraycopy;

public class Perceptron {
    private double ni;
    private int qtdIn;
    private int qtdOut;
    private double[][] w;

    private final static double RANGE_MIN = -0.03;
    private final static double RANGE_MAX = 0.03;

    public Perceptron(int qtdIn, int qtdOut, double ni) {
        this.qtdIn = qtdIn;
        this.qtdOut = qtdOut;
        this.ni = ni;
        this.w = new double[qtdIn + 1][qtdOut];

        this.gerarRandomW();
    }

    private void gerarRandomW() {
        Random random = new Random();
        for(int i = 0; i < w.length; i++) {
            for(int j = 0; j < w[0].length; j++) {
                w[i][j] = random.nextDouble() * (RANGE_MAX - RANGE_MIN) + RANGE_MIN;
            }
        }
    }

    public double[] learn(double[] xIn, double[] y) {
        double[] x = new double[xIn.length + 1];
        double[] out = new double[y.length];

        generateXArray(xIn, x);

        for (int j = 0; j < y.length; j++) {
            double u = 0;
            for (int i = 0; i < x.length; i++) {
                u += x[i] * w[i][j];
            }
            out[j] = MathUtils.sig(u);
        }

        for (int j = 0; j < y.length; j++) {
            for (int i = 0; i < x.length; i++) {
                w[i][j] += ni * (y[j] - out[j]) * x[i];
            }
        }
        return out;
    }

    public void generateXArray(double[] xIn, double[] x) {
        arraycopy(xIn, 0, x, 0, xIn.length);
        x[x.length - 1] = 1D;
    }
}