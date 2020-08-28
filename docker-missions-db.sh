echo "### Génération de l'image missions-db ###"
docker build -t postgresql-missions-db docker/missions-db/.

echo "### Création du containeur missions-db ###"
docker run -d --name postgresql-missions-db postgresql-missions-db 