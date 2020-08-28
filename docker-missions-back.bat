@ECHO OFF
chcp 65001

echo "### Génération de l'image missions-db ###"
docker build -t missions-back docker\missions-back\.

echo "### Création du containeur missions-db ###"
docker run -d --name missions-back missions-back 