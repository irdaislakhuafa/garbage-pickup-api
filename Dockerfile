FROM eclipse-temurin:17-jre-jammy
WORKDIR /apps
EXPOSE 8080
COPY target/*.war app.war
CMD [ "java", "-jar", "app.war" ]