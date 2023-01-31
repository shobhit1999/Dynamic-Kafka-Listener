# Dynamic Kafka Listener

## Highlights

-   Create Kafka Listener at Runtime.
-   No need to add KafkaLisener annotation for every kafka consumer.
-   All kafka properties are derived from table.
-   Optimized code as compare to previous kafka consumer implementation.

## Versions

- Java: 14
- Kafka: 2.5.6.RELEASE

## Dependency

- Spring Kafka
- Lombok
- Spring Starter Validation
- Mysql Connector
- AOP

## Environment Variables

```
spring.datasource.url=<your MySQL DB url>
spring.datasource.username=<your MySQL username>
spring.datasource.password=<your MySQL password>
server.port=9999
kafka.servers=<IPv4>
```

## Steps to run

1. Clone the project.
2. Install Maven
3. Setup Environment variables as mentioned above
4. Create [properties](https://github.com/shobhit1999/DynamicKafkaListener/blob/main/schema.sql) table in mysql
5. Insert each [Listner Properties](https://github.com/shobhit1999/DynamicKafkaListener/blob/main/src/main/java/com/dynamic/kafkalistener/enumeration/DynamicKafkaListenerType.java) (name column in properties table should be DynamicKafkaListenerType enum).
6. Run the application
