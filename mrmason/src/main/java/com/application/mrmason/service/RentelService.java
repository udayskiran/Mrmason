package com.application.mrmason.service;

import java.util.List;

import com.application.mrmason.dto.RentalDto;
import com.application.mrmason.entity.Rentel;

public interface RentelService {
	Rentel addRentalReq(Rentel rent);
	List<Rentel> getRentalReq(String assetId,String userId);
	Rentel updateRentalReq(Rentel rent);
}
