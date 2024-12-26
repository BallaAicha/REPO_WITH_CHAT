package org.etutoria.listingservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.entities.ListingStatus;
import org.etutoria.listingservice.repositories.ListingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListingPaymentScheduler {

    private final ListingRepository listingRepository;
    private final NotificationService notificationService;

    // Exécution quotidienne pour vérifier les délais de paiement
    @Scheduled(cron = "0 0 0 * * ?") // Chaque jour à minuit
    public void checkPaymentDeadlines() {
        List<Listing> unpaidListings = listingRepository.findAll().stream()
                .filter(listing -> listing.isPaymentPending() &&
                        LocalDateTime.now().isAfter(listing.getPaymentDeadline()))
                .collect(Collectors.toList());

        for (Listing listing : unpaidListings) {
            // Désactiver les annonces pour paiement non effectué
            listing.setStatus(ListingStatus.INACTIVE);
            listingRepository.save(listing);

            // Notifier les utilisateurs du non-respect du délai
            notificationService.sendNotification(
                    //à gérer avec Kafka eet mail de notification
                    listing.getUserId(),
                    "Votre annonce " + listing.getTitle() + " a été désactivée car le paiement n'a pas été effectué avant la date limite."
            );
        }
    }
}