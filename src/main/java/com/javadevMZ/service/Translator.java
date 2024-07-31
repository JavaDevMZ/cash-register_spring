package com.javadevMZ.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

@Service
public class Translator {

    public String translateEnglish(String text, Locale target) {
        return translate(text, Locale.ENGLISH, target);
    }

    public String translate(String text, Locale origin, Locale target) {
      String result = text;
        try {
            if(origin.toLanguageTag().equals(target.toLanguageTag())) {
                return text;
            }
            String link = String.format("https://api.mymemory.translated.net/get?q=%s&langpair=%s",
                    URLEncoder.encode(text, StandardCharsets.UTF_8),
                    URLEncoder.encode(origin.toLanguageTag().toUpperCase()+"|"+target.toLanguageTag().toUpperCase(), StandardCharsets.UTF_8)
            );
            System.out.println(link);
            URI uri = null;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri=URI.create(link))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> responseBody = mapper.readValue(response.body(), Map.class);
            result = ((Map<String, String>) responseBody.get("responseData")).get("translatedText");
            if(response.statusCode()!=200){
                result = text;
            }
        }catch (Exception e){
            result = text;
        }
        return result;
    }


    private static String decodeUnicode(String unicodeString) {
        StringBuilder writer = new StringBuilder();
        char[] chars = unicodeString.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\\' && i + 1 < chars.length && chars[i + 1] == 'u') {
                String hex = "" + chars[i + 2] + chars[i + 3] + chars[i + 4] + chars[i + 5];
                writer.append((char) Integer.parseInt(hex, 16));
                i += 5;
            } else {
                writer.append(chars[i]);
            }
        }
        return writer.toString();
    }
}
