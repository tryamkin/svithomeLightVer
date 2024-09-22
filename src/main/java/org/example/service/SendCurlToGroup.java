package org.example.service;

import org.zeroturnaround.exec.ProcessExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import static org.example.config.Resources.BotToken;
import static org.example.config.Resources.chatIdGroup;

public class SendCurlToGroup {

    private static String textMsg = "Your message here )";

     public static void sendMsg() throws IOException, InterruptedException, TimeoutException {
         new ProcessExecutor().command("curl", "-s", String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s",BotToken,chatIdGroup,textMsg)).execute();
     }

    }

