package com.revature.controller;

import java.util.Objects;

public class ReimbursementStatus {
    private String reimbStatus;

    public ReimbursementStatus() {
    }

    public ReimbursementStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public String getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(String reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbursementStatus that = (ReimbursementStatus) o;
        return Objects.equals(reimbStatus, that.reimbStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbStatus);
    }

    @Override
    public String toString() {
        return "ReimbursementStatus{" +
                "reimbStatus='" + reimbStatus + '\'' +
                '}';
    }
}
