package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.Backlog;
import io.mycompany.ppmtool.domain.Project;

import io.mycompany.ppmtool.exceptions.ProjectIdException;
import io.mycompany.ppmtool.repositories.BacklogRepository;
import io.mycompany.ppmtool.repositories.ProjectRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    /**
     *
     * @param project
     * @return
     */
    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID: '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    /**
     *
     * @param projectId
     * @return
     */
    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if( project == null) {
            throw new ProjectIdException("Project ID: '" + projectId + "' does not exists");
        }

        return project;
    }

    /**
     *
     * @return
     */
    public Iterable<Project> findAllProjects() { return projectRepository.findAll(); }

    /**
     *
     * @param projectId
     */
    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if( project == null) {
            throw new ProjectIdException("Project ID: '" + projectId + "' does not exists, this cannot delete it!");
        }

        projectRepository.delete(project);
    }
}