package com.example.game_services_api.controller.impl;

import com.example.game_services_api.commons.entities.GameModel;
import com.example.game_services_api.controller.GameApi;
import com.example.game_services_api.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController implements GameApi {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public ResponseEntity<GameModel> createGame(String id, GameModel gameRequest) {
        return ResponseEntity.ok(gameService.createGame(id, gameRequest));
    }

    @Override
    public ResponseEntity<GameModel> getGame(String id, Long gameId) {
        return ResponseEntity.ok(gameService.getGame(id, gameId));
    }

    @Override
    public ResponseEntity<GameModel> updateGame(String id, Long gameId, GameModel gameRequest) {
        return ResponseEntity.ok(gameService.updateGame(id, gameId, gameRequest));
    }

    @Override
    public ResponseEntity<Void> deleteGame(String id, Long gameId) {
        gameService.deleteGame(id, gameId);
        return ResponseEntity.noContent().build();
    }
}
