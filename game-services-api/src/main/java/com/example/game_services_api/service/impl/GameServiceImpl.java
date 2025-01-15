package com.example.game_services_api.service.impl;

import com.example.game_services_api.commons.entities.GameModel;
import com.example.game_services_api.repository.GameRepository;
import com.example.game_services_api.service.GameService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameModel createGame(String userId, GameModel gameRequest) {
        return Optional.of(gameRequest)
                .map(request -> mapToEntity(request, userId)) // Asociamos el usuario al juego
                .map(gameRepository::save)
                .orElseThrow(() -> new RuntimeException("Error creating game"));
    }

    @Override
    public GameModel getGame(String userId, Long gameId) {
        return gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId()))) // Validamos que el usuario sea el dueño
                .orElseThrow(() -> new RuntimeException("Error: Couldn't find game or access is denied"));
    }

    @Override
    public GameModel updateGame(String userId, Long gameId, GameModel gameRequest) {
        return gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId()))) // Validamos que el usuario sea el dueño
                .map(existingGame -> updateEntity(existingGame, gameRequest))
                .map(gameRepository::save)
                .orElseThrow(() -> new RuntimeException("Error: Couldn't update game or access is denied"));
    }

    @Override
    public void deleteGame(String userId, Long gameId) {
        gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId()))) // Validamos que el usuario sea el dueño
                .ifPresentOrElse(gameRepository::delete,
                        () -> { throw new RuntimeException("Error: Couldn't delete game or access is denied"); });
    }

    private GameModel updateEntity(GameModel updatedGame, GameModel gameRequest) {
        if (gameRequest.getName() != null) { // Solo actualizamos campos no nulos
            updatedGame.setName(gameRequest.getName());
        }
        return updatedGame;
    }

    private GameModel mapToEntity(GameModel gameRequest, String userId) {
        return GameModel.builder()
                .name(gameRequest.getName())
                .userId(Long.parseLong(userId)) // Convertimos el String id a Long para almacenarlo en el juego
                .build();
    }
}
