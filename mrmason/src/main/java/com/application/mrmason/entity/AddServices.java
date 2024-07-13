package com.application.mrmason.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_services_in_detail")
@Builder
public class AddServices {

	@Id
	@Column(name = "userid_serviceid")
	public String userIdServiceId;
	
	
	@Column(name = "service_id")
	public String serviceId;

	@Column(name = "service_subcategory")
	public String serviceSubCategory;

	@Column(name = "status")
	@Builder.Default
	public String status = "active";

	@Column(name = "userid")
	public String bodSeqNo;

	@Column(name = "updated_by")
	public String updatedBy;

	@Transient
	public LocalDateTime updatedDate;
	
   @Column(name = "updated_date")
   public String updateDateFormat;
    

	@PrePersist
	private void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
		this.updatedDate = now;
		this. updateDateFormat= now.format(formatter);

		String subString = serviceSubCategory.substring(0, Math.min(4, serviceSubCategory.length()));
		this.userIdServiceId = bodSeqNo + "_" + subString;
	}

	
	public void setServiceIdList(List<String> serviceIdList) {
        this.serviceId = String.join(",", serviceIdList);
    }

    
    public List<String> getServiceIdList() {
        return Arrays.asList(this.serviceId.split(","));
    }
}
