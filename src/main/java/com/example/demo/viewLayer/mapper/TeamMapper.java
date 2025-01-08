package com.example.demo.viewLayer.mapper;

import com.example.demo.domain.entity.Team;
import com.example.demo.viewLayer.dto.TeamDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeamMapper extends BaseMapper<Team, TeamDTO> {
}
