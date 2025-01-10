package com.example.demo.api;

import com.example.demo.service.TeamService;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashSet;


import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class TeamControllerTest {

    @Mock
    private TeamService teamService;

    @InjectMocks
    private TeamController teamController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createTeam() throws Exception {
        TeamDTO teamDTO = new TeamDTO("Manchester United", "England", BigDecimal.valueOf(900000000.000), BigDecimal.valueOf(10.00));

        String teamJson = objectMapper.writeValueAsString(teamDTO);

        mockMvc.perform(post("/team/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isCreated());

        verify(teamService, times(1)).createTeam(argThat(dto ->
                dto.getName().equals(teamDTO.getName()) &&
                        dto.getCountry().equals(teamDTO.getCountry()) &&
                        dto.getBudget().compareTo(teamDTO.getBudget()) == 0
        ));
    }

    @Test
    void getAllTeams() throws Exception {
        TeamDTO team1 = new TeamDTO("Manchester United", "England", BigDecimal.valueOf(900000000.000), BigDecimal.valueOf(10.00));
        TeamDTO team2 = new TeamDTO("Real Madrid", "Spain", BigDecimal.valueOf(950000000.000), BigDecimal.valueOf(12.00));

        when(teamService.getTeams()).thenReturn(new LinkedHashSet<>(Arrays.asList(team1, team2)));

        mockMvc.perform(get("/team/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(team1.getName()))
                .andExpect(jsonPath("$[1].name").value(team2.getName()));
    }

    @Test
    void getTeamByName() throws Exception {
        TeamDTO teamDTO = new TeamDTO("Manchester United", "England", BigDecimal.valueOf(900000000.000), BigDecimal.valueOf(10.00));

        when(teamService.getByName("Manchester United")).thenReturn(teamDTO);

        mockMvc.perform(get("/team/get")
                        .param("name", "Manchester United")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(teamDTO.getName()))
                .andExpect(jsonPath("$.country").value(teamDTO.getCountry()));
    }

    @Test
    void updateTeam() throws Exception {
        TeamDTO updatedTeam = new TeamDTO("Manchester United", "England", BigDecimal.valueOf(900000000.000), BigDecimal.valueOf(7.00));

        String teamJson = objectMapper.writeValueAsString(updatedTeam);

        when(teamService.updateTeam(any(TeamDTO.class))).thenReturn(updatedTeam);

        mockMvc.perform(patch("/team/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(teamJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedTeam.getName()))
                .andExpect(jsonPath("$.budget").value(updatedTeam.getBudget()));
    }

    @Test
    void deleteTeam() throws Exception {
        long teamId = 1L;

        mockMvc.perform(delete("/team/delete")
                        .param("id", String.valueOf(teamId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(teamService, times(1)).deleteByName(teamId);
    }
}
