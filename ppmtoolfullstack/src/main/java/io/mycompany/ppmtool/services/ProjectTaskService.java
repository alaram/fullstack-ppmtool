package io.mycompany.ppmtool.services;

import io.mycompany.ppmtool.domain.Backlog;
import io.mycompany.ppmtool.domain.ProjectTask;
import io.mycompany.ppmtool.repositories.BacklogRepository;
import io.mycompany.ppmtool.repositories.ProjectTaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    /**
     *
     * @param projectIdentifier
     * @param projectTask
     * @return
     */
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        //Handle Exceptions that are present

        //PTs to be added to a specific project, project!=null, Backlog exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

        //Set the BL to PT
        projectTask.setBacklog(backlog);

        //It is required to have a project sequence to be: IDPRO-1, IDPRO-2...
        Integer BacklogSequence = backlog.getPTSequence();

        //Update BL sequence
        BacklogSequence++;

        //Assign the sequence so it doesn't start from 0 every time
        backlog.setPTSequence(BacklogSequence);

        //Add sequence to PT
        projectTask.setProjectSequence(backlog.getPTSequence() + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        //Initial Priority when priority is NULL
        if (projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }

        //Initial Status when status is NULL
        if (projectTask.getStatus().isEmpty() || projectTask.getStatus() == null) {
            projectTask.setStatus("TO-DO");
        }

        return projectTaskRepository.save(projectTask);
    }

    /**
     *
     * @param backlog_id
     * @return
     */
    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}