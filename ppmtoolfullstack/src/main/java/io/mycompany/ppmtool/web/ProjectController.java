package io.mycompany.ppmtool.web;

import javax.validation.Valid;

import java.security.Principal;

import io.mycompany.ppmtool.domain.Project;
import io.mycompany.ppmtool.services.ProjectService;
import io.mycompany.ppmtool.services.MapValidationErrorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    /**
     *
     * @param project
     * @param bindingResult
     * @param principal
     * @return
     */
    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
                                              BindingResult bindingResult,
                                              Principal principal) {
        ResponseEntity<?> errorMap =  mapValidationErrorService.MapValidationService(bindingResult);
        if (errorMap != null)
            return errorMap;

        Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    /**
     *
     * @param projectId
     * @param principal
     * @return
     */
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectId(@PathVariable String projectId, Principal principal) {
        Project project = projectService.findProjectByIdentifier(projectId.toUpperCase(), principal.getName());
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    /**
     *
     * @param principal
     * @return
     */
    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal) {
        return projectService.findAllProjects(principal.getName());
    }

    /**
     *
     * @param projectId
     * @param principal
     * @return
     */
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectByIdentifier(@PathVariable String projectId, Principal principal) {
        projectService.deleteProjectByIdentifier(projectId.toUpperCase(), principal.getName());
        return new ResponseEntity<>("Project with id '" + projectId + "' was deleted", HttpStatus.OK);
    }
}