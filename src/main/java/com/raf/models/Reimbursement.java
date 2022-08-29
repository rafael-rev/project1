package com.raf.models;

import java.math.BigDecimal;

public class Reimbursement {
    private Integer id;
    private Double amount;
    private String time_submitted;
    private String time_resolved;
    private String description;
    private String receipt;
    private Integer author;
    private Integer resolver;
    private Integer status_id;
    private Integer type_id;
    private String status;
    private String type;

    public Reimbursement(Integer id, Double amount, String time_submitted, String time_resolved, String description, String receipt, Integer author, Integer resolver, Integer status_id, Integer type_id, String status, String type) {
        this.id = id;
        this.amount = amount;
        this.time_submitted = time_submitted;
        this.time_resolved = time_resolved;
        this.description = description;
        this.receipt = receipt;
        this.author = author;
        this.resolver = resolver;
        this.status_id = status_id;
        this.type_id = type_id;
        this.status = status;
        this.type = type;
    }

    public Reimbursement(Double amount, String time_submitted, String description, Integer author, Integer type_id, Integer status_id, String type) {
        this.amount = amount;
        this.time_submitted = time_submitted;
        this.description = description;
        this.author = author;
        this.status_id = status_id;
        this.type_id = type_id;
    }

    public Reimbursement(Double amount, String description, String type){
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public Reimbursement() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTime_submitted() {
        return this.time_submitted;
    }

    public void setTime_submitted(String time_submitted) {
        this.time_submitted = time_submitted;
    }

    public String getTime_resolved() {
        return this.time_resolved;
    }

    public void setTime_resolved(String time_resolved) {
        this.time_resolved = time_resolved;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceipt() {
        return this.receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public Integer getAuthor() {
        return this.author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public Integer getResolver() {
        return this.resolver;
    }

    public void setResolver(Integer resolver) {
        this.resolver = resolver;
    }

    public Integer getStatus_id() {
        return this.status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Integer getType_id() {
        return this.type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", amount='" + getAmount() + "'" +
            ", time_submitted='" + getTime_submitted() + "'" +
            ", time_resolved='" + getTime_resolved() + "'" +
            ", description='" + getDescription() + "'" +
            ", receipt='" + getReceipt() + "'" +
            ", author='" + getAuthor() + "'" +
            ", resolver='" + getResolver() + "'" +
            ", status_id='" + getStatus_id() + "'" +
            ", type_id='" + getType_id() + "'" +
            ", status='" + getStatus() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
    

}
