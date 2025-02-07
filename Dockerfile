# Utiliser l'image Maven comme base
# FROM maven:3.8.5-openjdk-17-slim

# # Créer un répertoire pour l'application dans le conteneur
# WORKDIR /crypto

# # Copier le fichier pom.xml et le répertoire src pour la compilation
# COPY crypto/pom.xml /crypto/pom.xml
# COPY crypto/src /crypto/src

# # Exposer le port 8080 pour accéder à l'application
# EXPOSE 8080

# Utiliser l'image Maven pour construire l'application
FROM maven:3.8.5-openjdk-17-slim AS build

# Définir le répertoire de travail
WORKDIR /crypto

# Copier le fichier pom.xml et les fichiers sources dans le conteneur
COPY crypto/pom.xml /crypto/pom.xml
COPY crypto/src /crypto/src

# Exécuter Maven pour télécharger les dépendances
RUN mvn dependency:go-offline

# Construire l'application (mais ne pas créer de JAR à ce stade)
RUN mvn compile

# Utiliser une image OpenJDK pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail pour l'exécution
WORKDIR /crypto

# Copier le fichier JAR généré par Maven (si nécessaire pour un mode non développement)
# COPY --from=build /crypto/target/*.jar /crypto/app.jar

# Exposer le port 8080
EXPOSE 8080

# Exécuter l'application Spring Boot en mode développement avec Maven
CMD ["mvn", "spring-boot:run"]
