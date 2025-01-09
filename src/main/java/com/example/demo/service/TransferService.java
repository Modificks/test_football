package com.example.demo.service;

public interface TransferService {

    void transferPlayer(long playerId, Long fromTeamId, long toTeamId);
}
