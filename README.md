# PROGETTO TWITTER API FRIENDS

## Introduzione
La nostra applicazione ha come obiettivo l'analisi dei *friends* (le persone seguite da un utente) di un profilo Twitter. I dati relativi ai *friends* sono ottenuti tramite la Twitter API v2. L'analisi consiste nel filtraggio dei *friends* e nel calcolo di statistiche.  

L'utente, grazie ad un browser o a [Postman](https://www.postman.com 'Vai su Postman') può utilizzare la nostra applicazione grazie al Web Service Tomcat integrato nel Framework Spring

---
## Plus del programma 
- Salvataggio dei dati in locale
- Statistiche e filtri aggiuntivi 
- Filtri sovrapponibili
- Eccezioni personalizzate 

---

## Rotte
L'utente può effettuare le richieste dal seguente indirizzo:
```http
http://localhost:8080
```
Le rotte sono le seguenti:

|   N°   |  TIPO  |   ROTTA   |  DESCRIZIONE  |
| ------ | ------ |  -------  |  -----------  |
| 1      | GET  | /get_all_friends| Mostra tutti gli amici di un utente |
| 2      | GET | /stats|Mostra statistiche dei friend di un utente |
| 3      | GET    | /filter | Filtra i friends in base al loro numero di follower|
| 4      | GET | /filter_following  | Mostra se degli utenti sono amici di un dato profilo |

### `GET /get_all_friends`

Esempio di chiamata:

