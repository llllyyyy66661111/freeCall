package com.freecall.controller;

import com.freecall.dto.CallControlResponse;
import com.freecall.dto.OriginateRequest;
import com.freecall.service.FreeSwitchService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calls")
public class CallController {

    private final FreeSwitchService freeSwitchService;

    public CallController(FreeSwitchService freeSwitchService) {
        this.freeSwitchService = freeSwitchService;
    }

    @PostMapping("/originate")
    public CallControlResponse originate(@Valid @RequestBody OriginateRequest request) {
        return freeSwitchService.originate(request);
    }

    @PostMapping("/{uuid}/hangup")
    public CallControlResponse hangup(@PathVariable String uuid,
                                      @RequestParam(required = false) String cause) {
        return freeSwitchService.hangup(uuid, cause);
    }
}
