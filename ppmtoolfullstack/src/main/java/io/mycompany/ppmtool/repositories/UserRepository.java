package io.mycompany.ppmtool.repositories;

import io.mycompany.ppmtool.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     *
     * @param id
     * @return
     */
    User getById(Long id);
}