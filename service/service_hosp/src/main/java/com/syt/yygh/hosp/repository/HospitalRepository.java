package com.syt.yygh.hosp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import syt.hospital.model.hosp.Hospital;

/**
 * @Author: foofoo3
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital,String> {
}
