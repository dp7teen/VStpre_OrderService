package com.dp.vstore_orderservice.security;

import java.io.Serializable;

public record UserPrincipal(String id, String email) implements Serializable {
}
