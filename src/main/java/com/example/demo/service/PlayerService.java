package com.example.demo.service;

import com.example.demo.viewLayer.dto.playerDTOs.PlayerDTO;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerRegisterDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO createPlayer(PlayerRegisterDTO playerRegisterDTO);

    List<PlayerDTO> getAll();

    PlayerDTO getById(long id);

    PlayerDTO updatePlayer(PlayerDTO playerDTO);

    void deleteById(long id);
}
