package com.application.mrmason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.application.mrmason.entity.AdminMail;

@Repository
public interface AdminMailRepo extends JpaRepository<AdminMail, String> {
	AdminMail findByEmailid(String emailid);
}
