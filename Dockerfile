# Giai đoạn 1: Build code bằng Maven (Dùng JDK 21)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn 2: Chạy web trên Tomcat (Dùng JDK 21)
FROM tomcat:10.1-jdk21
# Copy file WAR từ giai đoạn 1 sang Tomcat
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
