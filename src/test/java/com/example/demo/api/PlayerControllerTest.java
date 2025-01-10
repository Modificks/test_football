package com.example.demo.api;

import com.example.demo.service.PlayerService;
import com.example.demo.viewLayer.dto.TeamDTO;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerDTO;
import com.example.demo.viewLayer.dto.playerDTOs.PlayerRegisterDTO;
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
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void create() throws Exception {
        PlayerRegisterDTO playerRegisterDTO = new PlayerRegisterDTO("DIma", "N", LocalDate.parse("2005-05-12"), 1L);

        String playerJson = objectMapper.writeValueAsString(playerRegisterDTO);

        mockMvc.perform(post("/player/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(playerJson))
                .andExpect(status().isCreated());

        verify(playerService, times(1)).createPlayer(argThat(dto ->
                dto.getName().equals(playerRegisterDTO.getName()) &&
                        dto.getSurname().equals(playerRegisterDTO.getSurname()) &&
                        dto.getDateOfBirth().equals(playerRegisterDTO.getDateOfBirth()) &&
                        dto.getTeamId() == playerRegisterDTO.getTeamId()
        ));
    }

    @Test
    void getAllPlayers() throws Exception {
        PlayerDTO player1 = PlayerDTO.builder().id(1L).name("Lionel").surname("Messi").teamId(1L).experience(BigDecimal.valueOf(10)).salary(5000L).build();
        PlayerDTO player2 = PlayerDTO.builder().id(2L).name("Cristiano").surname("Ronaldo").teamId(2L).experience(BigDecimal.valueOf(12)).salary(6000L).build();
        List<PlayerDTO> players = List.of(player1, player2);

        when(playerService.getAll()).thenReturn(players);

        mockMvc.perform(get("/player/get-all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(player1.getName()))
                .andExpect(jsonPath("$[1].name").value(player2.getName()));
    }

    @Test
    void getPlayer() throws Exception {
        TeamDTO teamDTO = TeamDTO.builder()
                .name("Manchester United")
                .country("England")
                .budget(BigDecimal.valueOf(900000000.000))
                .commission(BigDecimal.valueOf(10.00))
                .build();

        Long playerId = 1L;
        PlayerDTO playerDTO = PlayerDTO.builder()
                .id(1L)
                .name("Lionel")
                .surname("Messi")
                .teamId(1L)
                .teamDTO(teamDTO)
                .experience(BigDecimal.valueOf(10))
                .salary(5000L)
                .build();

        when(playerService.getById(playerId)).thenReturn(playerDTO);

        mockMvc.perform(get("/player/get")
                        .param("id", String.valueOf(playerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(playerDTO.getId()))
                .andExpect(jsonPath("$.name").value(playerDTO.getName()))
                .andExpect(jsonPath("$.surname").value(playerDTO.getSurname()))
                .andExpect(jsonPath("$.teamId").value(playerDTO.getTeamId()))
                .andExpect(jsonPath("$.experience").value(playerDTO.getExperience()))
                .andExpect(jsonPath("$.salary").value(playerDTO.getSalary()));
    }

    @Test
    void updatePlayer() throws Exception {
        PlayerDTO updatedPlayer = PlayerDTO.builder().id(1L).name("Lionel").surname("Messi").teamId(1L).experience(BigDecimal.valueOf(10)).salary(7000L).build();

        String playerJson = objectMapper.writeValueAsString(updatedPlayer);

        when(playerService.updatePlayer(any(PlayerDTO.class))).thenReturn(updatedPlayer);

        mockMvc.perform(patch("/player/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedPlayer.getId()))
                .andExpect(jsonPath("$.name").value(updatedPlayer.getName()))
                .andExpect(jsonPath("$.salary").value(updatedPlayer.getSalary()));
    }

    @Test
    void deletePlayer() throws Exception {
        long playerId = 1L;

        mockMvc.perform(delete("/player/delete")
                        .param("id", String.valueOf(playerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(playerService, times(1)).deleteById(playerId);
    }
}
