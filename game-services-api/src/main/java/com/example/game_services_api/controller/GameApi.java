package com.example.game_services_api.controller;

import com.example.game_services_api.commons.constants.ApiPathConstants;
import com.example.game_services_api.commons.entities.GameModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(ApiPathConstants.V1_ROUTE + ApiPathConstants.GAME_ROUTE)
public interface GameApi {
    @PostMapping(value = "create")
    ResponseEntity<GameModel> createGame(
        @RequestBody GameModel gameRequest
    );
    @GetMapping(value = "/{gameId}")
    ResponseEntity<GameModel> getGame(
            @PathVariable Long gameId
    );
    @PutMapping(value = "/{gameId}")
    ResponseEntity<GameModel> updateGame(
            @PathVariable Long gameId,
            @RequestBody GameModel gameRequest
    );
    @DeleteMapping(value = "/{gameId}")
    ResponseEntity<Void> deleteGame(
            @PathVariable Long gameId
    );
}
