# Используем базовый образ с установленной Java
FROM openjdk:17

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

COPY ./. ./.

CMD ["./mvnw", "spring-boot:run"]