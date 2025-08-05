package com.aluracursos.conversordivisas.Modelos;

import java.util.Map;

public record Moneda(
        String time_last_update_utc,
        String base_code,
        Map<String, String> conversion_rates
        ) {
}
