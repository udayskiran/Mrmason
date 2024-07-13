package com.application.mrmason.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.mrmason.dto.SpWorkersDto;
import com.application.mrmason.entity.ServicePersonLogin;
import com.application.mrmason.entity.SpWorkers;
import com.application.mrmason.entity.User;
import com.application.mrmason.entity.UserType;
import com.application.mrmason.repository.ServicePersonLoginDAO;
import com.application.mrmason.repository.SpWorkersRepo;
import com.application.mrmason.repository.UserDAO;
import com.application.mrmason.service.SpWorkersService;


@Service
public class SpWorkersServiceImpl implements SpWorkersService {

	@Autowired
	UserDAO userRepo;
	@Autowired
	SpWorkersRepo workerRepo;
	@Autowired
	ServicePersonLoginDAO spRepo;

	@Override
	public String addWorkers(SpWorkers worker) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();
		Optional<User> userData = Optional.ofNullable(userRepo.findByBodSeqNo(worker.getServicePersonId()));
		if (userData.isPresent()) {
			SpWorkers workerDetails= workerRepo.findByWorkPhoneNum(worker.getWorkPhoneNum());
			if ( workerDetails== null) {
				SpWorkers spworker = workerRepo.save(worker);
				User user = new User();
				user.setBodSeqNo(spworker.getWorkerId());
				user.setMobile(worker.getWorkPhoneNum());
				user.setName(worker.getWorkerName());
				user.setVerified("yes");
				user.setPincodeNo(worker.getWorkerLocation());
				user.setEmail("none");
				user.setStatus("active");
				UserType userType = UserType.fromString("Worker");
				user.setUserType(userType);
				user.setServiceCategory(userData.get().getServiceCategory());
				
				String encodedPass = byCrypt.encode("mrmason@123");
				user.setPassword(encodedPass);
				userRepo.save(user);

				ServicePersonLogin spDetails = new ServicePersonLogin();
				spDetails.setMobile(spworker.getWorkPhoneNum());
				spDetails.setEmail("none");
				spDetails.setMobVerify("yes");
				spRepo.save(spDetails);
				return "added";
			}
			return "notUnique";
		}

		return null;
	}

	@Override
	public List<SpWorkersDto> getWorkers(String spId,String workerId,String phno,String location,String workerAvail) {

        List<SpWorkers> workers = workerRepo.findByServicePersonIdOrWorkerIdOrWorkPhoneNumOrWorkerLocationOrWorkerAvail(
                                        spId, workerId, phno, location, workerAvail);

        // Mapping entities to DTOs
        List<SpWorkersDto> dtos = new ArrayList<>();
        for (SpWorkers workerEntity : workers) {
        	SpWorkersDto dto = new SpWorkersDto();
            
            dto.setServicePersonId(workerEntity.getServicePersonId());
            dto.setWorkerId(workerEntity.getWorkerId());
            dto.setWorkPhoneNum(workerEntity.getWorkPhoneNum());
            dto.setWorkerLocation(workerEntity.getWorkerLocation());
            dto.setWorkerName(workerEntity.getWorkerName());
            dto.setWorkerAvail(workerEntity.getWorkerAvail());
            User data=userRepo.findByBodSeqNo(workerEntity.getWorkerId());
			dto.setWorkerStatus(data.getStatus());
            // Map other attributes as needed
            dtos.add(dto);
        }

        return dtos;
    }

	@Override
	public String updateWorkers(SpWorkersDto worker) {
		String spId = worker.getServicePersonId();
		String workerId = worker.getWorkerId();
		String location = worker.getWorkerLocation();
		String workerAvail = worker.getWorkerAvail();
		String workerStatus = worker.getWorkerStatus();
		
		Optional<SpWorkers> user = Optional.of(workerRepo.findByWorkerIdAndServicePersonId(workerId, spId));
		if (user.isPresent()) {
			user.get().setWorkerLocation(location);
			user.get().setWorkerAvail(workerAvail);
			User data=userRepo.findByBodSeqNo(workerId);
			data.setStatus(workerStatus);
			userRepo.save(data);
			workerRepo.save(user.get());
			return "updated";
		}
		return null;
	}

	@Override
	public SpWorkersDto getDetails(String phno) {
		SpWorkers workerDetails= workerRepo.findByWorkPhoneNum(phno);
		SpWorkersDto spWorker=new SpWorkersDto();
		spWorker.setServicePersonId(workerDetails.getServicePersonId());
		spWorker.setWorkerId(workerDetails.getWorkerId());
		spWorker.setWorkerAvail(workerDetails.getWorkerAvail());
		spWorker.setWorkerName(workerDetails.getWorkerName());
		spWorker.setWorkerLocation(workerDetails.getWorkerLocation());
		spWorker.setWorkPhoneNum(workerDetails.getWorkPhoneNum());
		User data=userRepo.findByEmailOrMobile(phno, phno);
		spWorker.setWorkerStatus(data.getStatus());
		return spWorker;
	}

}