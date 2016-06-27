package com.dvoss.services;

import com.dvoss.entities.AnonFile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Dan on 6/27/16.
 */
public interface AnonFileRepository extends CrudRepository<AnonFile, Integer> {
    List<AnonFile> findByOrderByIdDesc();
    public AnonFile findFirstByIsPermanentFalseOrderByIdAsc();

}
