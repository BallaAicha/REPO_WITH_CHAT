# Documentation du projet ConnectTroc

## Partie Chat

Le chat est une fonctionnalité qui permet aux utilisateurs de discuter entre eux. Par exemple, côté frontend, sur chaque annonce, il y aura un bouton "Chat" qui permettra à l'utilisateur de discuter avec le propriétaire de l'annonce.

### Logique du Chat

Voici la logique du chat que j'essaie d'implémenter avec WebSocket. Vous pouvez consulter le code dans le microservice `users-service`.

Dans ce microservice, vous trouverez les dossiers suivants concernant le chat :

- **Config** : Contient la configuration de WebSocket (`WebSocketConfig`).
- **ChatMessage** : Contient les messages envoyés par les utilisateurs.
- **ChatRoom** : Contient les salles de chat.
- **APIs** : Contient le `ChatController` qui permet de gérer les messages et les salles de chat.

### Configuration WebSocket

La configuration WebSocket est définie dans le fichier `WebSocketConfig.java`. Elle inclut la configuration du broker de messages et l'enregistrement des endpoints STOMP.

### Intercepteur JWT

L'intercepteur JWT (`JwtHandshakeInterceptor.java`) est utilisé pour décoder et vérifier les jetons JWT lors de la poignée de main WebSocket.

### Contrôleur de Chat

Le contrôleur de chat (`ChatController.java`) gère les endpoints REST pour envoyer et recevoir des messages de chat. Il utilise les services `ChatMessageService` et `ListingService` pour gérer les messages et les annonces.

### Services de Chat

Les services de chat incluent la gestion des messages (`ChatMessageService`) et des annonces (`ListingService`).

Pour plus de détails, veuillez consulter les fichiers de code source dans le répertoire `users-service/src/main/java/org/etutoria/usersservice`.

---

N'hésitez pas à me contacter si vous avez des questions ou des suggestions concernant cette documentation.