FROM maven:3.8.6-openjdk-18 AS build
COPY ./ /app
WORKDIR /app
RUN mvn --show-version --update-snapshots --batch-mode clean package

FROM amazoncorretto:18
RUN mkdir /app
WORKDIR /app
COPY tree_structure.json testna_struktura_1.json testna_struktura_2.json  /app
COPY --from=build ./app/target/demo-0.0.1-SNAPSHOT.jar /app
EXPOSE 8081
CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]