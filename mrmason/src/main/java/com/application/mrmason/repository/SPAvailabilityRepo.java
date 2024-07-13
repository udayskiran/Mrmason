package com.application.mrmason.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.SPAvailability;

@Repository
public interface SPAvailabilityRepo extends JpaRepository<SPAvailability, Integer> {

	List<SPAvailability> findByBodSeqNo(String bodSeqNo);

}
