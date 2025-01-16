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
                .map(request -> mapToEntity(request, userId))
                .map(gameRepository::save)
                .orElseThrow(() -> new RuntimeException("Error creating game"));
    }

    @Override
    public GameModel getGame(String userId, Long gameId) {
        return gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId())))
                .orElseThrow(() -> new RuntimeException("Error couldn't find game"));
    }

    @Override
    public GameModel updateGame(String userId, Long gameId, GameModel gameRequest) {
        return gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId())))
                .map(existingGame -> updateEntity(existingGame, gameRequest))
                .map(gameRepository::save)
                .orElseThrow(() -> new RuntimeException("Error couldn't update game"));
    }

    @Override
    public void deleteGame(String userId, Long gameId) {
        gameRepository.findById(gameId)
                .filter(game -> userId.equals(String.valueOf(game.getUserId())))
                .ifPresentOrElse(gameRepository::delete,
                        () -> { throw new RuntimeException("Error couldn't delete game"); });
    }

    private GameModel updateEntity(GameModel updatedGame, GameModel gameRequest) {
        if (gameRequest.getName() != null) {
            updatedGame.setName(gameRequest.getName());
        }
        return updatedGame;
    }

    private GameModel mapToEntity(GameModel gameRequest, String userId) {
        return GameModel.builder()
                .name(gameRequest.getName())
                .userId(Long.parseLong(userId))
                .build();
    }
}
