Для сборки образа:
указать версию приложения в:     
sudo docker build --build-arg JAR_FILE=build/libs/*.jar -t bael/ms-course-hw1:v2 .

Для отправки в Docker hub
sudo docker push bael/ms-course-hw1:v2

postgres:
docker run --rm -d \
    --name shop-postgres \
    -e POSTGRES_PASSWORD=12345678 \
    -e PGDATA=/var/lib/postgresql/data/pgdata \
    -e POSTGRES_DB=shop \
    -p 5432:5432 \
    -v /home/dk/Projects/postgres/shop/mount:/var/lib/postgresql/data \
    postgres