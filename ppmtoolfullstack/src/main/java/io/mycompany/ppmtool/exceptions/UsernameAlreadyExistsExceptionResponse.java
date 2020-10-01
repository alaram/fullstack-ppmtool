package io.mycompany.ppmtool.exceptions;

public class UsernameAlreadyExistsExceptionResponse {

    private String username;

    /**
     *
     * @param username
     */
    public UsernameAlreadyExistsExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userNameUnique) {
        this.username = username;
    }
}
