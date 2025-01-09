package com.example.demo.service;

public interface TransferService {

    void transferPlayer(Long playerId, Long fromTeamId, Long toTeamId);
}
