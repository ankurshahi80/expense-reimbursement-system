package com.revature.dto;

import java.io.InputStream;
import java.util.Objects;

public class AddReimbursementDTO {

    private double reimbAmount;
    private String reimbDescription;
    private InputStream reimbReceipt;
    private int reimbType;

    public AddReimbursementDTO() {
    }

    public AddReimbursementDTO(double reimbAmount, String reimbDescription, InputStream reimbReceipt, int reimbType) {
        this.reimbAmount = reimbAmount;
        this.reimbDescription = reimbDescription;
        this.reimbReceipt = reimbReceipt;
        this.reimbType = reimbType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddReimbursementDTO that = (AddReimbursementDTO) o;
        return Double.compare(that.reimbAmount, reimbAmount) == 0 && reimbType == that.reimbType && Objects.equals(reimbDescription, that.reimbDescription) && Objects.equals(reimbReceipt, that.reimbReceipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbAmount, reimbDescription, reimbReceipt, reimbType);
    }

    @Override
    public String toString() {
        return "AddReimbursementDTO{" +
                "reimbAmount=" + reimbAmount +
                ", reimbDescription='" + reimbDescription + '\'' +
                ", reimbReceipt=" + reimbReceipt +
                ", reimbType=" + reimbType +
                '}';
    }
}
