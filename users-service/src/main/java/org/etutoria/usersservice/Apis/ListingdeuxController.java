package org.etutoria.usersservice.Apis;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListingdeuxController {
    @GetMapping("/listingsdeux")
    public String getListingsPage() {
        return "listingsdeux";
    }
}