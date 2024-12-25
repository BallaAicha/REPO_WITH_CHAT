package org.etutoria.usersservice.Apis;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {

    /**
     * Route pour afficher la page "listings.html".
     * Cette route sera accessible via : http://localhost:6700/auth/listings
     */
    @GetMapping("/listings")
    public String getListingsPage() {
        // Retourner listings.html (Thymeleaf va chercher le fichier dans `resources/templates`)
        return "listings";
    }
}