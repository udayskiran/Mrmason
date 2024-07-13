package com.application.mrmason.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.mrmason.entity.CustomerAssets;
import com.application.mrmason.entity.CustomerRegistration;
import com.application.mrmason.entity.Membership;
import com.application.mrmason.entity.MembershipDetails;
import com.application.mrmason.repository.CustomerAssetsRepo;
import com.application.mrmason.repository.CustomerRegistrationRepo;
import com.application.mrmason.repository.MembershipDetailsRepo;
import com.application.mrmason.repository.MembershipRepo;
import com.application.mrmason.service.MembershipDetailsService;


@Service
public class MembershipServiceImpl implements MembershipDetailsService {
	
	@Autowired
	public MembershipDetailsRepo memDetailsRepo;
	@Autowired
	public CustomerAssetsRepo assetRepo;
	@Autowired
	public CustomerRegistrationRepo regRepo;
	@Autowired
	public MembershipRepo memberRepo;
	
	@Override
	public String addOrUpdateMembership(MembershipDetails member) {
		Optional<CustomerAssets> assetDetails = assetRepo.findByUserIdAndAssetId(member.getUserId(),
				member.getAssetId());

		CustomerRegistration customer = regRepo.findByUserid(member.getUserId());

		Membership user = new Membership();
		if (assetDetails.isPresent()) {
			MembershipDetails existingMembership = memDetailsRepo.findByUserIdAndAssetId(member.getUserId(),
					member.getAssetId());
			Membership userMember = memberRepo.findByUserId(member.getUserId());
			if (userMember!=null) {
				if(existingMembership!=null) {
					return "present";
				}
				if (existingMembership == null) {
					
					long sum = Long.parseLong(userMember.getAmount()) + Long.parseLong(member.getAmount());
					String amount = String.valueOf(sum);

					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate currentdate = LocalDate.parse(assetDetails.get().getMembershipExpDb(), formatter);
					LocalDate newExpDate = currentdate.plusYears(1);
					String expDate = newExpDate.format(formatter);

					member.setExpDate(expDate);
					member.setPurchaseId(userMember.getPurchaseId());
					memDetailsRepo.save(member);

					userMember.setAmount(amount);
					memberRepo.save(userMember);
					
					
					assetDetails.get().setMembershipExpDb(expDate);
					assetDetails.get().setPlanId(member.getPlanId());
					assetRepo.save(assetDetails.get());
					
					return "renewel";

				}
				
			} if(userMember==null) {

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				LocalDate currentdate = LocalDate.parse(assetDetails.get().getMembershipExpDb(), formatter);
				LocalDate newExpDate = currentdate.plusYears(1);
				String expDate = newExpDate.format(formatter);
				member.setExpDate(expDate);

				MembershipDetails memberdb = memDetailsRepo.save(member);
				String purchaseId = memberdb.getPurchaseId();

				user.setPurchaseId(purchaseId);
				user.setUserId(member.getUserId());
				user.setUserEmail(customer.getUserEmail());
				user.setAmount(member.getAmount());

				memberRepo.save(user);
				
				assetDetails.get().setMembershipExpDb(expDate);
				assetDetails.get().setPlanId(member.getPlanId());
				assetRepo.save(assetDetails.get());
				
				return "added";

			}

		}
		return null;
	}

	@Override
	public MembershipDetails getMembership(MembershipDetails member) {
		 MembershipDetails memDetails =memDetailsRepo.findByUserIdAndAssetId(member.getUserId(), member.getAssetId());
		return memDetails;
	}
}
