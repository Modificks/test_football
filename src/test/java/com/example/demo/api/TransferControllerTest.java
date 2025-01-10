package com.example.demo.api;

import com.example.demo.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transferController).build();
    }

    @Test
    void sellPlayer_Success() throws Exception {
        long playerId = 1L;
        long toTeamId = 2L;

        doNothing().when(transferService).transferPlayer(playerId, null, toTeamId);

        mockMvc.perform(patch("/transfer")
                        .param("playerId", String.valueOf(playerId))
                        .param("toTeamId", String.valueOf(toTeamId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        result.getResponse().getContentAsString().contains("Player with id: '1' was successfully transferred"));

        verify(transferService, times(1)).transferPlayer(playerId, null, toTeamId);
    }

    @Test
    void sellPlayer_WithFromTeamId_Success() throws Exception {
        long playerId = 1L;
        long fromTeamId = 3L;
        long toTeamId = 2L;

        doNothing().when(transferService).transferPlayer(playerId, fromTeamId, toTeamId);

        mockMvc.perform(patch("/transfer")
                        .param("playerId", String.valueOf(playerId))
                        .param("fromTeamId", String.valueOf(fromTeamId))
                        .param("toTeamId", String.valueOf(toTeamId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        result.getResponse().getContentAsString().contains("Player with id: '1' was successfully transferred"));

        verify(transferService, times(1)).transferPlayer(playerId, fromTeamId, toTeamId);
    }

    @Test
    void sellPlayer_MissingToTeamId() throws Exception {
        long playerId = 1L;

        mockMvc.perform(patch("/transfer")
                        .param("playerId", String.valueOf(playerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void sellPlayer_MissingPlayerId() throws Exception {
        long toTeamId = 2L;

        mockMvc.perform(patch("/transfer")
                        .param("toTeamId", String.valueOf(toTeamId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
