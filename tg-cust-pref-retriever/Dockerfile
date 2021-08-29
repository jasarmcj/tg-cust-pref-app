FROM openjdk:8-jdk-alpine as build
RUN apk add --no-cache maven
WORKDIR /java
COPY . /java
RUN mvn clean package -Dmaven.test.skip=true
EXPOSE 4378
ENTRYPOINT ["java","-jar","/java/target/tg-cust-pref-retriever-1.0.jar"]