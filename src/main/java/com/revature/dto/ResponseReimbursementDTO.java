package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class ResponseReimbursementDTO {

    private int reimbId;
    private double reimbAmount;
    private Timestamp reimbSubmitted;
    private Timestamp reimbResolved;
    private String reimbDescription;
    private String reimbAuthor;
    private String reimbResolver;
    private String reimbStatus;
    private String reimbType;

    public ResponseReimbursementDTO() {
    }

    public ResponseReimbursementDTO(int reimbId, double reimbAmount, Timestamp reimbSubmitted, Timestamp reimbResolved, String reimbDescription, String reimbAuthor, String reimbResolver, String reimbStatus, String reimbType) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
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

    public Timestamp getReimbResolved() {
        return reimbResolved;
    }

    public void setReimbResolved(Timestamp reimbResolved) {
        this.reimbResolved = reimbResolved;
    }

    public String getReimbDescription() {
        return reimbDescription;
    }

    public void setReimbDescription(String reimbDescription) {
        this.reimbDescription = reimbDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseReimbursementDTO that = (ResponseReimbursementDTO) o;
        return reimbId == that.reimbId && Double.compare(that.reimbAmount, reimbAmount) == 0 && Objects.equals(reimbSubmitted, that.reimbSubmitted) && Objects.equals(reimbResolved, that.reimbResolved) && Objects.equals(reimbDescription, that.reimbDescription) && Objects.equals(reimbAuthor, that.reimbAuthor) && Objects.equals(reimbResolver, that.reimbResolver) && Objects.equals(reimbStatus, that.reimbStatus) && Objects.equals(reimbType, that.reimbType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, reimbAmount, reimbSubmitted, reimbResolved, reimbDescription, reimbAuthor, reimbResolver, reimbStatus, reimbType);
    }

    @Override
    public String toString() {
        return "ResponseReimbursementDTO{" +
                "reimbId=" + reimbId +
                ", reimbAmount=" + reimbAmount +
                ", reimbSubmitted=" + reimbSubmitted +
                ", reimbResolved=" + reimbResolved +
                ", reimbDescription='" + reimbDescription + '\'' +
                ", reimbAuthor='" + reimbAuthor + '\'' +
                ", reimbResolver='" + reimbResolver + '\'' +
                ", reimbStatus='" + reimbStatus + '\'' +
                ", reimbType='" + reimbType + '\'' +
                '}';
    }
}

