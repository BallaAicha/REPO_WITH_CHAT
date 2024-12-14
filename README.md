Documentation du projet ConnectTroc 
=======================================

API REST pour la gestion des utilisateurs (Authentification)
=======================
Dans cette Application la partie Authentification est gérée par Keycloak.

***


Pour Créer un utilisateur dans Keycloak, il faut envoyer une requête POST à l'URL suivante:
=======================================

```
http://localhost:6700/auth/users
```

```

Corps de la requête:
{
"email": "david@test.com",
"password": "password123",
"firstName": "David",
"lastName": "Fernandes",
"phoneNumber": "1234567890"

}

```
Pour Authentifier un utilisateur dans Keycloak, il faut envoyer une requête POST à l'URL suivante:
=======================================
```
http://localhost:6700/auth/users/login
```

```
Corps de la requête:

{
"username": "david@test.com",
"password": "password123"
}

```

```
Quand tu t'authentifies, tu reçois un token. Ce token est à envoyer dans le header de chaque requête pour accéder aux ressources protégées.

```
Ce token Resseemble à ceci:
=======================================
```
"access_token":"eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJXZEEtY2IxcFhwSkdTZFo2ck5CNWJla1ljWm9YbG1OYXZKRlI0TWtHNk04In0.eyJleHAiOjE3MzQyMDgwNTksImlhdCI6MTczNDE5MDA1OSwianRpIjoiZDdmMTg3NWQtNDJlOC00MjVmLTg2ZTktMTM1MDYzNjkyNmU0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo5MDk5L3JlYWxtcy9jb25uZWN0VHJvYyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI2YjE2MzgwZi03ZTk4LTQ3NjEtODUzZi0yZGY1M2ZiN2E1MDgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjb25uZWN0Iiwic2lkIjoiMjMwMzJmYmUtOWI0MC00NWYxLWE2MmItNTIxYjFjYjYzOWUzIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIvKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY29ubmVjdHRyb2MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJwaG9uZU51bWJlciI6IjEyMzQ1Njc4OTAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm51bWJlck9mVmlld3MiOjAsIm5hbWUiOiJEYXZpZCBGZXJuYW5kZXMiLCJyYXRpbmciOiIwLjAiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJkYXZpZEB0ZXN0LmNvbSIsImdpdmVuX25hbWUiOiJEYXZpZCIsImZhbWlseV9uYW1lIjoiRmVybmFuZGVzIiwiZW1haWwiOiJkYXZpZEB0ZXN0LmNvbSJ9.bvnpTqtHPyHnarCDMh99vySFlxxzpuAn22cQvkt8LMVHjfHZlEiVEHWdrjbvuoBtX9bRYsJXCU5Rkg2F35-vsPIh1X_tflOK1vdqb9OVpRftEfCSbFzcJSr4q9vTQcv4cwHvrNNuVmN-gMl-jmDajlojCxfuUab0u9cD__exaGZIbOyzYbQF6JRVI6WDV9oj86_JF-oY-HSQVWyg2DDBp_CJ2FK_xEi6Hhl1uMNYdhMWa8o5qyD1UIhGcmqyQGngfLKC42rqZHblSE9kqxegbZh-JNmDfSOOAfyv25Z-kYuqEVpMj3grTDKIJ5i7z4TY4D9rS-ybpZk8sEnhjiUBwQ"


```


```
Pour Voir ce que Contient le token, tu peux le décoder sur le site suivant: https://jwt.io/ en le testant là-bas. voici ce que tu peux voir dans ce Token
PAYLOAD:
=======================================
{
"exp": 1734208059,
"iat": 1734190059,
"jti": "d7f1875d-42e8-425f-86e9-1350636926e4",
"iss": "http://localhost:9099/realms/connectTroc",
"aud": "account",
"sub": "6b16380f-7e98-4761-853f-2df53fb7a508",
"typ": "Bearer",
"azp": "connect",
"sid": "23032fbe-9b40-45f1-a62b-521b1cb639e3",
"acr": "1",
"allowed-origins": [
"/*"
],
"realm_access": {
"roles": [
"offline_access",
"uma_authorization",
"default-roles-connecttroc"
]
},
"resource_access": {
"account": {
"roles": [
"manage-account",
"manage-account-links",
"view-profile"
]
}
},
"scope": "email profile",
"phoneNumber": "1234567890",
"email_verified": false,
"numberOfViews": 0,
"name": "David Fernandes",
"rating": "0.0",
"preferred_username": "david@test.com",
"given_name": "David",
"family_name": "Fernandes",
"email": "david@test.com"
}

```

