FROM gradle:latest as builder
ARG SPOTIFY_CLIENT_ID="5d2537db57414ef8a45a964547fff619"
ARG SPOTIFY_AUTHORIZE_URI="https://accounts.spotify.com/authorize"
ARG SPOTIFY_SCOPE="user-read-private user-read-email"
ARG SPOTIFY_RESPONSE_TYPE="code"
ARG SPOTIFY_CODE_CHALLENGE_METHOD="S256"
ARG SPOTIFY_REDIRECT_URI="https://api.dsound.site/user/authorized"
WORKDIR /app/
COPY . .

RUN ./gradlew build

FROM openjdk:21-jdk-oracle

ENV SPOTIFY_CLIENT_ID="5d2537db57414ef8a45a964547fff619"
ENV SPOTIFY_AUTHORIZE_URI="https://accounts.spotify.com/authorize"
ENV SPOTIFY_SCOPE="user-read-private user-read-email"
ENV SPOTIFY_RESPONSE_TYPE="code"
ENV SPOTIFY_CODE_CHALLENGE_METHOD="S256"
ENV SPOTIFY_REDIRECT_URI="https://api.dsound.site/authorized"

WORKDIR /app/

COPY --from=builder /app/build/libs/api-0.0.1-SNAPSHOT.jar dsound-api.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -jar dsound-api.jar"]