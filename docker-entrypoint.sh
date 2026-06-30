#!/bin/bash
set -e

echo "Waiting for MySQL at ${MYSQL_HOST}:3306..."
while ! nc -z "${MYSQL_HOST}" 3306; do
  echo "MySQL not ready yet... sleeping 3s"
  sleep 3
done

echo "MySQL is ready. Starting application..."
exec java -jar /app/app.jar