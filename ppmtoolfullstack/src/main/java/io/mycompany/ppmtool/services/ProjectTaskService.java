package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.Backlog;
import io.mycompany.ppmtool.domain.ProjectTask;

import io.mycompany.ppmtool.repositories.BacklogRepository;
import io.mycompany.ppmtool.repositories.ProjectTaskRepository;
import io.mycompany.ppmtool.exceptions.ProjectNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectTaskService {

    //@Autowired
    //private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectService projectService;

    /**
     *
     * @param projectIdentifier
     * @param projectTask
     * @param username
     * @return
     */
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        //PTs to be added to a specific project, project!=null, Backlog exists
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        //Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //Set the BL to PT
        projectTask.setBacklog(backlog);

        //It is required to have a project sequence to be: IDPRO-1, IDPRO-2...
        Integer BacklogSequence = backlog.getPTSequence();

        //Update BL sequence
        BacklogSequence++;

        //Assign the sequence so it doesn't start from 0 every time
        backlog.setPTSequence(BacklogSequence);

        //Add sequence to PT
        projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //Initial Priority when priority is NULL
        if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) {
            projectTask.setPriority(3);
        }

        //Initial Status when status is NULL
        if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
            projectTask.setStatus("TO-DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    /**
     *
     * @param id
     * @param username
     * @return
     */
    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        //Project project = projectRepository.findByProjectIdentifier(id);
        //if (project == null) {
        //    throw new ProjectNotFoundException("Project with ID: '" + id + "' does not exist!");
        //}

        projectService.findProjectByIdentifier(id, username);
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    /**
     *
     * @param pt_id
     * @return
     */
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {

        //Perform search on existing backlog
        //Backlog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
        //if (backlog == null) {
        //    throw new ProjectNotFoundException("Project with ID: '" + backlog_id + "' does not exist!");
        //}
        projectService.findProjectByIdentifier(backlog_id, username);

        //Validate that the task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);

        //Validate that Backlog and ProjectId in the path corresponds to the right project
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task: '" + pt_id + "' not found!");
        }

        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task: '" + pt_id + "' does not exist in project: " + backlog_id);
        }

        return projectTask;
    }

    /**
     *
     * @param updateTask
     * @param backlog_id
     * @param pt_id
     * @param username
     * @return
     */
    public ProjectTask updateByProjectSequence(ProjectTask updateTask,
                                               String backlog_id,
                                               String pt_id,
                                               String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
        projectTask = updateTask;
        return projectTaskRepository.save(projectTask);
    }

    /**
     *
     * @param backlog_id
     * @param pt_id
     * @param username
     */
    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = this.findPTByProjectSequence(backlog_id, pt_id, username);
        projectTaskRepository.delete(projectTask);
    }
}