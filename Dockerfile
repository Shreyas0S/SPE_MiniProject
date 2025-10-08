FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the already-built fat/regular jar from Maven target directory (passed in build context)
ARG JAR_FILE=target/calculator-1.0.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]
CMD []
