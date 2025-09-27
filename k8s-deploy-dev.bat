kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml

docker build --build-arg MODULE_PATH=user-svc/svc -t user-svc:latest -f services/module.Dockerfile .
docker build --build-arg MODULE_PATH=hotel-svc/svc -t hotel-svc:latest -f services/module.Dockerfile .

kubectl apply -k k8s/overlays/dev