[http://localhost:8080/get_all_friends?username=UnivPoliMarche](http://localhost:8080/get_all_friends?username=UnivPoliMarche)

Risultato della chiamata:
```json
{
    "data": [
        {
            "id": "1398745924285911043",
            "name": "Giulio Milanese",
            "username": "GiulioMilanese1",
            "description": "Urologist at UNIVPM Dep. of Urology AV5\n\nRobotic surgery, Laparoscopy, PCNL, RIRS, Endourology, Uro-gynecology and Incontinence surgery",
            "location": "Ascoli Piceno",
            "verified": false,
            "public_metrics": {
                "followers_count": 37,
                "following_count": 79,
                "tweet_count": 12,
                "listed_count": 0
            }
        },

        .
        .
        .

        {
            "id": "377914543",
            "name": "CensisGuida.it",
            "username": "censisguida",
            "description": "CensisGuida.it ti orienta nel mondo dei #master, dell'#Università e dell'alta #formazione. Anche su Facebook http://t.co/q2TEuVnv6N",
            "location": null,
            "verified": false,
            "public_metrics": {
                "followers_count": 782,
                "following_count": 0,
                "tweet_count": 2716,
                "listed_count": 16
            }
        }
    ],
    "meta": {
        "result_count": 1019
    }
}
```
### Struttura lista Json

Il Json sopra riportato presenta due nodi:
- data  
  - id = ID dell'utente;
  - name = nome dell'utente;
  - username = nome identificativo dell'utente;
  - description = descrizione del profilo dell'utente;
  - location = posizione indicata nel profilo dell'utente;
  - verified = indica se l'utente è un utente Twitter verificato;
  - public_metrics = contiene dati riguardanti l'attività dell'utente:
    - followers_count = numero di follower;
    - following_count = numero di persone seguite;
    - tweet_count = numero di tweet;
    - listed_count = numero di liste.
- meta 
  - result_count = numero di risultati.

### `GET /stats`

Esempio di chiamata:

[http://localhost:8080/stats?username=UnivPoliMarche](http://localhost:8080/stats?username=UnivPoliMarche)

Risultato della chiamata:
```json
{
    "friends_followers_average_number": 108041,
    "friends_following_average_number": 2921,
    "friends_tweets_average_number": 19244,
    "friends_percentage_with_description": 90.97
}
```
I campi del Json sono autodescrittivi.


### `GET /filter`

Parametri:
- word = parola cercata nella descrizione;
- min_tweets = numero minimo di tweet;
- min_followers = numero minimo di follower.

Esempio 1 di chiamata:

[http://localhost:8080/filter?username=UnivPoliMarche&word=ricerca](http://localhost:8080/filter?username=UnivPoliMarche&word=ricerca)

Risultato della chiamata:
```json
{
    "data": [
        {
            "id": "1185502412385259521",
            "name": "S.I.R.O.E.",
            "username": "SIROE65796143",
            "description": "Account ufficiale della #Società #Italiana per la #Ricerca sugli #Oli #Essenziali - #SIROE",
            "location": null,
            "verified": false,
            "public_metrics": {
                "followers_count": 69,
                "following_count": 65,
                "tweet_count": 194,
                "listed_count": 2
            }
        },

        .
        .
        .

        {
            "id": "146892041",
            "name": "Controcampus",
            "username": "controcampus",
            "description": "Controcampus è il magazine più letto dai giovani e non solo. Ogni giorno notizie su scuola - università - formazione - ricerca - lavoro. Iscriviti!",
            "location": "italia",
            "verified": false,
            "public_metrics": {
                "followers_count": 1231,
                "following_count": 1927,
                "tweet_count": 26295,
                "listed_count": 28
            }
        }
    ],
    "meta": {
        "result_count": 37
    }
}
```
Esempio 2 di chiamata:

[http://localhost:8080/filter?username=UnivPoliMarche&word=ricerca&min_tweets=1000&min_followers=10000](http://localhost:8080/filter?username=UnivPoliMarche&word=ricerca&min_tweets=1000&min_followers=10000)

Risultato della chiamata:
```json
{
    "data": [
        {
            "id": "98855762",
            "name": "Fondazione GIMBE",
            "username": "GIMBE",
            "description": "Ente indipendente di ricerca e formazione che difende un servizio sanitario pubblico, equo e universalistico.\n#SalviamoSSN #iostoconGIMBE",
            "location": "Italy, Bologna",
            "verified": false,
            "public_metrics": {
                "followers_count": 26671,
                "following_count": 593,
                "tweet_count": 14318,
                "listed_count": 199
            }
        },

        .
        .
        .

        {
            "id": "145578712",
            "name": "Università degli Studi di Milano",
            "username": "LaStatale",
            "description": "Il 5x1000 alla Statale è un investimento sui giovani e sulla libera ricerca",
            "location": "Milano",
            "verified": false,
            "public_metrics": {
                "followers_count": 24074,
                "following_count": 784,
                "tweet_count": 12816,
                "listed_count": 282
            }
        }
    ],
    "meta": {
        "result_count": 8
    }
}
```

Descrizione della [struttura dei Json](#struttura-lista-json) sopra riportati.


### `GET /filter_following`

Parametri:
- friends_names = nomi degli amici.

Esempio di chiamata:

[http://localhost:8080/filter_following?username=elonmusk&friends_names=nasa,tesla,youtube](http://localhost:8080/filter_following?username=elonmusk&friends_names=nasa,tesla,youtube)

Risultato della chiamata:
```json
{
    "data": [
        {
            "name": "nasa",
            "following": true
        },
        {
            "name": "tesla",
            "following": true
        },
        {
            "name": "youtube",
            "following": false
        }
    ],
    "meta": {
        "result_count": 3
    }
}
```
Il Json sopra riportato presenta due nodi:
- data  
  - name = nome dell'account;
  - following = indica se il profilo viene seguito o meno.
- meta 
  - result_count = numero di risultati.


---

## DOCUMENTAZIONE
Il codice è stato commentato in Javadoc.

---

## STRUMENTI UTILIZZATI

- Eclipse (IDE per scrivere lo sviluppo dell'applicativo in Java)
- Postman (Software per richiamare e testare le chiamate)
- Springboot (Framework per creare applicazioni basate sul framework Java Spring)
- Javadoc (Per la generazione automatica della documentazione del codice sorgente scritto in linguaggio Java)
- Visual Studio Code (IDE per l'editing e il rendering in tempo reale del documento di markdown per il README)

---

## ECCEZIONI
Oltre alle eccezioni standard di java abbiamo aggiunto delle eccezioni personalizzate: 
- NotExistingAccountException 
   
  Viene richiamata quando l'utente cercato non è presente nell'api di twitter. Viene visualizzato il seguente messaggio:
  ```java
  "NON ESISTE NESSUN ACCOUNT TWITTER CON IL SEGUENTE NOME IDENTIFICATIVO: [username]"
  ```  

- NegativeNumberException

  Viene richiamata quando l'utente inserisce valori negativi dentro campi che accettano solo valori positivi. Viene visualizzato il seguente messaggio:
  ```java
  "NUMERO NEGATIVO INSERITO: [traccia]
  INSERIRE SOLO NUMERI POSITIVI!"
  ```  

---

## TEST JUNIT
Nell'applicazione sono presenti anche una serie di test:
- Test 1

  Verifica se la media dei follower degli amici viene calcolata correttamente.

- Test 2

  Verifica se la percentuale degli amici che hanno una descizione viene calcolata correttamente.

---

## AUTORI
- Mihail Bobeica (50%)
- Giovanni Ghiotto (50%)
