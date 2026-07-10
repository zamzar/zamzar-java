ARG JAVA_VERSION=8
FROM eclipse-temurin:${JAVA_VERSION}

# Add maven
RUN apt-get update && apt-get install -y maven gnupg