```
NB: Dans ce Token Voici less explications en detais de chaque attribut:
"sub": "6b16380f-7e98-4761-853f-2df53fb7a508", // Identifiant de l'utilisateur (userID)
"preferred_username": "david@test.com", // Nom d'utilisateur


=======================================

```
Pour Gérer le forgot password, il faut envoyer une requête POST à l'URL suivante:
=======================================
```
http://localhost:6700/auth/users/forgot-password

```

```
Corps de la requête:
{
  "username": ""(cad l'email de l'utilisateur) pour lui envoyer un email de réinitialisation de mot de passe via mailDev)
}
```
Pour que un utilisateur connecté veux ajouter un photo de profil, il faut envoyer une requête POST à l'URL suivante:
=======================================
```
http://localhost:6700/auth/internal-users/uplaodImageprofile/{userId}
```


Voici ce que retourne une image Crée :
=======================================


```
{
"idImage": 1,
"name": "profile.png",
"type": "image/png",
"image": "iVBORw0KGgoAAAANSUhEUgAAAgAA"
}
```

Dans la partie Dashboard si tu veux afficher la liste des utilisateurs, il faut envoyer une requête GET à l'URL suivante: NB , il faut toujours envoyer le token dans le header de la requête
=======================================

```
http://localhost:6700/auth/internal-users

```

Voici ce que retourne la liste des utilisateurs:
=======================================

```
[
    {
        "id": "6b16380f-7e98-4761-853f-2df53fb7a508",
        "email": "david@test.com",
        "password": "password123",
        "firstName": "David",
        "lastName": "Fernandes",
        "numberOfViews": 0,
        "phoneNumber": "1234567890",
        "rating": 0.0,
        "images": [
            {
                "idImage": 1,
                "name": "profile.png",
                "type": "image/png",
                "image": "iVBORw0KGgoAAAANSUhEUgAAAg"
            }
        ],

    }
]
NB : Ici images , réprésente le profil de l'utilisateur
```
Pour l'affichage des imagees de profil pour un utillisateur donnée il faut envoyer une requête GET à l'URL suivante:
=======================================
```
http://localhost:6700/auth/internal-users/getImagesProfile/6b16380f-7e98-4761-853f-2df53fb7a508
NB : Ici 6b16380f-7e98-4761-853f-2df53fb7a508 est l'identifiant de l'utilisateur
Quand tu le fais tu auras toujours la meme reponse que celle ci-dessus
{
"idImage": 1,
"name": "profile.png",
"type": "image/png",
"image": "iVBORw0KGgoAAAANSUhEUgAAAgAA"
}
```

NB : Je te Conseillle cette technique que j'utilise d'habitude dans Angular pour afficher cee genre d'image j'ai tendance à faire ceci 
=======================================

```

this.userService
.loadImage(this.currentUser.image.idImage)
.subscribe((img: Image) => {
this.myImage = 'data:' + img.type + ';base64,' + img.image;
});

NB : A adapter selon ta logique ce que je voulais juste de signaler c'est l'encodage de l'image en base64

```
Pour Supprimer Un User, il faut envoyer une requête DELETE à l'URL suivante:
=======================================
```
http://localhost:6700/auth/internal-users/delete/id
```

En tant que Admin aussi il peut assigner un role à un utilisateur, il faut envoyer une requête POST à l'URL suivante:
=======================================
```
http://localhost:6700/auth/roles/assign/users/{userId}
```
API REST pour la gestion des Listings
=======================

API REST pour la gestion des Catégories de Listings , CRUD
=======================
Pour Créer une Catégorie de Listing, il faut envoyer une requête POST à l'URL suivante:
=======================================
```
http://localhost:6801/api/categories
```

```
Corps de la requête:

{
   
    "name": "Meuble de Salon",
    "description": "Meuble pas neuf "
}


Retourne une réponse de ce genre:
{
    "id": 1,
    "name": "Meuble de Salon",
    "description": "Meuble pas neuf "
}

```

