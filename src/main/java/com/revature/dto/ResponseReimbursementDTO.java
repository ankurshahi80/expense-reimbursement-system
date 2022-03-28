package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;

public class ResponseReimbursementDTO {

    private int reimbId;
    private double reimbAmount;
    private Timestamp reimbSubmitted;
    private String reimbDescription;
    private InputStream reimbReceipt;
    private String reimbAuthor;
    private String reimbResolver;
    private String reimbStatus;
    private String reimbType;

    public ResponseReimbursementDTO() {
    }

    public ResponseReimbursementDTO(int reimbId, double reimbAmount, Timestamp reimbSubmitted, String reimbDescription, InputStream reimbReceipt, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbDescription = reimbDescription;
        this.reimbReceipt = reimbReceipt;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public double getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(double reimbAmount) {
        this.reimbAmount = reimbAmount;
    }

    public Timestamp getReimbSubmitted() {
        return reimbSubmitted;
    }

    public void setReimbSubmitted(Timestamp reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public String getReimbDescription() {
        return reimbDescription;
    }

    public void setReimbDescription(String reimbDescription) {
        this.reimbDescription = reimbDescription;
    }

    public InputStream getReimbReceipt() {
        return reimbReceipt;
    }

    public void setReimbReceipt(InputStream reimbReceipt) {
        this.reimbReceipt = reimbReceipt;
    }

    public String getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(String reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public String getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(String reimbResolver) {
        this.reimbResolver = reimbResolver;
    }

    public String getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getReimbType() {
        return reimbType;
    }

    public void setReimbType(String reimbType) {
        this.reimbType = reimbType;
    }
}
