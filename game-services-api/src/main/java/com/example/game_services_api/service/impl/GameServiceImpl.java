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
    public GameModel createGame(GameModel gameRequest) {
        return Optional.of(gameRequest)
                .map(this::mapToEntity)
                .map(gameRepository::save)
                .orElseThrow(()-> new RuntimeException("Error creating game"));
    }

    @Override
    public GameModel getGame(Long gameId) {
        return Optional.of(gameId)
                .flatMap(gameRepository::findById)
                .orElseThrow(()-> new RuntimeException("Error couldn't find game"));
    }

    @Override
    public GameModel updateGame(Long gameId, GameModel gameRequest) {
        return Optional.of(gameId)
                .map(this::getGame)
                .map(updatedGame -> updateEntity(updatedGame, gameRequest))
                .map(gameRepository::save)
                .orElseThrow(()-> new RuntimeException("Error couldn't update game"));
    }

    @Override
    public void deleteGame(Long gameId) {
        Optional.of(gameId)
                .map(this::getGame)
                .ifPresent(gameRepository::delete);
    }


    private GameModel updateEntity(GameModel updatedGame, GameModel gameRequest) {
        updatedGame.setName(gameRequest.getName());
        return updatedGame;
    }


    private GameModel mapToEntity(GameModel gameRequest) {
        return GameModel.builder()
                .name(gameRequest.getName())
                .build();
    }
}
