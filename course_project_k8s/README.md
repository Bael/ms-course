# Курсовая работа
## Инструкция по установке:
Запустить миникуб
Убедиться что аддон с ингресом установлен
В директории с чартами course_project_k8s выполнить команды:
Подготовка:
```
kubectl create namespace monitoring
kubectl config set-context --current --namespace=monitoring
```

Установка kafka 
```
kubectl create namespace kafka && \
kubectl apply -k github.com/Yolean/kubernetes-kafka/variants/dev-small/?ref=v6.0.3
```

Запуск сервисов:
```
helm install accounting accounting-chart && \
helm install catalog catalog-chart  && \
helm install history history-chart  && \
helm install orders orders-chart && \
helm install inventory inventory-chart  && \
helm install auth auth-chart 
```
Настройка ингресса
``` 
kubectl apply -f auth-ingress.yaml 
kubectl apply -f auth-ingress-user.yaml
kubectl apply -f app-ingress.yaml
```






 
