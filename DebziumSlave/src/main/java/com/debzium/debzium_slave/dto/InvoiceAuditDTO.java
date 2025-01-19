package com.debzium.debzium_slave.dto;


import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;
import java.util.List;


//@NoArgsConstructor
public class InvoiceAuditDTO {
    private String operation;
    private JsonNode source; // JSON structure representing the source
    private JsonNode before; // JSON structure for "before" struct (for DELETE and ./,)
    private JsonNode after;  // JSON structure for "after" struct (for INSERT and UPDATE)
    private String topic;    // Kafka topic name
    private String changedColumns; // Changed columns for UPDATE
    private Date updatedDate; // Timestamp from the source struct


    // No-args constructor
    public InvoiceAuditDTO() {
    }

    // All-args constructor
    public InvoiceAuditDTO(String operation, JsonNode source, JsonNode before, JsonNode after, String topic,
                           List<String> changedColumns, Date updatedDate) {
        this.operation = operation;
        this.source = source;
        this.before = before;
        this.after = after;
        this.topic = topic;
        this.changedColumns = changedColumns!=null ? changedColumns.toString() : null;
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "InvoiceAuditDTO{" +
                "operation='" + operation + '\'' +
                ", source=" + source +
                ", before=" + before +
                ", after=" + after +
                ", topic='" + topic + '\'' +
                ", changedColumns=" + changedColumns +
                ", updatedDate=" + updatedDate +
                '}';
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public JsonNode getSource() {
        return source;
    }

    public void setSource(JsonNode source) {
        this.source = source;
    }

    public JsonNode getBefore() {
        return before;
    }

    public void setBefore(JsonNode before) {
        this.before = before;
    }

    public JsonNode getAfter() {
        return after;
    }

    public void setAfter(JsonNode after) {
        this.after = after;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getChangedColumns() {
        return changedColumns;
    }

    public void setChangedColumns(List<String> changedColumns) {
        this.changedColumns = changedColumns!=null? changedColumns.toString():null;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
