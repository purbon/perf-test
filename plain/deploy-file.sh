#!/usr/bin/env bash

PWD=`pwd`

docker run -it  --network plain_default \
       -v $PWD/sql:/sql \
       confluentinc/ksqldb-cli \
       ksql --file /sql/demo.sql http://ksqldb-server:8088
