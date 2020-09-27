package io.mycompany.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Backlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer PTSequence = 0;
    private String projectIdentifier;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "backlog")
    private List<ProjectTask> projectTaskList = new ArrayList<>();

    public Backlog() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getPTSequence() { return PTSequence; }

    public void setPTSequence(Integer PTSequence) { this.PTSequence = PTSequence; }

    public String getProjectIdentifier() { return projectIdentifier; }

    public void setProjectIdentifier(String projectIdentifier) { this.projectIdentifier = projectIdentifier; }

    public Project getProject() { return project; }

    public void setProject(Project project) { this.project = project; }

    public List<ProjectTask> getProjectTaskList() { return projectTaskList; }

    public void setProjectTaskList(List<ProjectTask> projectTaskList) { this.projectTaskList = projectTaskList; }
}