# Use existing image as base
FROM eclipse-temurin:25-jre-alpine

WORKDIR /opt/myApp

# Retrieve needed files and dependencies
COPY ./target/WombatPlanning-0.0.1-SNAPSHOT.jar WombatPlanning-0.0.1-SNAPSHOT.jar

# Specify a start-up command
CMD ["java", "-jar", "WombatPlanning-0.0.1-SNAPSHOT.jar"]

# Specify a start-up command with the active profile
#ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/web-frontend-0.0.1-SNAPSHOT.jar"]