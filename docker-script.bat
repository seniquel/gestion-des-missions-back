@ECHO OFF
chcp 65001

echo "### Génération de l'image postresql-mission-db ###"
docker build -t postgresql-mission-db .

echo "### Création du containeur postgresql ###"
docker run -d --name postgresql-mission-db  postgresql-mission-db