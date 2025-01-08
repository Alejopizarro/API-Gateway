package com.example.game_services_api.service;

import com.example.game_services_api.commons.entities.GameModel;

public interface GameService {
    GameModel createGame(GameModel gameRequest);
    GameModel getGame(Long gameId);
    GameModel updateGame(Long gameId, GameModel gameRequest);
    void deleteGame(Long gameId);
}
