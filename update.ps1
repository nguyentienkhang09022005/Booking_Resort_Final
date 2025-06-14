docker build --no-cache -t dauxu/mobile-api:1.0.3 .

docker login

docker push dauxu/mobile-api:1.0.3

gcloud auth login
gcloud config set project doanjava-api
.\deploy.ps1