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
    public ResponseEntity<GameModel> createGame(GameModel gameRequest) {
        return ResponseEntity.ok(gameService.createGame(gameRequest));
    }

    @Override
    public ResponseEntity<GameModel> getGame(Long gameId) {
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

    @Override
    public ResponseEntity<GameModel> updateGame(Long gameId, GameModel gameRequest) {
        return ResponseEntity.ok(gameService.updateGame(gameId, gameRequest));
    }

    @Override
    public ResponseEntity<Void> deleteGame(Long gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
