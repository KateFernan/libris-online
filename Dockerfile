FROM maven:3.9.6-eclipse-temurin-21

ENV MAVEN_OPTS="-Xmx256m"

RUN apt-get update && apt-get install -y \
    libfreetype6 \
    libpango-1.0-0 \
    libpangocairo-1.0-0 \
    libcairo2 \
    fonts-dejavu \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD mvn jpro:run -Djpro.port=$PORT