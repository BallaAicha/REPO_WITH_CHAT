package org.etutoria.usersservice.Config;
import org.etutoria.usersservice.constantes.KeycloakConstantes;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class KeycloakConfig {
    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .clientSecret(KeycloakConstantes.CLIENT_SECRET)
                .clientId(KeycloakConstantes.CLIENT_ID)
                .grantType(KeycloakConstantes.CREDENTIALS)
                .realm(KeycloakConstantes.REALM)
                .serverUrl(KeycloakConstantes.SERVER_URL)
                .build();


    }
}
