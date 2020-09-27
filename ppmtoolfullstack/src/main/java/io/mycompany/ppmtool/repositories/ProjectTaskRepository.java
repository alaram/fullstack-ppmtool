package io.mycompany.ppmtool.repositories;

import java.util.List;

import io.mycompany.ppmtool.domain.ProjectTask;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

    /**
     *
     * @return
     */
    List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
}
