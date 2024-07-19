package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Check {
    public static void main(String[] args) {
        }

    public static void testSendMsg(){
        try {
            // Выполнение команды
            Process process = Runtime.getRuntime().exec("bash -c ls -l");

            // Чтение вывода команды
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Ожидаем завершения процесса и получаем его код возврата
            System.out.println( process.getInputStream());
            int exitCode = process.waitFor();
            System.out.println("\nError code : " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
