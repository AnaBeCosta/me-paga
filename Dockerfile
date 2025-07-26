# Estágio 1: Build da aplicação com Maven
# Usamos uma imagem completa do Maven com JDK para compilar o código
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Baixa as dependências. Se o pom.xml não mudar, o Docker reutilizará o cache desta camada
RUN mvn dependency:go-offline

# Copia o resto do código fonte
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests

# Estágio 2: Criação da imagem final
# Usamos uma imagem base do Java muito menor, apenas com o necessário para rodar a aplicação
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia apenas o arquivo .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta que a aplicação Spring Boot usa (padrão 8080)
EXPOSE 8080

# Comando para iniciar a aplicação quando o contêiner for executado
ENTRYPOINT ["java", "-jar", "app.jar"]
