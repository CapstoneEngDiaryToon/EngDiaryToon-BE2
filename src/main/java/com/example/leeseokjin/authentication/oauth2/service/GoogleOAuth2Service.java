package com.example.leeseokjin.authentication.oauth2.service;

import com.example.leeseokjin.authentication.oauth2.OAuth2RequestFailedException;
import com.example.leeseokjin.authentication.oauth2.OAuth2Token;
import com.example.leeseokjin.authentication.oauth2.ClientRegistration;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GoogleOAuth2Service extends OAuth2Service{
    public GoogleOAuth2Service(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public void unlink(ClientRegistration clientRegistration, OAuth2Token token){

        //토큰이 만료되었다면 토큰을 갱신
        token = refreshOAuth2Token(clientRegistration, token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);

        String unlinkUri = UriComponentsBuilder.fromUriString(clientRegistration.getProviderDetails().getUnlinkUri())
                .queryParam("token", token.getToken()).encode().build().toUriString();

        ResponseEntity<String> entity = null;
        try {
            entity = restTemplate.exchange(unlinkUri, HttpMethod.GET, httpEntity, String.class);
        } catch (HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();
            throw new OAuth2RequestFailedException(String.format("%s 연동해제 실패. [응답코드 : %d].", clientRegistration.getRegistrationId().toUpperCase(), statusCode), exception);
        }
    }
}
