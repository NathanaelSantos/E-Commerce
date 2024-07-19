package com.ecommerce.customer;

import java.util.Map;

public record ErroResponse(
        Map<String, String> errors
) { }
