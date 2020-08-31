echo "### Compilation Maven ###"
mvn clean package

echo "### Execution docker-compose ###"
docker-compose up -d --build