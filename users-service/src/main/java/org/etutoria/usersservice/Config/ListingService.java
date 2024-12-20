package org.etutoria.usersservice.listing;

import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.entities.Listing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ListingService {
    @Value("${spring.application.config.listings-url}")
    private String listingsUrl;
    private final RestTemplate restTemplate;

    public Listing getListing(String id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String url = listingsUrl + "/" + id;
        ResponseEntity<Listing> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {}
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new RuntimeException("An error occurred while fetching the listing: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }
}