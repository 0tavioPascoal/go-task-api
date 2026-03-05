package dev.tavin.go_task.infra.helpers;

import org.springframework.http.ResponseCookie;

public  class CookiesHelper {

    public static  ResponseCookie buildCookie(String name, String value, int maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(maxAge)
                .sameSite("Strict")
                .build();
    }
}
