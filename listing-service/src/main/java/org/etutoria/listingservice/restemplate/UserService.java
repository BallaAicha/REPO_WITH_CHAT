package org.etutoria.listingservice.restemplate;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.etutoria.listingservice.exception.BusinessException;
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
public class UserService {

    @Value("${spring.application.config.users-url}")
    private String usersUrl;
    private final RestTemplate restTemplate;

    public InternalUser getInternalUser(String userId, String token) {
        if (userId == null || token == null) {
            throw new IllegalArgumentException("User ID and token cannot be null");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        String url = usersUrl + "/" + userId;
        System.out.println("Request URL: " + url); // Log the URL
        System.out.println("Request Headers: " + headers); // Log the headers

        ParameterizedTypeReference<InternalUser> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<InternalUser> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while fetching the user: " + responseEntity.getStatusCode());
        }

        return responseEntity.getBody();
    }
}