package com.dvoss.services;

import com.dvoss.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Dan on 6/27/16.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
    public AnonFile findFirstByOrderByIdAsc();
}
