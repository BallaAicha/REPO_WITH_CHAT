package org.etutoria.usersservice.Apis;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingController {
    @GetMapping("/listings")
    public String getListingsPage() {
        return "listings";
    }
}