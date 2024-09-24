package org.example;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    //usr + key for NASA server
    public static final String REMOTE_SERVER_URL = "https://api.nasa.gov/planetary/apod?api_key=6MMbh7SpNPsk4rTYgq6xJoeYPlYp2w5ZjcImDPag";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Gleb Papulovsky")
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(10000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet(REMOTE_SERVER_URL);
        //request.setHeader(HttpHeaders.ACCEPT);
        CloseableHttpResponse response = httpClient.execute(request);

        //Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        String body = new String(response.getEntity().getContent().readAllBytes());
        String photoUrl = "";
        String[] strs = body.split("\"");
        for (String str : strs) {
            if (str.contains(".jpg")) {
                photoUrl = str;
                break;
            }
        }
        System.out.println("Photos url: " + photoUrl);
//        HttpGet httpRequest=new HttpGet(photoUrl);
//        CloseableHttpResponse httpResponse= httpClient.execute( httpRequest);
//        System.out.println(Arrays.toString(httpResponse.getEntity().getContent().readAllBytes()));
        try (InputStream in = new URL(photoUrl).openStream()) {
            Files.copy(in, Paths.get("G://image.jpg"));
        }


    }

}