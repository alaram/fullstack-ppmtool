package io.mycompany.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse {

    private String ProjectNotFound;

    /**
     *
     * @param ProjectNotFound
     */
    public ProjectNotFoundExceptionResponse(String ProjectNotFound) {
        this.ProjectNotFound = ProjectNotFound;
    }

    /**
     *
     * @return
     */
    public String getProjectNotFound() {
        return ProjectNotFound;
    }

    /**
     *
     * @param projectNotFound
     */
    public void setProjectNotFound(String projectNotFound) {
        this.ProjectNotFound = projectNotFound;
    }
}