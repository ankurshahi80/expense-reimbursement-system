package com.revature.dto;

import java.io.InputStream;

public class AddReimbursementDTO {

    private double reimbAmount;
    private String reimbDescription;
    private InputStream reimbReceipt;
    private int reimbType;

    public AddReimbursementDTO() {
    }

    public double getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(double reimbAmount) {
        this.reimbAmount = reimbAmount;
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

    public int getReimbType() {
        return reimbType;
    }

    public void setReimbType(int reimbType) {
        this.reimbType = reimbType;
    }
}
