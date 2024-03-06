FROM eclipse-temurin:8

# Add maven
RUN apt-get update && apt-get install -y maven gnupg