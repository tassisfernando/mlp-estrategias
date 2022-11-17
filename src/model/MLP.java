package model;

import math.MathUtils;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.arraycopy;

public class MLP {
    private int in, out, qtdH;

    private double[][] wh;
    private double[][] wo;
    private double ni;

    private final static double RANGE_MIN = -0.3;
    private final static double RANGE_MAX = 0.3;
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public MLP(int in, int out, int qtdH, double ni) {
        this.in = in;
        this.out = out;
        this.qtdH = qtdH;
        this.ni = ni;

        this.wh = new double[in + 1][qtdH];
        this.wo = new double[qtdH + 1][out];

        //inicializar os pesos: pegar do código anterior
        this.gerarRandomW(this.wh);
        this.gerarRandomW(this.wo);
    }

    private void gerarRandomW(double[][] w) {
        for(int i = 0; i < w.length; i++) {
            for(int j = 0; j < w[0].length; j++) {
                w[i][j] = random.nextDouble(RANGE_MIN, RANGE_MAX);
            }
        }
    }

    public double[] treinar(double[] xIn, double[] y) {
        double[] x = new double[xIn.length + 1];
        //copia do Xin para o X
        generateXArray(xIn, x);

        // Calcula a saída da camada intermediária
        double[] hiddenOut = new double[qtdH + 1]; // representa a saída da camada intermediária

        for (int h = 0; h < qtdH; h++) {
            double u = 0;
            for (int i = 0; i < x.length; i++) {
                u += x[i] * wh[i][h];
            }
            hiddenOut[h] = MathUtils.sig(u);;
        }
        hiddenOut[qtdH] = 1;

        // calcula a saida obtida
        double[] teta = new double[out];
        for (int j = 0; j < teta.length; j++) {
            double u = 0;
            for (int h = 0; h < hiddenOut.length; h++) {
                u += hiddenOut[h] * wo[h][j];
            }
            teta[j] = MathUtils.sig(u);
        }

        // Calcula os deltas
        double[] deltaO = new double[out];
        for (int j = 0; j < out; j++) {
            deltaO[j] = teta[j] * (1 - teta[j]) * (y[j] - teta[j]);
        }

        double[] deltaH = new double[qtdH];
        for (int h = 0; h < qtdH; h++) {
            double soma = calculaSomatorioPesos(deltaO, h);

            deltaH[h] = hiddenOut[h] * (1 - hiddenOut[h]) * soma;
        }

        // Ajuste dos pesos da camada intermediária
        // peso WHij += ni * deltaH[j] * xi; (Dois for aninhados -> pra i e j)
        for (int i = 0; i < wh.length; i++) {
            for (int h = 0; h < wh[0].length; h++) {
                wh[i][h] += ni * deltaH[h] * x[i];
            }
        }

        // Ajuste dos pesos da saída
        // peso WTETAhj += ni * deltaTetaj * Hh; (Dois for aninhados -> pra h e j)
        for (int h = 0; h < wo.length; h++) {
            for (int j = 0; j < wo[0].length; j++) {
                wo[h][j] += ni * deltaO[j] * hiddenOut[h];
            }
        }

        return teta;
    }

    public double[] executar(double[] xIn) {
        double[] x = new double[xIn.length + 1];
        //copia do Xin para o X
        generateXArray(xIn, x);

        // Calcula a saída da camada intermediária
        double[] hiddenOut = new double[qtdH + 1]; // representa a saída da camada intermediária

        for (int h = 0; h < qtdH; h++) {
            double u = 0;
            for (int i = 0; i < x.length; i++) {
                u += x[i] * wh[i][h];
            }
            hiddenOut[h] = MathUtils.sig(u);;
        }
        hiddenOut[qtdH] = 1;

        // calcula a saida obtida
        double[] teta = new double[out];
        for (int j = 0; j < teta.length; j++) {
            double u = 0;
            for (int h = 0; h < hiddenOut.length; h++) {
                u += hiddenOut[h] * wo[h][j];
            }
            teta[j] = MathUtils.sig(u);
        }

        return teta;
    }

    private double calculaSomatorioPesos(double[] deltaO, int h) {
        double soma = 0d;
        for(int j = 0; j < out; j++) {
            soma += deltaO[j] * wo[h][j];
        }

        return soma;
    }

    private void generateXArray(double[] xIn, double[] x) {
        arraycopy(xIn, 0, x, 0, xIn.length);
        x[x.length - 1] = 1D;
    }

    public double calculaErroClassificacao(double[] y, double[] out) {
        double[] outTresh = Arrays.stream(out).map(Math::round).toArray();
        double soma = 0d;
        for (int i = 0; i < y.length; i++) {
            soma += Math.abs(outTresh[i] - y[i]);
        }
        return soma > 0 ? 1 : 0;
    }
}
