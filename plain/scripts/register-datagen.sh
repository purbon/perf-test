#!/usr/bin/env bash


curl -i -X PUT http://localhost:18083/connectors/datagen_local_01/config \
     -H "Content-Type: application/json" \
     -d '{
            "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
            "key.converter": "io.confluent.connect.json.JsonSchemaConverter",
            "key.converter.schema.registry.url": "http://schema-registry:8081",
            "value.converter": "io.confluent.connect.json.JsonSchemaConverter",
            "value.converter.schema.registry.url": "http://schema-registry:8081",
            "schema.keyfield": "userid",
            "kafka.topic": "pageviews",
            "quickstart": "pageviews",
            "max.interval": 1000,
            "iterations": 10000000,
            "tasks.max": "1"
        }'



curl -i -X PUT http://localhost:18083/connectors/datagen_local_02/config \
     -H "Content-Type: application/json" \
     -d '{
              "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
              "key.converter": "io.confluent.connect.json.JsonSchemaConverter",
              "key.converter.schema.registry.url": "http://schema-registry:8081",
              "value.converter": "io.confluent.connect.json.JsonSchemaConverter",
              "value.converter.schema.registry.url": "http://schema-registry:8081",
              "kafka.topic": "users",
              "quickstart": "users",
              "max.interval": 1000,
              "iterations": 10000000,
              "tasks.max": "1"
        }'


curl http://localhost:18083/connectors/datagen_local_01/status | jq .


curl -X DELETE  http://localhost:18083/connectors/datagen_local_01
curl -X DELETE  http://localhost:18083/connectors/datagen_local_02

curl  http://localhost:18083/connectors



curl -i -X PUT http://localhost:18083/connectors/datagen_dlq/config \
     -H "Content-Type: application/json" \
     -d '{
              "connector.class": "io.confluent.kafka.connect.datagen.DatagenConnector",
              "key.converter": "io.confluent.connect.json.JsonSchemaConverter",
              "key.converter.schema.registry.url": "http://schema-registry:8081",
              "value.converter": "io.confluent.connect.json.JsonSchemaConverter",
              "value.converter.schema.registry.url": "http://schema-registry:8081",
              "kafka.topic": "users",
              "quickstart": "users",
              "max.interval": 1000,
              "iterations": 10000000,
              "tasks.max": "1"
        }'
