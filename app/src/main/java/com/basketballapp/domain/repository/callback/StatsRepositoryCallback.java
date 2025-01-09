package com.basketballapp.domain.repository.callback;

public interface StatsRepositoryCallback<T> {
    void onSuccess(T stats);

    void onFailure(String errorMessage);
}
