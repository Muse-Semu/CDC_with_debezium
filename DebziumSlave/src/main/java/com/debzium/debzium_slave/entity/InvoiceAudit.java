package com.debzium.debzium_slave.entity;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;


import java.util.Date;
import java.util.List;


@Entity
@Table(name = "invoice_audit")
public class InvoiceAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operation;

    @Lob
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode source;

    @Lob
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode before;

    @Lob
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode after;

    private String topic;


    @Column(name = "changed_column")
    private String changedColumns;

    private Date updatedDate;

    private String updatedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setChangedColumns(String changedColumns) {
        this.changedColumns = changedColumns;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "InvoiceAudit{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", source=" + source +
                ", before=" + before +
                ", after=" + after +
                ", topic='" + topic + '\'' +
                ", changedColumns=" + changedColumns +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}

