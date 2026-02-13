package com.freecall.dto;

public record FreeSwitchHealthResponse(
        boolean healthy,
        boolean connected,
        String host,
        int port,
        String command,
        String response,
        String error
) {
}
