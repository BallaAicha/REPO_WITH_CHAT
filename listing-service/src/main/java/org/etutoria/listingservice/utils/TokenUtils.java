package org.etutoria.listingservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtils {

    /**
     * Extrait l'ID utilisateur d'un token JWT.
     *
     * @param token Le JWT décodé
     * @return L'identifiant de l'utilisateur
     */
    public static String extractUserIdFromToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Le token est invalide.");
        }
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getSubject();
    }
}