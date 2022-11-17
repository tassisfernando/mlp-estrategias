package model;

import enums.EcoliOutTypeEnum;
import utils.RandomUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Classe para ler a base de dados Ecoli
 * Dados de saída:
 * cp
 * im
 * imS
 * imL
 * imU
 * om
 * omL
 * pp
 *
 */
public class Database {
    private double[][][] dataTreino;
    private double[][][] dataTeste;
    private double[] input;
    private double[] output;

    private static final int MAX_INDEX_ECOLI = 336;
    private static final int MAX_INDEX_TREINO = 252;
    private static final int MAX_INDEX_TESTE = MAX_INDEX_ECOLI - MAX_INDEX_TREINO;

    public Database() {
        readData();
    }

    private void readData() {
        dataTreino = new double[MAX_INDEX_TREINO][7][];
        dataTeste = new double[MAX_INDEX_TESTE][7][];
        input = new double[7];
        output = new double[3];

        List<Integer> testePos = RandomUtils.getRandomNumbers(MAX_INDEX_TESTE, MAX_INDEX_ECOLI);

        try {
            File file = new File("files/ecoli.data");
            Scanner scn = new Scanner(file);
            int iTreino = 0;
            int iTeste = 0;
            int cont = 0;

            while (scn.hasNext()) {
                String row = scn.nextLine();
                readRow(row);
                if (testePos.contains(cont)) {
                    dataTeste[iTeste][0] = input;
                    dataTeste[iTeste][1] = output;
                    iTeste++;
                } else {
                    dataTreino[iTreino][0] = input;
                    dataTreino[iTreino][1] = output;
                    iTreino++;
                }
                cont++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro: arquivo com dados não encontrado.");
        }
    }

    private void readRow(String row) {
        List<String> stringRow = Arrays.stream(row.split(" ")).collect(Collectors.toList());
        removeInvalidInputs(stringRow);

        output = EcoliOutTypeEnum.getValuebyName(stringRow.get(7).toUpperCase());
        stringRow.remove(7);
        input = stringRow.stream().map(String::trim).mapToDouble(Double::parseDouble).toArray();
    }

    private void removeInvalidInputs(List<String> stringRow) {
        stringRow.removeIf(r -> r.contains("ECOLI"));
        stringRow.removeIf(String::isEmpty);
    }

    public double[][][] getDataTreino() {
        return dataTreino;
    }

    public double[][][] getDataTeste() {
        return dataTeste;
    }
}
