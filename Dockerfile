FROM debezium/connect:1.4
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-jdbc:10.7.1
