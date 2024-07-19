package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SendCurlToGroup {
    public static void main(String[] args) {
        }

    public static void testSendMsg(){
        try {
            // Выполнение команды
            Process process = Runtime.getRuntime().exec("bash -c curl -s https://api.telegram.org/bot7116590369:AAHTmFYS9Bgg1LiDF7CmOC7uIWKL7_XBx8s/sendMessage?chat_id=-4242637154&text=simple_cUrl_test_");

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
