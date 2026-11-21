package com.spam.ai_healthcare_backend.dto;

import java.time.LocalDateTime;

public class RecentReportDTO {

    private Long reportId;
    private String reportName;
    private String reportType;
    private LocalDateTime uploadedAt;

    public RecentReportDTO(Long reportId,
            String reportName,
            String reportType,
            LocalDateTime uploadedAt) {

        this.reportId = reportId;
        this.reportName = reportName;
        this.reportType = reportType;
        this.uploadedAt = uploadedAt;
    }

    public Long getReportId() {
        return reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}