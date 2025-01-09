package com.basketballapp.domain.repository.callback;

import java.util.List;

public interface RepositoryCallback<T> {
    void onSuccess(List<T> games);

    void onFailure(String errorMessage);
}

