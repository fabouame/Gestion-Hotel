# 🏨 Application de Gestion d’Hôtel – Java Swing

Application de gestion d’hôtel développée en **Java Swing**, utilisant une base de données **MySQL** et une architecture **MVC légère**.  
Elle permet la gestion des chambres, des clients, des réservations, ainsi que l’affichage de statistiques graphiques.

---

## 📌 Fonctionnalités principales

### ✔ Gestion des chambres
- Ajouter une chambre  
- Modifier une chambre  
- Supprimer une chambre  
- Lister toutes les chambres  
- Filtrer par type (Single, Double, Suite)  
- Afficher les chambres disponibles entre deux dates  

### ✔ Gestion des clients
- Ajouter un client  
- Modifier un client  
- Supprimer un client  
- Lister tous les clients  

### ✔ Gestion des réservations
- Ajouter une réservation pour un client  
- Empêcher les chevauchements de dates  
- Afficher les réservations d’un client  
- Rechercher les réservations entre dates  

### ✔ Statistiques
- Taux d’occupation des chambres par mois  
- Graphique généré avec **JFreeChart**  

---

## 🛠️ Technologies utilisées
- **Java 8**  
- **Swing**
- **JDBC**
- **MySQL**
- **JFreeChart**
- **JCalendar**
- **mysql-connector-java**
- **NetBeans 8+**
- **Inno Setup (JRE embarquée)**

---

## 📁 Structure du projet

src/
app/
connexion/
dao/
entities/
services/
ui/
utiles/

dist/
GestionHotel.jar
lib/
jcalendar-1.4.jar
jcommon-1.0.24.jar
jfreechart-1.5.6.jar
mysql-connector-java-5.1.23.jar

jre/
setup.iss
output/ (installateur Windows .exe)

---

## 🗄️ Base de données (MySQL)

### 📌 Modèle relationnel
- **Chambre(numero, type, prixParNuit)**
- **Client(id, nom, ville, telephone)**
- **Reservation(id, chambre, client, dateDebut, dateFin)**

### 📌 Script SQL d’installation
```sql
CREATE TABLE Chambre (
  numero INT PRIMARY KEY,
  type VARCHAR(20),
  prixParNuit DOUBLE
);

CREATE TABLE Client (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nom VARCHAR(50),
  ville VARCHAR(50),
  telephone VARCHAR(20)
);

CREATE TABLE Reservation (
  id INT AUTO_INCREMENT PRIMARY KEY,
  chambre INT,
  client INT,
  dateDebut DATE,
  dateFin DATE,
  FOREIGN KEY (chambre) REFERENCES Chambre(numero),
  FOREIGN KEY (client) REFERENCES Client(id)
);


