package com.freecall.controller;

import com.freecall.dto.FreeSwitchHealthResponse;
import com.freecall.service.FreeSwitchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/freeswitch")
public class FreeSwitchHealthController {

    private final FreeSwitchService freeSwitchService;

    public FreeSwitchHealthController(FreeSwitchService freeSwitchService) {
        this.freeSwitchService = freeSwitchService;
    }

    @GetMapping("/health")
    public FreeSwitchHealthResponse health() {
        return freeSwitchService.healthCheck();
    }
}
