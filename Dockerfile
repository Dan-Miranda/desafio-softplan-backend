FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/api.jar
WORKDIR /app
COPY . /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/api.jar"]
