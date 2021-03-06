## Домашнее задание по мониторингу:
### Задача
Инструментировать сервис метриками и алертами.
Инструментировать сервис из прошлого занятия метриками в формате Prometheus.
Сделать дашборд в Графане, в котором были бы метрики с разбивкой по API методам:
Настроить алертинг в графане на Error Rate и Latency.
Инструментировать базу данных с помощью экспортера для prometheus для этой БД.
Добавить в общий дашборд графики с метриками работы БД.
Используя существующие системные метрики из кубернетеса, добавить на дашборд графики с метриками:
1. Потребление подами приложения памяти
2. Потребление подами приложения CPU

## Инструкция по установке:
Запустить миникуб
В директории с чартом выполнить команды:
```
kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring
helm install prom stable/prometheus-operator -f prometheus.yaml
```

Далее запускаем приложение. Сразу запустится стресс тест.
(* прописал в etc/hosts ip миникуба для адреса arch.homework)
```
helm install shop-1 shop-chart
```
Загружаем  дашбоард homework_shop_dasboard
```
kubectl apply -f grafana-import.yaml
```

Редиректим графану
``` 
kubectl port-forward service/prom-grafana 3000:80
```

Смотрим графану (логин admin, пароль prom-operator)
http://localhost:3000/

Смотрим графики после загрузки (30 минут) 
![RPS](img/screenshot1.png)

![Pod cpu and memory consuming](img/screenshot2.png)

![db metrics](img/screenshot3.png)





 
