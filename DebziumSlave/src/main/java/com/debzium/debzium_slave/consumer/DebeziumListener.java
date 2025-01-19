package com.debzium.debzium_slave.consumer;


import com.debzium.debzium_slave.dto.InvoiceAuditDTO;
import com.debzium.debzium_slave.mapper.InvoiceAuditMapper;
import com.debzium.debzium_slave.service.CustomerService;
import com.debzium.debzium_slave.service.InvoiceAuditService;
import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static io.debezium.data.Envelope.FieldName.*;
import static io.debezium.data.Envelope.Operation;
import static java.util.stream.Collectors.toMap;

@Component
public class DebeziumListener {


    private static final Logger log = LoggerFactory.getLogger(DebeziumListener.class);
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final DebeziumEngine<RecordChangeEvent<SourceRecord>> debeziumEngine;

    private final InvoiceAuditMapper invoiceAuditMapper;
    private final InvoiceAuditService invoiceAuditService;


    public DebeziumListener(Configuration customerConnectorConfiguration, CustomerService customerService, InvoiceAuditMapper invoiceAuditMapper, InvoiceAuditService invoiceAuditService) {
        this.debeziumEngine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(customerConnectorConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();
        this.invoiceAuditMapper = invoiceAuditMapper;
        this.invoiceAuditService = invoiceAuditService;
    }

//    private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
//        var sourceRecord = sourceRecordRecordChangeEvent.record();
//
//
////        log.info("SourceRecord = \n\n{}\n\n", sourceRecord);
//
//        log.info("Key = {} \n, \n Value = {}\n", sourceRecord.key(), sourceRecord.value());
//        var sourceRecordChangeValue= (Struct) sourceRecord.value();
////        log.info(" \n\n SourceRecordChangeValue = '{}' \n\n", sourceRecordChangeValue.getStruct("source"));
////        log.info("\n\n Source  = '{}' \n\n", sourceRecord.sourcePartition());
////        log.info("\n\n SourceOffset = '{}' \n\n", sourceRecord.sourceOffset());
////        log.info("\n \nTopic = '{}' \n\n", sourceRecord.topic());
////        log.info("Timestamp = '{}'", sourceRecord.timestamp());
////        log.info("Schema Name = '{}'", sourceRecord.valueSchema());
//
//        if (sourceRecordChangeValue != null) {
//            Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));
//
////         Operation.READ operation events are always triggered when application initializes
////         We're only interested in CREATE operation which are triggered upon new insert registry
//            if(operation != Operation.READ) {
//                String record = operation == Operation.DELETE ? BEFORE : AFTER; // Handling Update & Insert operations.
//
//                Struct struct = (Struct) sourceRecordChangeValue.get(record);
//                log.info("SourceRecordChangeValue = '{}'", struct);
//                Map<String, Object> payload = struct.schema().fields().stream()
//                        .map(Field::name)
//                        .filter(fieldName -> struct.get(fieldName) != null)
//                        .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
//                        .collect(toMap(Pair::getKey, Pair::getValue));
//
//                log.info("Updated Data: {} with Operation: {}", payload, operation.name());
////                customerService.replicateData(payload, operation);
//
//            }
//        }
//    }


private void handleChangeEvent(RecordChangeEvent<SourceRecord> sourceRecordRecordChangeEvent) {
    var sourceRecord = sourceRecordRecordChangeEvent.record();
    log.info("Key = {} \n, \n Value = {}\n", sourceRecord.key(), sourceRecord.value());

    // Extract topic
    String topic = sourceRecord.topic();

    // Extract change value and process
    Struct sourceRecordChangeValue = (Struct) sourceRecord.value();
    if (sourceRecordChangeValue != null) {
        Operation operation = Operation.forCode((String) sourceRecordChangeValue.get(OPERATION));

        // Ignore READ operation
        if (operation != Operation.READ) {
            Struct sourceStruct = (Struct) sourceRecordChangeValue.get(SOURCE);
            Struct beforeStruct = (Struct) sourceRecordChangeValue.get(BEFORE);
            Struct afterStruct = (Struct) sourceRecordChangeValue.get(AFTER);

            // Detect changed columns for UPDATE
            List<String> changedColumns = operation == Operation.UPDATE
                    ? invoiceAuditMapper.getChangedColumns(beforeStruct, afterStruct)
                    : null;

            // Map to DTO
            InvoiceAuditDTO auditDTO = invoiceAuditMapper.mapToDTO(
                    sourceStruct, beforeStruct, afterStruct,
                    operation.name(), topic, changedColumns
            );

            // Log or send the DTO to another service
            log.info("Mapped DTO: {}", auditDTO);

            // Example: Persist the DTO using a service
             invoiceAuditService.saveInvoiceAudit(auditDTO);
        }
    }
}


    @PostConstruct
    private void start() {
        this.executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (Objects.nonNull(this.debeziumEngine)) {
            this.debeziumEngine.close();
        }
    }
}