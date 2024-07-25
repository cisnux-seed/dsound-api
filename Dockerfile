FROM gradle:latest as builder
ARG SPOTIFY_CLIENT_ID="5d2537db57414ef8a45a964547fff619"
ARG SPOTIFY_AUTHORIZE_URI="https://accounts.spotify.com/authorize"
ARG SPOTIFY_SCOPE="user-read-private user-read-email"
ARG SPOTIFY_RESPONSE_TYPE="code"
ARG SPOTIFY_CODE_CHALLENGE_METHOD="S256"
ARG SPOTIFY_REDIRECT_URI="https://api.dsound.site/user/authorized"
ARG SPOTIFY_AUTHENTICATION_URL="https://accounts.spotify.com/api/token"
ARG SPOTIFY_API_URL="https://api.spotify.com/v1"
ARG ACR_ACCESS_KEY="c70f7b38afcd75fb44ab66056b8bf9ce"
ARG ACR_SECRET_KEY="t1dS0ACBgcgCeA8b6jgx8tApATIr9ZaPXBnK2QOQ"
ARG ACR_REQ_URL="https://identify-ap-southeast-1.acrcloud.com/v1/identify"
WORKDIR /app/
COPY . .

RUN ./gradlew build

FROM openjdk:21-jdk-oracle

ENV SPOTIFY_CLIENT_ID="5d2537db57414ef8a45a964547fff619"
ENV SPOTIFY_AUTHORIZE_URI="https://accounts.spotify.com/authorize"
ENV SPOTIFY_SCOPE="user-read-private user-read-email"
ENV SPOTIFY_RESPONSE_TYPE="code"
ENV SPOTIFY_CODE_CHALLENGE_METHOD="S256"
ENV SPOTIFY_REDIRECT_URI="https://api.dsound.site/user/authorized"
ENV SPOTIFY_AUTHENTICATION_URL="https://accounts.spotify.com/api/token"
ENV SPOTIFY_API_URL="https://api.spotify.com/v1"
ENV ACR_ACCESS_KEY="c70f7b38afcd75fb44ab66056b8bf9ce"
ENV ACR_SECRET_KEY="t1dS0ACBgcgCeA8b6jgx8tApATIr9ZaPXBnK2QOQ"
ENV ACR_REQ_URL="https://identify-ap-southeast-1.acrcloud.com/v1/identify"

WORKDIR /app/

COPY --from=builder /app/build/libs/api-0.0.1-SNAPSHOT.jar dsound-api.jar
COPY --from=builder main.py main.py

EXPOSE 8080
EXPOSE 9090
ENTRYPOINT ["sh", "-c", "java -jar dsound-api.jar && python3 main.py"]