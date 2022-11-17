package runner;

import model.Database;
import model.MLP;
import utils.FileUtils;

import java.io.IOException;

import static java.lang.Math.abs;
import static utils.FileUtils.*;

public class MLPRunner {

    private static final int N_EPOCAS = 100000;
    private static final StringBuilder errosAproxTreino = new StringBuilder();
    private static final StringBuilder errosClassTreino = new StringBuilder();
    private static final StringBuilder errosAproxTeste = new StringBuilder();
    private static final StringBuilder errosClassTeste = new StringBuilder();
    private static final StringBuilder epocas = new StringBuilder();

    public static void main(String[] args) throws IOException {
        FileUtils.clearFiles();

        final double NI = 0.001;
        final int QTD_H = 6;

        Database dataModel = new Database();
        double[][][] dataTreino = dataModel.getDataTreino();
        double[][][] dataTeste = dataModel.getDataTeste();

        MLP mlp = new MLP(dataTreino[0][0].length, dataTreino[0][1].length, QTD_H, NI);
        double erroEpAproxTreino, erroEpClassTreino, erroEpAproxTeste, erroEpClassTeste;

        for(int e = 0; e < N_EPOCAS; e++) {
            erroEpAproxTreino = 0D;
            erroEpClassTreino = 0D;
            for (double[][] sample : dataTreino) {
                double[] x = sample[0];
                double[] y = sample[1];
                double[] out = mlp.treinar(x, y);

                erroEpAproxTreino += sumErro(y, out);
                erroEpClassTreino += mlp.calculaErroClassificacao(y, out);
            }

            erroEpAproxTeste = 0D;
            erroEpClassTeste = 0D;
            for (double[][] sample : dataTeste) {
                double[] x = sample[0];
                double[] y = sample[1];
                double[] out = mlp.executar(x);

                erroEpAproxTeste += sumErro(y, out);
                erroEpClassTeste += mlp.calculaErroClassificacao(y, out);
            }

            erroEpClassTreino = erroEpClassTreino/dataTreino.length;
            erroEpClassTeste = erroEpClassTeste/dataTeste.length;

            System.out.printf("Época: %s - ", (e+1));
            System.out.printf("Er. Aprox Treino: %6f - ", erroEpAproxTreino);
            System.out.printf("Er. Class Treino: %6f - ", erroEpClassTreino);
            System.out.printf("Er. Aprox Teste: %6f - ", erroEpAproxTeste);
            System.out.printf("Er. Class Teste: %6f\n", erroEpClassTeste);
            escreveArquivos(e+1, erroEpAproxTreino, erroEpClassTreino, erroEpAproxTeste, erroEpClassTeste);
        }
    }

    private static void escreveArquivos(int epoca, Double erroAproxTreino, Double erroClassTreino, Double erroAproxTeste,
                                        Double erroClassTeste) {
        errosAproxTreino.append(erroAproxTreino).append("\n");
        errosClassTreino.append(erroClassTreino).append("\n");
        errosAproxTeste.append(erroAproxTeste).append("\n");
        errosClassTeste.append(erroClassTeste).append("\n");
        epocas.append(epoca).append("\n");

        if (epoca == N_EPOCAS) {
            try {
                FileUtils.fileWriter(ARQUIVO_BASE_TREINO, errosAproxTreino.toString());
                FileUtils.fileWriter(ARQUIVO_BASE_TREINO_CLASS, errosClassTreino.toString());
                FileUtils.fileWriter(ARQUIVO_BASE_TESTE, errosAproxTeste.toString());
                FileUtils.fileWriter(ARQUIVO_BASE_TESTE_CLASS, errosClassTeste.toString());
                FileUtils.fileWriter(ARQUIVO_EPOCAS, epocas.toString());
            } catch (IOException e) {
                System.out.println("Erro ao escrever arquivo: " + e.getMessage());
            }
        }
    }

    private static double sumErro(double[] y, double[] out) {
        double sum = 0D;
        for(int i = 0; i < y.length; i++) {
            sum += abs(y[i] - out[i]);
        }

        return sum;
    }
}
