package io.mycompany.ppmtool.repositories;

import io.mycompany.ppmtool.domain.Backlog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Long> {

    /**
     *
     * @param projectIdentifier
     * @return
     */
    Backlog findByProjectIdentifier(String projectIdentifier);
}
