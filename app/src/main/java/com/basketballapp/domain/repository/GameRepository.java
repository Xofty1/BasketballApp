package com.basketballapp.domain.repository;

import com.basketballapp.datasource.network.WebScraper;
import com.basketballapp.domain.model.Game;
import com.basketballapp.domain.model.Team;
import com.basketballapp.domain.repository.callback.RepositoryCallback;

import java.util.List;

public class GameRepository {
    private final WebScraper webScraper;

    public GameRepository() {
        this.webScraper = new WebScraper();
    }

    public void fetchGames(int year, int month, RepositoryCallback<Game> callback) {
        // Вызов WebScraper для получения данных
        webScraper.fetchGames(year, month, new WebScraper.WebScraperCallback<Game>() {
            @Override
            public void onResult(List<Game> games) {
                // Успешно загруженные данные
                callback.onSuccess(games);
            }

            @Override
            public void onError(String errorMessage) {
                // Ошибка при загрузке данных
                callback.onFailure(errorMessage);
            }
        });
    }

    public void fetchTeams(int year, boolean isE, RepositoryCallback<Team> callback) {
        // Вызов WebScraper для получения данных
        webScraper.fetchTeams(year, isE, new WebScraper.WebScraperCallback<>() {
            @Override
            public void onResult(List<Team> games) {
                // Успешно загруженные данные
                callback.onSuccess(games);
            }

            @Override
            public void onError(String errorMessage) {
                // Ошибка при загрузке данных
                callback.onFailure(errorMessage);
            }
        });
    }
}
