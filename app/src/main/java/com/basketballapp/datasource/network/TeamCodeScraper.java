package com.basketballapp.datasource.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TeamCodeScraper {

    public Map<String, String> fetchTeamCodes() {
        Map<String, String> teamCodes = new HashMap<>();

        try {
            System.out.println("start");
            String url = "https://www.basketball-reference.com/teams/";
            Document document = Jsoup.connect(url).get();

            Elements rows = document.select("table#teams_active tbody > tr");

            for (Element row : rows) {
                // Ищем все ссылки в строке
                Elements links = row.select("a[href^='/teams/']");
                for (Element link : links) {
                    // Получаем полный путь ссылки
                    String href = link.attr("href");

                    // Проверяем, что ссылка действительно ведет к странице команды
                    if (href.contains("/teams/")) {
                        String teamName = link.text(); // Получаем имя команды
                        String teamCode = href.split("/")[2]; // Извлекаем сокращённый код (например, "LAL")
                        System.out.println("teamName: " + teamName + ", teamCode: " + teamCode);
                        teamCodes.put(teamName, teamCode); // Добавляем в Map
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teamCodes;
    }

}