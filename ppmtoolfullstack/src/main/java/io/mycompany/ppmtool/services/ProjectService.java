package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.User;
import io.mycompany.ppmtool.domain.Backlog;
import io.mycompany.ppmtool.domain.Project;

import io.mycompany.ppmtool.exceptions.ProjectIdException;
import io.mycompany.ppmtool.exceptions.ProjectNotFoundException;
import io.mycompany.ppmtool.repositories.BacklogRepository;
import io.mycompany.ppmtool.repositories.ProjectRepository;
import io.mycompany.ppmtool.repositories.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     *
     * @param project
     * @param username
     * @return
     */
    public Project saveOrUpdateProject(Project project, String username) {

        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if (existingProject != null && !existingProject.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) {
                throw new ProjectNotFoundException("Project with ID: '" +
                                                    project.getProjectIdentifier() +
                                                    "' cannot be updated because it doesn't exist!");
            }
        }

        try {
            User user = userRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());
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
            throw new ProjectIdException("Project ID: '" +
                                         project.getProjectIdentifier().toUpperCase() +
                                         "' already exists");
        }
    }

    /**
     *
     * @param projectId
     * @param username
     * @return
     */
    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Project ID: '" + projectId + "' does not exists");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account!");
        }

        return project;
    }

    /**
     *
     * @return
     */
    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    /**
     *
     * @param projectId
     * @param username
     */
    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }
}