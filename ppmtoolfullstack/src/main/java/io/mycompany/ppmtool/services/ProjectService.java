package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.Project;
import io.mycompany.ppmtool.exceptions.ProjectIdException;
import io.mycompany.ppmtool.repositories.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if( project == null) {
            throw new ProjectIdException("Project ID: '" + projectId + "' does not exists");
        }

        return project;
    }

    public Iterable<Project> findAllProjects() { return projectRepository.findAll(); }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if( project == null) {
            throw new ProjectIdException("Project ID: '" + projectId + "' does not exists, this cannot delete it!");
        }

        projectRepository.delete(project);
    }
}