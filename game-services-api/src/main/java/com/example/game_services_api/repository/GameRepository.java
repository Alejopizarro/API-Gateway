package com.example.game_services_api.repository;

import com.example.game_services_api.commons.entities.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<GameModel, Long> {
    @Override
    List<GameModel> findAllById(Iterable<Long> longs);
}
