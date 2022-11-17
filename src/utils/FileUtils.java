package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileUtils {

    public final static String ARQUIVO_BASE_TREINO = "files/erroTreino.txt";
    public final static String ARQUIVO_BASE_TREINO_CLASS = "files/erroTreinoClass.txt";
    public final static String ARQUIVO_BASE_TESTE = "files/erroTeste.txt";
    public final static String ARQUIVO_BASE_TESTE_CLASS = "files/erroTesteClass.txt";
    public final static String ARQUIVO_EPOCAS = "files/epocas.txt";

    public static void fileWriter(String arquivo, String conteudo) throws IOException {
        FileWriter fw = new FileWriter(arquivo);
        PrintWriter printWriter = new PrintWriter(fw);
        conteudo = conteudo.replace(".", ",");
        printWriter.println(conteudo);
        printWriter.close();
    }

    public static void clearFiles() throws IOException {
        FileWriter fw = new FileWriter(ARQUIVO_BASE_TREINO);
        fw = new FileWriter(ARQUIVO_BASE_TREINO_CLASS);
        fw = new FileWriter(ARQUIVO_BASE_TESTE);
        fw = new FileWriter(ARQUIVO_BASE_TESTE_CLASS);
        fw = new FileWriter(ARQUIVO_EPOCAS);
    }
}
