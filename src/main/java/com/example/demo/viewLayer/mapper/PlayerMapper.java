package com.example.demo.viewLayer.mapper;

import com.example.demo.domain.entity.Player;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerDTO;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper extends BaseMapper<Player, PlayerDTO> {

    Player toEntity(PlayerRegisterDTO playerRegisterDTO);
}
