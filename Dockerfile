FROM java:8
EXPOSE 8000
ARG JAR_FILE = back-end/build/libs/*.JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]