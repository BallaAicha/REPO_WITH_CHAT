package org.etutoria.usersservice.Config;

import org.etutoria.usersservice.repositories.InternalUserRepository;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class KeycloakEventListener implements EventListenerProvider {
    private final InternalUserRepository internalUserRepository;
    public KeycloakEventListener(InternalUserRepository internalUserRepository) {
        this.internalUserRepository = internalUserRepository;
    }

    @EventListener
    public void onEvent(Event event) {
        if (event.getType() == EventType.DELETE_ACCOUNT) {
            String userId = event.getUserId();
            internalUserRepository.deleteById(userId);
            System.out.println("User with ID " + userId + " has been deleted from internal database.");
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        if (adminEvent.getOperationType() == OperationType.DELETE) {
            String userId = adminEvent.getAuthDetails().getUserId();
            internalUserRepository.deleteById(userId);
            System.out.println("User with ID " + userId + " has been deleted from internal database.");
        }
    }


    @Override
    public void close() {
        // No-op
    }
}