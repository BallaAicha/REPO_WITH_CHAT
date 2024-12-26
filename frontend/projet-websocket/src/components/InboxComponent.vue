<template>
  <div>
    <h1>Boîte de réception</h1>

    <!-- Conteneur pour les messages reçus -->
    <div id="messages">
      <h2>Messages reçus :</h2>
      <ul id="message-list">
        <li v-for="message in receivedMessages" :key="message.tstamp">
          <strong>De :</strong> {{ message.senderName }} <br>
          <strong>Message :</strong> {{ message.content }} <br>
          <strong>À propos de :</strong> Listing ID {{ message.listingId }} <br>
          <span><em>Reçu à : {{ new Date(parseInt(message.tstamp)).toLocaleString() }}</em></span><br>
          <button @click="selectRecipient(message.senderId, message.senderName, message.chatChannel)">
            Répondre
          </button>
        </li>
      </ul>
    </div>

    <!-- Formulaire pour envoyer une réponse -->
    <div id="reply-box" v-if="selectedRecipientId">
      <h2>Répondre à : {{ selectedRecipientName }}</h2>
      <textarea v-model="replyMessage" placeholder="Tapez votre message..."></textarea><br>
      <button @click="sendReply">Envoyer</button>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import { Client as Stomp } from "@stomp/stompjs";

export default {
  data() {
    return {
      token: "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXZEEtY2IxcFhwSkdTZFo2ck5CNWJla1ljWm9YbG1OYXZKRlI0TWtHNk04In0.eyJleHAiOjE3MzUyNTY5NzAsImlhdCI6MTczNTIzODk3MCwianRpIjoiMmNlNDM2YmYtODg4Ny00OWUyLWE4NGQtYzUyNTAzY2I3M2E2IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDk5L3JlYWxtcy9jb25uZWN0VHJvYyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJjOTNlNDc3Yi03MTAzLTQ5ODMtODEwOS00ZDAzODAxZjljMzUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjb25uZWN0Iiwic2lkIjoiNDFhZTg3MDktMmIxMy00MTFiLThhYjctNDllNzhkOTM5ZDAzIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbF9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY29ubmVjdHRyb2MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX0sImNvbm5lY3QiOnsicm9sZXMiOlsiVVNFUiJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInBob25lTnVtYmVyIjoiMTIzNDU2Nzg5NiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibnVtYmVyT2ZWaWV3cyI6MCwibmFtZSI6Ik91c21hbmUgTUJBQ0tFIiwicmF0aW5nIjoiMC4wIiwicHJlZmVycmVkX3VzZXJuYW1lIjoib3VzbWFuZUB0ZXN0LmNvbSIsImdpdmVuX25hbWUiOiJPdXNtYW5lIiwiZmFtaWx5X25hbWUiOiJNQkFDS0UiLCJlbWFpbCI6Im91c21hbmVAdGVzdC5jb20ifQ.BOKxgbfwSSYu1VaZp51WtY4-7NNNIT8wTmqyMO-SzfucPRfJFtagNUTXEZ3gaOrY4pX7TwAP2aE2jcUgTNc7tQECKx9Y1Eeo5fe13i-blL61L_XHxF3zjKIHNxGpSJaX4JNl0w0DHGRu961EiKEfttf-dhxC8CZ-QY9-2Z8avP9e9wp_0PSLn8GGVROUHSWTGKxJG5qLivqzuBvk9hcBu1yCytIRMaq3bgmU4Zt_CSjXkSW6NLX8hDMs3b3ERB6z5mKDJ_JUwFVYkPK7n9xws9MmikSowij4QXfM9j-u10ZTndUW1CdS8teM3Glj0bkyiVGTLrQtFQyI4BvyV0mcmA", // Remplacer par le jeton JWT
      recipientId: "c93e477b-7103-4983-8109-4d03801f9c35",
      stompClientUrl: "http://localhost:6700/auth/ws", // URL WebSocket
      stompClient: null, // Objet STOMP
      receivedMessages: [], // Liste des messages reçus
      selectedRecipientId: null,
      selectedRecipientName: null,
      currentChatChannel: null,
      replyMessage: "", // Contenu du message de réponse
    };
  },
  methods: {
    // Connexion au WebSocket en tant qu'utilisateur basé sur le token JWT
    connectAsRecipient() {
      console.log("Connexion WebSocket pour afficher les messages reçus.");

      const socket = new SockJS(this.stompClientUrl);

      // Initialisation du client STOMP
      this.stompClient = new Stomp({
        webSocketFactory: () => socket,
        connectHeaders: {
          Authorization: "Bearer " + this.token, // Inclure le token dans le handshake
        },
        debug: (str) => console.log(str), // Debug
        reconnectDelay: 5000, // Réessai automatique après 5 secondes
      });

      this.stompClient.onConnect = () => {
        console.log("Connexion établie au serveur STOMP.");

        // S'abonner pour recevoir les messages destinés à l'utilisateur
        const topic = `/topic/messages/${this.recipientId}`;
        console.log("S'abonner au topic :", topic);

        this.stompClient.subscribe(topic, (message) => {
          const parsedMessage = JSON.parse(message.body);
          console.log("Message reçu :", parsedMessage);

          this.displayMessage(parsedMessage);
        });
      };

      this.stompClient.onStompError = (error) => {
        console.error("Erreur lors de la connexion STOMP :", error);
      };

      this.stompClient.onWebSocketError = (error) => {
        console.error("Erreur WebSocket :", error);
      };

      this.stompClient.onWebSocketClose = (event) => {
        console.log("WebSocket fermé :", event);
      };

      this.stompClient.activate(); // Active la connexion STOMP
    },

    // Affiche les messages dans la liste reçue
    displayMessage(message) {
      this.receivedMessages.push(message);
    },

    // Sélectionne un destinataire pour répondre (active la boîte de réponse)
    selectRecipient(recipientId, recipientName, chatChannel) {
      this.selectedRecipientId = recipientId;
      this.selectedRecipientName = recipientName;
      this.currentChatChannel = chatChannel;
    },

    // Envoi de la réponse au destinataire sélectionné
    sendReply() {
      if (!this.replyMessage || !this.selectedRecipientId || !this.currentChatChannel) {
        alert("Veuillez sélectionner un destinataire et entrer un message !");
        return;
      }

      // Préparation du message
      const chatMessage = {
        content: this.replyMessage,
        senderId: this.recipientId, // ID de l'utilisateur actuel comme expéditeur
        recipientId: this.selectedRecipientId,
        senderName: "Annonceur", // Nom de l'expéditeur
        chatChannel: this.currentChatChannel,
        tstamp: new Date().getTime().toString(), // Timestamp du message
      };

      // Envoi du message via STOMP
      this.stompClient.publish({
        destination: `/app/chat/${this.selectedRecipientId}`, // Destination côté backend
        headers: { Authorization: "Bearer " + this.token },
        body: JSON.stringify(chatMessage),
      });

      console.log("Message envoyé :", chatMessage);

      // Réinitialisation du formulaire de message
      this.replyMessage = "";
      this.selectedRecipientId = null;
      this.selectedRecipientName = null;
      this.currentChatChannel = null;
    },
  },
  mounted() {
    // Connexion WebSocket dès que le composant est monté
    this.connectAsRecipient();
    console.log("Page chargée et connexion WebSocket tentée.");
  },
};
</script>

<style>
#messages {
  margin-top: 20px;
  font-family: Arial, sans-serif;
}

#message-list li {
  margin-bottom: 10px;
}

#reply-box {
  margin-top: 20px;
}

textarea {
  width: 100%;
  height: 60px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
</style>