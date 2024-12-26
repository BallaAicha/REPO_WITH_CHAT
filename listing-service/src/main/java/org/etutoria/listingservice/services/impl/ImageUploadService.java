package org.etutoria.listingservice.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageUploadService {

    private final String uploadDir = "uploads/payment-proofs"; // Répertoire local pour les preuves

    public ImageUploadService() {
        // Crée le répertoire s'il n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Charge une image et retourne son chemin d'accès.
     *
     * @param file MultipartFile (fichier à uploader)
     * @return Chemin complet du fichier enregistré
     * @throws IOException Si une erreur survient durant l'enregistrement
     */
    public String uploadImage(MultipartFile file) throws IOException {
        // Valider le fichier (e.g., type et taille acceptée)
        validateImage(file);

        // Générer un nom unique pour éviter les conflits
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Enregistrer le fichier sur le disque
        Path filePath = Paths.get(uploadDir, fileName);
        Files.write(filePath, file.getBytes());

        // Retourner le chemin relatif pour stockage dans la base de données
        return filePath.toString();
    }

    private void validateImage(MultipartFile file) {
        // Exemple de validation (vous pourriez adapter selon vos besoins)
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Le fichier est vide.");
        }
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("Le fichier doit être une image.");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // Limite à 5 MB
            throw new IllegalArgumentException("Le fichier est trop volumineux (max: 5MB).");
        }
    }
}