package com.freecall.dto;

import jakarta.validation.constraints.NotBlank;

public record OriginateRequest(
        @NotBlank String caller,
        @NotBlank String callee,
        String gateway,
        String context,
        String dialplan,
        String endpoint
) {
}
