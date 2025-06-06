# Usa a imagem oficial do OpenJDK 21 (para Java 21)
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo pom.xml e o diretório src para o container
COPY pom.xml .
COPY src ./src

# Instala o Maven para construir a aplicação
RUN apk add --no-cache maven

# Compila o projeto e empacota o jar
RUN mvn clean package -DskipTests

# Copia o jar gerado para o diretório /app
# O nome do jar será algo como mepaga-0.0.1-SNAPSHOT.jar
# Ajuste caso tenha outro nome ou versão
RUN cp target/mepaga-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java","-jar","app.jar"]
