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

# Etapa 2

## Cerinte

Cerintele etapei 2 in plus fata de prima intra in 4 mari categorii:

- adaugare/stergere filme
- notificari
- undo page
- recomandare pentru useri premium

Voi aborda implementarea relativ la aceste 4 cerinte, cu mentiunea ca ultima nu este implementata complet.

## Explicatii rezolvari

1. Adaugare/stergere filme

Aceasta cerinta a fost destul de simpla, implementarea celor doua functionalitati se gaseste in clasa `Database`, functiile `addMovie` si `deleteMovie`. Implementarea lor este destul de straightforward; verific daca acel film exista in baza de date, il adaug/sterg dupa caz si apoi notific userii abonati pentru film nou (mai multe detalii la sectiunea urmatoare), respectiv sterg toate aparitiile acelui film din listele de cumparate/vizionate etc. ale userilor.

2. Notificari

Pentru notificari am implementat un *Observer design pattern*, astfel:

- Observerii implementeaza interfata `MyObserver`, subiectii extind clasa `MySubject` cu metodele/campurile specifice
- Observerii sunt userii (clasa `User` implementeaza acum interfata `MyObserver`)
- Subiectii sunt o noua clasa `Genre` care extinde clasa `MySubject`
- Singleton-ul `Database` contine acum si un `ArrayList<Genre>` unde vor fi stocati toti subiectii pentru acces de oriunde din program
- Cand se adauga filme in baza de date (la inceputul executiei, respectiv la comenzi de tipul **database add**) imi actualizez, daca este necesar, lista de `Genre` din `Database` asa incat sa am cate o instanta pentru fiecare gen (Action, Thriller etc.)
- Atunci cand un user da comanda **subscribe** atasez acel user ca observator pentru genul la care se aboneaza
- Cand se adauga filme noi in baza de date, notific toti userii atasati fiecarui gen al filmului, avand in vedere sa dau maxim o notificare pentru un film fiecarui user (un user abonat la genurile Action si Thriller va primi o singura notificare cand se adauga un film care apartine ambelor genuri).

In plus, notifcarile in sine sunt instante ale clasei `Notification` (care contine doua campuri de tipul `String` pentru mesaj si pentru numele filmului), iar fiecare user va avea asociat un `ArrayList<Notification>`, initial gol, care va contine notificarile fiecarui user. De asemenea, clasa `Notification` implementeaza interfata `JSONable` pentru output.

3. Undo page

> Sugestie: Implementarea acestei actiuni necesita implementarea design pattern-ului *command* inclusiv pentru actiunea de change page normala. Insa noi nu stiam aceste design pattern-uri pentru prima etapa a proiectului, astfel ca ar fi fost necesar sa refactorizam o parte semnificativa din proiect pentru implementarea corespunzatoare a design pattern-ului command, ceea ce nu mi se pare foarte ok :(

Actiunea de undo page foloseste o tehnica inspirata din design pattern-ul *command*: 

- In clasa `Commander` stochez o stiva cu starea paginilor accesate de user prin intermediul clasei `DatabaseSnapshot`, care contine doua campuri pentru pagina, respectiv pentru lista de filme disponibile pe acea pagina
- Clasa commander este instantiata la logarea unui user si initializata cu pagina `LoggedInHomepage`
- La fiecare actiune de change page valida, adaug in stiva noua pagina accesata
- Cand se executa actiunea de **undo**, scot din stiva primul element pentru a avea acces la ultima pagina accesata; in plus, daca acea pagina este `MoviesPage` sau `MovieDetailsPage` afisez la output un mesaj corespunzator
- Daca stiva este goala sau instanta de commander inexistenta, atunci actiunea de undo genereaza o eroare

4. Recomandare pentru useri premium

Asa cum am mentionat mai devreme, aceasta functionalitate nu este implementata complet. De fapt, ea functioneaza corect doar daca user-ul nu a dat like niciunui film.

Implementarea acestei functionalitati se gaseste in clasa `Helpers`, functia `createRecommendation` care este apelata automat la finalul fiecarui fisier de intrare. Daca user-ul este premium, ea genereaza un top al genurilor vizionate de user, astfel:

- Mai intai contruieste un `Map` cu chei genre si valori numarul de filme din acel gen la care user-ul a dat like

- Apoi afiseaza acele valori la consola; pentru restul implementarii ar trebui puse intr-un `ArrayList` (sau `PriorityQueue`) pentru a fi sortate, ca mai departe sa selectez filmul corect pentru recomandare

- La final, functia afiseaza o notificare cu continutul "No recommendation" user-ului logat

## Alte functionalitati

Una dintre functionalitatile suplimentare (si care nu a fost mentionata in enunt!!) a fost o verificare pentru atunci cand un user da purchase/like etc. unui film care a fost deja cumparat/apreciat de el, caz in care nu fac nimic nou, de fapt.

Merita mentionata in mod special situatia in care user-ul da rating unui film caruia ii daduse deja rating, trebuie suprascris acel rating si recalculat rating-ul filmului. Pentru a rezolva aceasta problema, am adaugat clasei `User` un camp `ratingsMap` care este un `HashMap` in care stochez toate rating-urile date de acel user filmelor. Daca filmul caruia ii da rating apare in acel `HashMap`, inlocuiesc acea valoare si recalculez corespunzator rating-ul filmului.

# Alte observatii

Acest proiect a folosit doua design pattern-uri pentru implementare:

- Singleton
- Observer

De asemenea, m-am inspirat din elemente ale urmatoarelor design pattern-uri:

- Command pentru actiunea de undo page (care ar fi trebuie sa acopere si actiunea de change page normala)
- Strategy pentru actiunea de change page (decid ce actiune execut la change page in functie de parametrul - numele noii pagini)

O mica observatie legata de enunt: de data aceasta este ceva mai clar enuntul, dar inca lipsesc observatii (actiunea de suprascriere a rating-urilor explicata mai sus, spre exemplu). De asemenea, s-a schimbat tipul de data la an =)))