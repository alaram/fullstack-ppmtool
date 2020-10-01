package io.mycompany.ppmtool.domain;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.PrePersist;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email.")
    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Please enter your full name.")
    private String fullname;

    @NotBlank(message = "Password field is required.")
    private String password;

    @Transient
    private String confirmPassword;

    private Date createdAt;
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public User() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    /**
     *
     */
    @PrePersist
    protected void onCreate() { this.createdAt = new Date(); }

    /**
     *
     */
    @PreUpdate
    protected void onUpdate() { this.updatedAt = new Date(); }

    /**
     * User Details methods
     */

    /**
     * This method is used for ROLE based security, if not used then
     * return null is OK.
     *
     * The method uses JSON ignore, so the tools such as Postman does not
     * display such information when the object is created
     *
     * @return null
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * Logic in case the account has been expired
     *
     * The method uses JSON ignore, so the tools such as Postman does not
     * display such information when the object is created
     *
     * @return <code>true</code>
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Logic in case the account isn't locked
     *
     * The method uses JSON ignore, so the tools such as Postman does not
     * display such information when the object is created
     *
     * @return <code>true</code>
     */
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Logic in case the credentials aren't expired
     *
     * The method uses JSON ignore, so the tools such as Postman does not
     * display such information when the object is created
     *
     * @return <code>true</code>
     */
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Logic in case the account is enabled
     *
     * The method uses JSON ignore, so the tools such as Postman does not
     * display such information when the object is created
     *
     * @return <code>true</code>
     */
    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}