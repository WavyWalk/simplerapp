FROM openjdk:11

RUN apt-get update && \
    apt-get install -qy \
        ruby-full \
        ruby-dev \
        build-essential \
        patch \
        zlib1g-dev \
        liblzma-dev && \
    gem install standalone_migrations && \
    apt-get install -qy \
        postgresql-client \
        libpq5 \
        libpq-dev && \
    gem install pg

RUN mkdir -p /app

COPY ./migrations /app/migrations
COPY ./build/libs /app