FROM node:alpine as webapp-build

WORKDIR /app

COPY src/main/webapp .

RUN yarn && yarn build


FROM maven:3-openjdk-11 as mvn-build

WORKDIR /app

COPY . .
COPY --from=webapp-build /app/dist src/main/webapp/dist

RUN mvn clean package


FROM openjdk:11.0-jdk

WORKDIR /app

COPY --from=mvn-build /app/target/arev.jar .

CMD ["java", "-jar", "arev.jar"]
