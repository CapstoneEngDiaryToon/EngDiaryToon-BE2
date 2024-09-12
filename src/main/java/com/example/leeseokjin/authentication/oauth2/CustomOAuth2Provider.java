package com.example.leeseokjin.authentication.oauth2;

public enum CustomOAuth2Provider {
    GOOGLE;

    public ClientRegistration.ClientRegistrationBuilder getBuilder(String registrationId) {
        return ClientRegistration.builder().registrationId(registrationId);
    }
}