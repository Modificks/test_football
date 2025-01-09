package com.example.demo.api;

import com.example.demo.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PatchMapping("/transfer")
    public String sellPlayer(@RequestParam long playerId,
                           @RequestParam(required = false) Long fromTeamId,
                           @RequestParam long toTeamId) {
        transferService.transferPlayer(playerId, fromTeamId, toTeamId);

        return "Player with id: '" + playerId + "' was successfully transferred";
    }
}
