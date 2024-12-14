package org.etutoria.usersservice.model;
public record NewUser(
        String email,
        String password,
        String firstName,
        String lastName,
        Integer numberOfViews,
        String phoneNumber,
        String profilePicture,
        Double rating
) {
    public NewUser {
        if (numberOfViews == null) {
            numberOfViews = 0;
        }

        if (rating == null) {
            rating = 0.0;
        }

    }
}