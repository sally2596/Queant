FROM java:8
EXPOSE 8000
ARG JAR_FILE=./back-end/Queant/build/libs/*.jar
COPY /usr/share/zoneinfo/Asia/Seoul /etc/localtime
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
