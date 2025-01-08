package com.basketballapp.datasource.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.basketballapp.domain.model.Game;
import com.basketballapp.domain.model.Team;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebScraper {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static Map<String, String> teamCodes;


    public interface WebScraperCallback<T> {
        void onResult(List<T> list);

        void onError(String errorMessage);
    }

    public void fetchGames(int year, int month, WebScraperCallback callback) {
        Log.d("TAG52", "start FETCH GAMES");
        executor.execute(() -> {
            try {
                // Формируем URL с учётом года и месяца
                String monthString = getMonthString(month);
                String url = "https://www.basketball-reference.com/leagues/NBA_" + year + "_games-" + monthString + ".html";
                Document document = Jsoup.connect(url).get();
                List<Game> games = new ArrayList<>();
                Element table = document.selectFirst("table#schedule");

                if (table != null) {
                    Elements rows = table.select("tbody > tr");
                    for (Element row : rows) {
                        if (row.hasClass("thead")) continue;
                        Log.d("TAG52", "parsing");
                        // Извлекаем данные из строк таблицы
                        String date = row.select("th[data-stat=date_game]").text();
                        String homeTeam = row.select("td[data-stat=home_team_name]").text();
                        String awayTeam = row.select("td[data-stat=visitor_team_name]").text();
                        String score = row.select("td[data-stat=home_pts]").text() + " - " +
                                row.select("td[data-stat=visitor_pts]").text();

                        // Получаем изображения команд
                        String homeTeamImage = "https://cdn.ssref.net/req/202412261/tlogo/bbr/" + getTeamCode(homeTeam) + ".png";
                        String awayTeamImage = "https://cdn.ssref.net/req/202412261/tlogo/bbr/" + getTeamCode(awayTeam) + ".png";
                        games.add(new Game(date, homeTeam, awayTeam, score, homeTeamImage, awayTeamImage));
                    }
                }

                // Возвращаем результат на главный поток
                mainThreadHandler.post(() -> callback.onResult(games));
            } catch (IOException e) {
                mainThreadHandler.post(() -> callback.onError("Ошибка загрузки данных: " + e.getMessage()));
            }
        });
    }

    private String getTeamCode(String teamName) {
        initializeTeamCodes();
        return teamCodes.getOrDefault(teamName, "");
    }

    private static void initializeTeamCodes() {
        if (teamCodes == null) {
            teamCodes = new TeamCodeScraper().fetchTeamCodes();
        }
    }

    /**
     * Преобразует номер месяца в строку, соответствующую формату URL.
     *
     * @param month Номер месяца (1-12)
     * @return Название месяца в нижнем регистре
     */
    private String getMonthString(int month) {
        String[] months = {
                "january", "february", "march", "april", "may",
                "june", "july", "august", "september", "october", "november", "december",
        };
        return months[month - 1];
    }

    public void fetchTeams(int year, WebScraperCallback<Team> callback) {
        Log.d("TAG52", "start FETCH TEAMS");
        executor.execute(() -> {
            try {
                // Используем StandingScraper для получения списка команд
                StandingScraper standingScraper = new StandingScraper();
                List<Team> teams = standingScraper.fetchTeams(year);

                // Передаем результат обратно на главный поток
                mainThreadHandler.post(() -> callback.onResult(teams));
            } catch (Exception e) {
                mainThreadHandler.post(() -> callback.onError("Ошибка загрузки данных: " + e.getMessage()));
            }
        });
    }
}
