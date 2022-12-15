# Proiect POO - POO TV

Made by Andrei Maruntis

# Etapa 1

## Design-ul claselor

### Paginile

La baza implementarii se afla clasa abstracta `Page`, care contine cele doua metode esentiale pentru orice pagina: `changePage` si `action`. Fiecare pagina (care va mosteni, bineinteles, clasa `Page`) va implementa cele doua metode, asa incat sa pot naviga intre pagini in mod corect si sa pot executa actiunile specifice fiecarei pagini.

Datorita felului in care functioneaza aceste functii (cu un bloc `switch`), pentru navigarile catre pagini ilegale, precum si actiuni ilegale, folosesc ramura `default`, astfel ca la adaugarea de noi clase/actiuni, functiile deja scrise vor functiona in continuare. De asemenea, adaugarea unei actiuni/ rute noi intre pagini necesita numai adaugarea unei ramuri noi la bloc-urile `switch` deja existente.

Un lucru semnificativ despre pagini este ca fiecare clasa care mosteneste clasa abstracta `Page` are un constructor care executa actiuni specifice la mutarea pe acea pagina; spre exemplu, la navigarea pe pagina Movies se creeaza o instanta noua a clasei `MoviesPage` care va popula in constructor lista de filme disponibile.

### Baza de date

Baza de date a aplicatiei este o clasa care urmeaza *design pattern-ul Singleton*. In ea sunt stocate date precum lista de useri, lista de filme, user-ul logat in momentul curent etc.

### Interfata `JSONable`

Pentru formatarea output-ului aplicatiei am folosit o interfata `JSONable` care contine metoda `JsonNode toJSON()`. Clasele care implementeaza aceasta interfata vor implementa aceasta functie, care returneaza campurile relevante din acea instanta sub format JSON. Exemple de clase care implementeaza aceasta interfata sunt Movie, User, MovieList.

### Clase pentru citire

Pentru a realiza citirea din fisierul JSON am creat mai multe clase (care se afla in pachetul `input`) care au campurile de cu numele specifice si tipurile de date necesare pentru o functionare buna a citirii datelor.

### Alte clase

Aplicatia mai foloseste doua clase auxiliare: o clasa care contine constante, respectiv o clasa cu diverse functii utile pe parcursul executiei, precum o functie care realizeaza delogarea unui user, traduce un `ArrayList` de `String` intr-un `ArrayNode` sau creeaza un mesaj de eroare. Aceasta din urma creeaza toate mesajele de eroare (sub forma de ObjectNode) care vor aparea in fisierul de output.

## Workflow-ul aplicatiei

La initializarea aplicatiei este populata baza de date cu userii si filmele din fisierul de intrare (prin apelul functiei `Database.initializeDatabase`), pagina curenta este setata o instanta a clasei `LoggedOutHomepage` si user-ul curent este nul. 

Mai apoi sunt executate secvential actiunile din fisierul de intrare prin apelul functiei `Helpers.runAction` care va apela, la randul ei, metodele `changePage` sau `action` ale paginii curente, dupa caz. Dupa aceste apeluri, aceasta functie va scrie in fisierul de iesire o eroare, daca este necesar.

Functiile de `changePage` si `action` folosesc un block `switch` pentru a decide codul care trebuie executat (o eroare sau o anumita actiune).

## Alte observatii

- Am incercat sa folosesc si design pattern-ul *Visitor*, astfel:
  - Paginile sunt `Visitable`, actiunile sunt `Visitors`
  - Paginile au implementarea metodei `accept(Action)` care returneaza mereu eroare
  - Metoda `accept` este supraincarcata asa incat pentru unele actiuni (spre exemplu `LoginPage` va supraincarca metoda cu semnatura `accept(LoginAction)`) sa se execute un cod, in loc de mesajul generic de eroare

  Din pacate, aceasta implementare nu a functionat, deoarece compiler-ul Java nu vedea supraincararea metodei `accept(Action)` cu metoda cu semnatura `accept(LoginAction)`, din moment ce clasa `LoginAction` mosteneste clasa `Action`. Asadar, aceasta implementare nu mai apare in forma finala a temei.

- Lucrurile pe care le-am invatat rezolvand aceasta tema sunt:
  - Cum sa folosesc design pattern-urile *Singleton* si *Visitor*
  - Cum sa fac citirea dintr-un fisier JSON (aceasta parte era deja implementata la tema)

- Este foarte naspa ca nu e posibil sa testam un test individual decat prin artificii de debug sau daca ne scrie singuri o functie de test care apeleaza dunctia `Main.main` cu cele doua argumente necesare; aceasta functie ar trebui sa vina de la voi (similar cu tema)

- Enuntul temei este **foarte prost explicat si organizat**. Unele probleme:
  - Nu este suficient de clar cand trebuie afisata o eroare, cand trebuie afisat un mesaj cu campul `Error: null` si cand nu trebuie afisat niciun mesaj.
  - In mod similar, nu scrie nicaieri ca daca afisez o eroare, celelate doua campuri (`currentMoviesList` si `currentUser`) trebuie sa fie goale
  - De ce este campul `balance` din input si ref `String`???
  - Nu are sens ca un user standard sa aiba 15 filme gratuite dar sa nu aiba acces la ele, ar fi normal ca user-ul standard sa aiba 0 filme gratuite si la trecerea la premium sa primeasca 15
  - Explicatia ca rating-ul maxim este intre 1 si 5 nu se afla unde trebuie; in momentul de fata, ea apare in paragraful care explica ierarhia sitemului de pagini; ar trebui sa fie in paragraful care explica actiunea de "rate movie"
  - Similar cu mai sus explicatia ca filmul trebuie sa fie deja vizionat pentru like, rate
  - Overall multe explicatii lipsesc si sunt compensate de faptul ca, intuitiv, anumite actiuni au anumite prerechizite (spre exemplu "rate" necesita sa fi vizionat filmul), insa asta e o abordare gresita, ar trebui ca enuntul sa contina toate informatiile necesare intr-un format clar
  - Unele explicatii lipsesc cu desavarsire: explicatiile actiunii de "filter" nu apar nicaieri, iar acest lucru a generat de departe cea mai multa frustrare; sortarea rezultatelor la filtrare se face mai intai dupa `duration` si apoi dupa `rating`, daca ambele sunt mentionate, insa **ordinea acestora in fisierele de intrare este exact invers**; in mod similar, ar trebui mentionat ca filmele trebuie sa contina **toate** genurile si **toti** actorii
  - Probabil lista nu se incheie aici, acestea sunt problemele pe care le-am intampinat eu; overall, echipa temei 1 la POO anul acesta a facut o tema foarte buna si bine explicata si au setat niste standarde foarte inalte, iar voi ati facut *just another OOP homework* de care nu te apuci in prima saptamana, fiindca scheletul e gresit/incomplet si enuntul prost explicat.