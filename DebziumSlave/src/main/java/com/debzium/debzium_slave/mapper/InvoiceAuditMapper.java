package com.debzium.debzium_slave.mapper;

import com.debzium.debzium_slave.dto.InvoiceAuditDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InvoiceAuditMapper {

    private final ObjectMapper objectMapper;

    public InvoiceAuditMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InvoiceAuditDTO mapToDTO(Struct sourceStruct, Struct beforeStruct, Struct afterStruct,
                                    String operation, String topic, List<String> changedColumns) {
        try {
            // Convert Structs to JsonNode
            JsonNode source = sourceStruct != null ? structToJsonNode(sourceStruct) : null;
            JsonNode before = beforeStruct != null ? structToJsonNode(beforeStruct) : null;
            JsonNode after = afterStruct != null ? structToJsonNode(afterStruct) : null;

            // Extract updated date and user from the source
            Date updatedDate = sourceStruct != null && sourceStruct.get("ts_ms") != null
                    ? new Date((Long) sourceStruct.get("ts_ms"))
                    : null;


            // Create and return the DTO
            return new InvoiceAuditDTO(
                    operation,
                    source,
                    before,
                    after,
                    topic,
                    changedColumns,
                    updatedDate
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to map Struct to InvoiceAuditDTO", e);
        }
    }

    private JsonNode structToJsonNode(Struct struct) {
        Map<String, Object> structMap = struct.schema().fields().stream()
                .filter(field -> struct.get(field.name()) != null)
                .collect(Collectors.toMap(Field::name, field -> struct.get(field.name())));

        return objectMapper.valueToTree(structMap);
    }

    public List<String> getChangedColumns(Struct beforeStruct, Struct afterStruct) {
        if (beforeStruct == null || afterStruct == null) {
            return Collections.emptyList();
        }

        List<String> changedColumns = new ArrayList<>();
        for (Field field : beforeStruct.schema().fields()) {
            Object beforeValue = beforeStruct.get(field.name());
            Object afterValue = afterStruct.get(field.name());

            if (!Objects.equals(beforeValue, afterValue)) {
                changedColumns.add(field.name());
            }
        }
        return changedColumns;
    }
}
