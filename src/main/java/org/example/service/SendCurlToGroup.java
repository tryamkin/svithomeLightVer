package org.example.service;

import org.zeroturnaround.exec.ProcessExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import static org.example.config.Resources.BotToken;
import static org.example.config.Resources.chatIdGroup;

public class SendCurlToGroup {

    private static String textMsg = "-1002167397948";
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        new ProcessExecutor().command("cmd", "curl"," -s \"https://api.telegram.org/bot6984490398:AAE4oJZC7h0b_KeqnCQ8VG1wnGIXV1wO-EY/sendMessage?chat_id=1326899332&text=simple_text1").execute();
        }

     public static void sendMsg() throws IOException, InterruptedException, TimeoutException {
         new ProcessExecutor().command("curl", "-s", String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",BotToken,chatIdGroup,textMsg)).execute();
     }

    }

