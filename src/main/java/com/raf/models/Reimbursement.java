package com.raf.models;

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

    public Reimbursement(Integer id, Double amount, String time_submitted, String time_resolved, String description, String receipt, Integer author, Integer resolver, Integer status_id, Integer type_id) {
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
            "}";
    }

}
