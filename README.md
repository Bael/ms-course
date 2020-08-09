# ms-course
homeworks for microservices course

used ports:

* users app port 8000
* catalog app port 8082
* orders app port 8083
* accounting app port 8084
* inventory app port 8085

to run prometheus - 
```
docker run --rm -d -p 9090:9090 -v ./prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus
```