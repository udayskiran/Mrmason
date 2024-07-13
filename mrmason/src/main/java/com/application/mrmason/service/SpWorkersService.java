package com.application.mrmason.service;

import java.util.List;

import com.application.mrmason.dto.SpWorkersDto;
import com.application.mrmason.entity.SpWorkers;

public interface SpWorkersService {
	String addWorkers(SpWorkers worker);
    List<SpWorkersDto> getWorkers(String spId,String workerId,String phno,String location,String workerAvail);
    String updateWorkers(SpWorkersDto worker); 
    SpWorkersDto getDetails(String phno);
}