Pour Afficher la liste des Catégories de Listings selon l'id du catégorie, il faut envoyer une requête GET à l'URL suivante:
=======================================
```
http://localhost:6801/api/categories/id
Retourne une réponse de ce genre:
{
    "id": 1,
    "name": "Meuble de Salon",
    "description": "Meuble pas neuf "
}
Pour modifier une Catégorie de Listing, il faut envoyer une requête PUT à l'URL suivante:
=======================================
```

```
http://localhost:6801/api/categories/id
NB : La methode est de type PUT et le corps de la requête est le meme que celui de la création


```

Pour Supprimer une Catégorie de Listing, il faut envoyer une requête DELETE à l'URL suivante:
=======================================
```
http://localhost:6801/api/categories/id
NB : La methode est de type DELETE
```

Pour afficher toutes les Catégories de Listings, il faut envoyer une requête GET à l'URL suivante:
=======================================
```
http://localhost:6801/api/categories
Retourne une réponse de ce genre:
[
    {
        "id": 1,
        "name": "Meuble de Salon",
        "description": "Meuble pas neuf "
    },
    {
        "id": 2,
        "name": "Montre",
        "description": "Meuble pas neuf "
    }
]
```

API REST pour la gestion des Listings
=======================


Pour qu'un utilisateur connecté veux ajouter un Listing, il faut envoyer une requête POST à l'URL suivante:
=======================
```
http://localhost:6801/api/listings
Exemple de corps de la requête:
{
   

  "title": "Meuble", // Titre du Listing
  "description": "Meuble en trés bon état mais pas neuf", // Description du Listing
  "price": 699.99,// Prix du Listing
  "location": "Rabat , Hay Riad",// Lieu du Listing
  "status": "SOLD",// Statut du Listing(PENDING,ACTIVE)
  "category": {
    "id": 1
  }

}
```
La réponse de cette requête est la suivante:
=======================

```
{
    "createdAt": "2024-12-14T18:21:51.348016",
    "createdBy": "LISTING_MS",
    "updatedAt": "2024-12-14T18:21:51.348016",
    "updatedBy": "LISTING_MS",
    "listingId": "7deff2ce-e2d1-4c35-81c7-dffa45470d82",
    "userId": "6b16380f-7e98-4761-853f-2df53fb7a508",
    "title": "Meuble",
    "description": "Meuble en trés bon état mais pas neuf",
    "price": 699.99,
    "location": "Rabat , Hay Riad",
    "status": "SOLD",
    "category": {
        "id": 1,
        "name": "Meuble de Salon",
        "description": "Meuble pas neuf "
    },
    "images": null,
    }
```
NB le Processus de Création du Listing n'est pas encore terminé, il reste l'ajout des images du Listing 
=======================
Pour ajouter une image à un Listing, il faut envoyer une requête POST à l'URL suivante:
=======================
```
http://localhost:6801/api/listing-images/uplaodImageListing/{listingId}

```

Pour Voir la liste des Listings, il faut envoyer une requête GET à l'URL suivante:

=======================
```
http://localhost:6801/api/listings
```

La réponse de cette requête est la suivante:
=======================

```
 {
        "createdAt": "2024-12-14T18:21:51.348016",
        "createdBy": "LISTING_MS",
        "updatedAt": null,
        "updatedBy": null,
        "listingId": "7deff2ce-e2d1-4c35-81c7-dffa45470d82",
        "userId": "6b16380f-7e98-4761-853f-2df53fb7a508",
        "title": "Meuble",
        "description": "Meuble en trés bon état mais pas neuf",
        "price": 699.99,
        "location": "Rabat , Hay Riad",
        "status": "SOLD",
        "category": {
            "id": 1,
            "name": "Meuble de Salon",
            "description": "Meuble pas neuf "
        },
             "images": [
            {
                "idImage": 1,
                "name": "meuble.png",
                "type": "image/png",
                "image": "iVBORw0KGgoAAAANSUhEUgAAAgAA"
                
            },
            {
                "idImage": 2,
                "name": "meuble2.png",
                "type": "image/png",
                "image": "iVBORw0KGgoAAAANSUhEUgAAAgAA"
                
            }
        ]
            
            
```

NB : Il y'à toutes les oppération CRUD pour les Listings et les Catégories de Listings
=======================






