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
```

---

## 🧪 Tests et validation


Avant la livraison finale, plusieurs tests ont été effectués :

### ✔ Tests unitaires (DAO)
- Tests CRUD sur **Chambre**
- Tests CRUD sur **Client**
- Tests CRUD sur **Reservation**
- Vérification des contraintes (clés étrangères, unicité…)

### ✔ Tests fonctionnels (UI)
- Navigation entre fenêtres
- Validation des formulaires
- Prévention des chevauchements de réservations
- Filtrage dynamique des tableaux
- Rafraîchissement automatique après ajout/modification/suppression

### ✔ Tests de performance
- Temps de connexion à la base
- Génération rapide des tableaux (JTable)
- Génération des graphiques JFreeChart

### ✔ Tests d’installation Windows (Inno Setup)
- Installation sur PC sans Java  
- Lancement via `.exe`  
- Vérification de la JRE embarquée  
- Suppression propre (désinstalleur Inno Setup)

---

## 💡 Choix techniques & justification

### 🔹 MVC léger
Séparation claire :
- **Model** → Entities + DAO  
- **Controller** → Services  
- **View** → Swing (ui/)  

➡️ Facilite la maintenance et les évolutions.

### 🔹 MySQL + JDBC  
- Stable  
- Facile à intégrer  
- Large compatibilité avec Java  

### 🔹 Swing
- Interface simple mais robuste  
- Compatible avec tous les OS  
- Idéal pour applications pédagogiques

### 🔹 JFreeChart
- Librairie mature  
- Graphiques professionnels  
- Intégration facile avec Swing

---

## 🔮 Améliorations possibles (travaux futurs)

- Ajouter un système d’authentification avancé (rôles : admin, employé)
- Ajouter une gestion des paiements
- Export des rapports en PDF / Excel
- Moderniser l'interface (JavaFX ou FlatLaf)
- Ajouter un tableau de bord (dashboard)
- Système de notifications pour les réservations proches
- Migration vers **Hibernate** pour remplacer JDBC

---

##Architecture du Projet

<img width="1920" height="1080" alt="1" src="https://github.com/user-attachments/assets/8c312f88-579c-49c7-b320-85f503db5acf" />
---


##🎥 Démonstration Vidéo



https://github.com/user-attachments/assets/33d4e9a2-ae2c-4cbc-9026-c12bc71962f3



---
### Auteur  
Nom : Abouame fatemaezzahra

