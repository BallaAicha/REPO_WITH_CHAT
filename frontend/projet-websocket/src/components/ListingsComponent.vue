<template>
  <div>
    <h1>Listings</h1>

    <!-- Conteneur pour les listings -->
    <div id="listings">
      <div v-for="listing in listings" :key="listing.id">
        <h2>{{ listing.title }}</h2>
        <p>{{ listing.description }}</p>
        <button @click="contactSeller(listing.userId)">Contacter le vendeur</button>
      </div>
    </div>

    <div id="received-messages">
      <h2>Messages reçus :</h2>
      <ul>
        <li v-for="message in receivedMessages" :key="message.tstamp">
          <strong>De :</strong> {{ message.senderName }} <br />
          <strong>Message :</strong> {{ message.content }} <br />
          <em>Reçu à : {{ new Date(Number(message.tstamp || Date.now())).toLocaleString() }}</em>
          <br />
          <textarea
              :id="'reply-' + message.senderId"
              placeholder="Répondre..."
          ></textarea>
          <button @click="sendReply(message.senderId, $refs['reply-' + message.senderId][0].value)">
            Envoyer
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import { Client as Stomp } from "@stomp/stompjs";

export default {
  data() {
    return {
      listings: [],
      receivedMessages: [],
      stompClient: null,
      token: "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXZEEtY2IxcFhwSkdTZFo2ck5CNWJla1ljWm9YbG1OYXZKRlI0TWtHNk04In0.eyJleHAiOjE3MzUyNTQxOTcsImlhdCI6MTczNTIzNjE5NywianRpIjoiMzlkYmQ3MzgtMDM4Zi00MmI3LTgyMTgtY2I1Y2ZkZmJjOWRjIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDk5L3JlYWxtcy9jb25uZWN0VHJvYyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIxMmUxODlhOS1iNWM0LTRhODEtYTdkZC0yMDg3ODg4Y2RhZjMiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjb25uZWN0Iiwic2lkIjoiMmQ0NTkwYmEtOGY1OS00YzZhLTkyOTktMjcyZmMxNWI0NWI4IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY29ubmVjdHRyb2MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX0sImNvbm5lY3QiOnsicm9sZXMiOlsiVVNFUiJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsInBob25lTnVtYmVyIjoiMTIzNDU2Nzg5NiIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibnVtYmVyT2ZWaWV3cyI6MCwibmFtZSI6IkRhdmlkIERhdmlkIiwicmF0aW5nIjoiMC4wIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGF2aWRAdGVzdC5jb20iLCJnaXZlbl9uYW1lIjoiRGF2aWQiLCJmYW1pbHlfbmFtZSI6IkRhdmlkIiwiZW1haWwiOiJkYXZpZEB0ZXN0LmNvbSJ9.pl5sh_-Wl2mLwi5OcOeLXMCqgqu16K_VciH84TifuSn7-Hpp40Xs_fOOPnYs3Vc2VU4r1vpHB1ELmlQj5cbTEXvx4evNS1i8eZnKCbu3ZZgEdSEC0vkjKwwM8hWy7qCBO-rXrNd0Sm-TEcJxdtZ0A57Pez5uWy2bgVKBjdu9cA6Rn_6mpG0KK262xnrQGwPzXActFMEeMT-KvCM6tAFREU_uVODoUEIAVp1Ux-xVqBzNe7K-a-Ip_ZyPRQvN62Jt0EoZFJMvcPljrrGaFuFPmorVwsyHgJtVm3a1XU2PXNibaAGDBIybQAApAWfDfCReNf_O-KYZ_O6xNXUMchC-mA",
      apiUrl: "http://localhost:6801/api/listings",
      stompClientUrl: "http://localhost:6700/auth/ws",
    };
  },
  methods: {
    async getListings() {
      try {
        const response = await fetch(this.apiUrl, {
          method: "GET",
          headers: {
            Authorization: "Bearer " + this.token,
            "Content-Type": "application/json",
          },
        });
        if (!response.ok) throw new Error("Erreur : " + response.status);

        this.listings = await response.json();
      } catch (error) {
        console.error("Erreur lors de la récupération des listings :", error);
        alert("Impossible de charger les listings. Veuillez réessayer.");
      }
    },

    contactSeller(userId) {
      console.log("Connecting to seller with ID:", userId);

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

      // Lors de la connexion, établir les abonnements et envoyer le message initial
      this.stompClient.onConnect = () => {
        console.log("STOMP connection established");

        // S'abonner aux messages privés pour cet utilisateur
        this.stompClient.subscribe(`/user/${userId}/queue/messages`, (message) => {
          const receivedMessage = JSON.parse(message.body);
          this.receivedMessages.push(receivedMessage);
        });

        // Message à envoyer
        const chatMessage = {
          content: "Bonjour, je suis intéressé par votre annonce.",
          recipientId: userId,
        };

        // Publier avec l'en-tête Authorization
        this.stompClient.publish({
          destination: `/app/chat/${userId}`,
          body: JSON.stringify(chatMessage),
          headers: {
            Authorization: "Bearer " + this.token, // Ajoute explicitement l'en-tête Authorization
          },
        });

        console.log("Message envoyé avec succès au vendeur");
      };

      // Gestion des erreurs STOMP
      this.stompClient.onStompError = (error) => {
        console.error("Erreur STOMP :", error);
      };

      this.stompClient.activate(); // Active la connexion STOMP
    },

    sendReply(userId, content) {
      if (!content.trim()) {
        alert("Le message ne peut pas être vide.");
        return;
      }

      const replyMessage = {
        content,
        recipientId: userId,
      };

      this.stompClient.publish({
        destination: `/app/chat/${userId}`,
        body: JSON.stringify(replyMessage),
      });

      alert("Réponse envoyée avec succès.");
    },
  },

  mounted() {
    this.getListings();
  },
};
</script>

<style>
#listings {
  margin: 20px 0;
}

#listings div {
  border: 1px solid #ddd;
  padding: 15px;
  margin-bottom: 10px;
  border-radius: 5px;
  background: #f9f9f9;
}

#received-messages {
  margin-top: 30px;
}

ul {
  list-style-type: none;
  padding: 0;
}

textarea {
  width: 100%;
  height: 60px;
  margin-top: 10px;
  margin-bottom: 5px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}
</style>