package org.etutoria.listingservice.admin;

import lombok.RequiredArgsConstructor;
import org.etutoria.listingservice.scheduler.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final NotificationService notificationService; // Service pour les notifications (Kafka, Email, etc.)

    /**
     * Notifie les administrateurs pour la validation d'un paiement
     *
     * @param listingId L'identifiant de l'annonce
     * @param userId    L'identifiant de l'utilisateur
     */
    public void notifyAdminForPaymentValidation(String listingId, String userId) {
        // Extraire le JWT de l'utilisateur actuel
        Jwt jwt = getAuthenticatedJwt();

        // Vérifier si l'utilisateur connecté dispose du rôle ADMIN
        if (isUserAdmin(jwt)) {
            // Logique de notification
            notificationService.sendNotification(
                    userId,
                    "Un paiement nécessite une validation pour l'annonce : " + listingId
            );
            System.out.println("Admin notified for payment validation for listing: " + listingId);
        } else {
            throw new SecurityException("L'utilisateur n'a pas les droits d'administration.");
        }
    }

    /**
     * Vérifie si l'utilisateur dispose du rôle ADMIN
     *
     * @param jwt Le token JWT de l'utilisateur
     * @return True si l'utilisateur a le rôle ADMIN
     */
    private boolean isUserAdmin(Jwt jwt) {
        return jwt.getClaimAsStringList("roles").contains("ADMIN");
    }

    /**
     * Récupère le JWT authentifié de l'utilisateur connecté
     *
     * @return Jwt authentifié
     */
    private Jwt getAuthenticatedJwt() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}