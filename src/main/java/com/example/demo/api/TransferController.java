package com.example.demo.api;

import com.example.demo.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferController {

    private final TransferService transferService;

    @PatchMapping("/sell")
    public String sellPlayer(@RequestParam long playerId,
                           @RequestParam long fromTeamId,
                           @RequestParam long toTeamId) {
        transferService.transferPlayer(playerId, fromTeamId, toTeamId);

        return "Player was successfully transferred";
    }
}
