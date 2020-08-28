@ECHO OFF
chcp 65001

echo "### Génération de l'image mission-db ###"
docker build -t postgresql-mission-db mission-db\.

echo "### Création du containeur mission-db ###"
docker run -d --name postgresql-mission-db  postgresql-mission-db