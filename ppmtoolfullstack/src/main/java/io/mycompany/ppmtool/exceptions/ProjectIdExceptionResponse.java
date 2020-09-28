package io.mycompany.ppmtool.exceptions;

public class ProjectIdExceptionResponse {

    private String projectIdentifier;

    /**
     *
     * @param projectIdentifier
     */
    public ProjectIdExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    /**
     *
     * @return
     */
    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    /**
     *
     * @param projectIdentifier
     */
    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}