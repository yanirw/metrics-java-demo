#!/bin/bash

echo "Building Docker image..."
docker build -t hello-world-metrics:latest .

echo "Running the application..."
docker run -d --name hello-world-test -p 8080:8080 hello-world-metrics:latest

echo "Waiting for application to start..."
sleep 10

echo "Testing Hello World endpoint..."
curl http://localhost:8080/

echo -e "\n\nTesting health endpoint..."
curl http://localhost:8080/actuator/health

echo -e "\n\nTesting metrics endpoint..."
curl http://localhost:8080/actuator/metrics

echo -e "\n\nTesting Prometheus metrics endpoint..."
curl http://localhost:8080/actuator/prometheus

echo -e "\n\nStopping and removing test container..."
docker stop hello-world-test
docker rm hello-world-test

echo "Test completed!" 