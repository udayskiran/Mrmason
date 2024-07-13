package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.mrmason.entity.SpWorkers;
import java.util.List;

@Repository
public interface SpWorkersRepo extends JpaRepository<SpWorkers,String>{
	List<SpWorkers> findByServicePersonIdOrWorkerIdOrWorkPhoneNumOrWorkerLocationOrWorkerAvail(String serviceId, String workId, String workPhoneNum, String workerLocation, String workerAvail);
	SpWorkers findByWorkerIdAndServicePersonId(String workId, String serviceId);
	SpWorkers findByWorkPhoneNum(String workPhoneNum);
}
