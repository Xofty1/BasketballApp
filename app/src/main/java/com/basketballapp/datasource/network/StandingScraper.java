package com.basketballapp.datasource.network;

import android.util.Log;

import com.basketballapp.domain.model.Team;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StandingScraper {

    public List<Team> fetchTeams(int year, boolean isE) {
        List<Team> teams = new ArrayList<>();

        try {
            String url = "https://www.basketball-reference.com/leagues/NBA_" + year + "_standings.html";
            Document document = Jsoup.connect(url).get();
            Log.d("TAG53", "start");
            // Селектор для таблиц конференций
            Elements tables;
            if (isE)
                tables = document.select("table[id=confs_standings_E]");
            else
                tables = document.select("table[id=confs_standings_W]");
            for (Element table : tables) {
                Log.d("TAG53", "element found");
                Elements rows = table.select("tbody > tr");

                for (Element row : rows) {
                    if (row.hasClass("thead")) continue;
                    Log.d("TAG53", "row found");
                    String name = row.select("th[data-stat=team_name] a").text();
                    String wins = row.select("td[data-stat=wins]").text();
                    String losses = row.select("td[data-stat=losses]").text();
                    String win_loss_pct = row.select("td[data-stat=win_loss_pct]").text();
                    String gb = row.select("td[data-stat=gb]").text();
                    String pts_per_game = row.select("td[data-stat=pts_per_g]").text();
                    String opp_pts_per_game = row.select("td[data-stat=opp_pts_per_g]").text();
                    String srs = row.select("td[data-stat=srs]").text();

                    if (!name.isEmpty()) {
                        teams.add(new Team(name, wins, losses, gb, win_loss_pct, pts_per_game, opp_pts_per_game, srs));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teams;
    }
}
