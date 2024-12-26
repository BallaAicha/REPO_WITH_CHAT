package org.etutoria.listingservice.scheduler;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String userId, String message) {
        // Logique pour envoyer une notification (Email, SMS, etc.)
        System.out.println("Notification envoyée à l'utilisateur " + userId + ": " + message);
    }
    public void sendReminder(String userId, String reminderMessage) {
        // Logique pour envoyer un rappel
        System.out.println("Rappel envoyé à l'utilisateur " + userId + ": " + reminderMessage);
    }

    public void notifyAdmin(String adminMessage) {
        // Logique pour envoyer une notification aux administrateurs
        System.out.println("Notification à l'administrateur: " + adminMessage);
    }
}