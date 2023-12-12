# Используем базовый образ с установленной Java
FROM openjdk:17

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /TaskManagerSystemApi

# Копируем JAR-файл приложения в контейнер
COPY target/TaskManagerSystemApi-0.0.1-SNAPSHOT.jar .

# Определяем команду для запуска приложения
CMD ["java", "-jar", "TaskManagerSystemApi-0.0.1-SNAPSHOT.jar"]