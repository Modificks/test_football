package com.example.demo.viewLayer.mapper;

import com.example.demo.domain.entity.Player;
import com.example.demo.viewLayer.dto.PlayerDTO;
import com.example.demo.viewLayer.dto.PlayerRegisterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper extends BaseMapper<Player, PlayerDTO> {

    Player toEntity(PlayerRegisterDTO playerRegisterDTO);
}
