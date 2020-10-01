package io.mycompany.ppmtool.repositories;

import io.mycompany.ppmtool.domain.Project;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    /**
     *
     * @param project
     * @return
     */
    Project findByProjectIdentifier(String project);

    /**
     *
     * @param username
     * @return
     */
    Iterable<Project> findAllByProjectLeader(String username);
}