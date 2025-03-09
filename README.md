## Установка и запуск

### 1. Клонируйте репозиторий

```bash
git clone https://github.com/ORAkhmetov/store.git
```
### 2. Сборка проекта
```bash
mvn clean package
```

### 4. Настройка подключения к базе данных
Используй переменные для подключения к базе данных Postgres

`spring.datasource.url`

`spring.datasource.username`

`spring.datasource.password`
### 3. Запуск jar файла
```bash
java -jar target/store-0.0.1-SNAPSHOT.jar
```
### 4. Запуск в контейнере

```bash
docker build -t myapp .
```
```bash
docker run -d -p 8080:8080 --name myapp-container myapp
```
### 5. Остановка контейнера
```bash
docker stop myapp-container
```