package io.mycompany.ppmtool.web;

import io.mycompany.ppmtool.domain.ProjectTask;

import io.mycompany.ppmtool.services.MapValidationErrorService;
import io.mycompany.ppmtool.services.ProjectTaskService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     *
     * @param projectTask
     * @param bindingResult
     * @param backlog_id
     * @return
     */
    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTBacklog(@Valid @RequestBody ProjectTask projectTask,
                                          BindingResult bindingResult,
                                          @PathVariable String backlog_id) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

        return new ResponseEntity<>(projectTask1, HttpStatus.CREATED);
    }

    /**
     *
     * @param backlog_id
     * @return
     */
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id) {
        return projectTaskService.findBacklogById(backlog_id);
    }

    /**
     *
     * @param backlog_id
     * @param pt_id
     * @return
     */
    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    /**
     *
     * @param projectTask
     * @param bindingResult
     * @param backlog_id
     * @param pt_id
     * @return
     */
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask,
                                               BindingResult bindingResult,
                                               @PathVariable String backlog_id,
                                               @PathVariable String pt_id) {

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null) return errorMap;

        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    /**
     *
     * @param backlog_id
     * @param pt_id
     * @return
     */
    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<String> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id) {
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<>("Project task '" + pt_id + "' was successfully deleted!", HttpStatus.OK);
    }
}