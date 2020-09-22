package com.wp.refcodegen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wp.refcodegen.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByContactNoAndEmailId(String contactNo, String emailId);

	public List<User> findByLinkedTo(String agentId);
	
}
