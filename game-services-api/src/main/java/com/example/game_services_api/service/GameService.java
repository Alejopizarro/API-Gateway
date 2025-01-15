package com.example.game_services_api.service;

import com.example.game_services_api.commons.entities.GameModel;

public interface GameService {
    GameModel createGame(String id, GameModel gameRequest);
    GameModel getGame(String id, Long gameId);
    GameModel updateGame(String id, Long gameId, GameModel gameRequest);
    void deleteGame(String id, Long gameId);
}
