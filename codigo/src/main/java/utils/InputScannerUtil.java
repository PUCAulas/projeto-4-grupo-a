package main.java.utils;

import java.util.Scanner;

public class InputScannerUtil {

    /**
     * Inicia scanner padrao
     */
    public static final Scanner scanner = new Scanner(System.in);

    /**
     * Finaliza scanner padrao
     */
    public static void close() {
        scanner.close();
    }
}
