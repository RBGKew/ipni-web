FROM openjdk:8-jre-alpine

ADD target/indexer.jar ./indexer.jar

ENTRYPOINT ["java", "-jar", "./indexer.jar"]
