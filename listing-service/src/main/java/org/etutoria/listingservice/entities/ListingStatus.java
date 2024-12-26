package org.etutoria.listingservice.entities;

public enum ListingStatus {
    ACTIVE,
    SOLD,
    REMOVED,
    DRAFT,         // Annonce en brouillon
    WAITING_FOR_PAYMENT,  // Annonce en attente de paiement
    AWAITING_VALIDATION, // Annonce en attente de validation
    PAID,          // Annonce payée
    PUBLISHED,  // Annonce publiée
    INACTIVE,       // Annonce désactivée
  BANNED///  // Annonce interdite
}
