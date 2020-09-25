package io.mycompany.ppmtool.domain;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Project identifier is required")
    @Size(min=4, max = 5, message = "Please use 4 to 5 characters")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is required")
    private String description;

    @JsonFormat(pattern = "yyyyy-mm-dd")
    private Date start_date;

    @JsonFormat(pattern = "yyyyy-mm-dd")
    private Date end_date;

    @JsonFormat(pattern = "yyyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyyy-mm-dd")
    private Date updatedAt;

    public Project() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getProjectName() { return projectName; }

    public void setProjectName(String projectName) { this.projectName = projectName; }

    public String getProjectIdentifier() { return projectIdentifier; }

    public void setProjectIdentifier(String projectIdentifier) { this.projectIdentifier = projectIdentifier; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Date getStartDate() { return start_date; }

    public void setStartDate(Date startDate) { this.start_date = startDate; }

    public Date getEndDate() { return end_date; }

    public void setEndDate(Date endDate) { this.end_date = endDate; }

    public Date getCreatedAt() { return createdAt; }

    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }

    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }
}