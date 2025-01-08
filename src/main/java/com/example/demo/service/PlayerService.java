package com.example.demo.service;

import com.example.demo.viewLayer.dto.PlayerDTO;
import com.example.demo.viewLayer.dto.PlayerRegisterDTO;

import java.util.List;

public interface PlayerService {

    PlayerDTO createPlayer(PlayerRegisterDTO PlayerRegisterDTO);

    List<PlayerDTO> getAll();

    PlayerDTO getById(long id);

    PlayerDTO updatePlayer(PlayerDTO playerDTO);

    void deleteById(long id);
}
