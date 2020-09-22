package com.wp.refcodegen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wp.refcodegen.entity.CustomSequenceTracker;

@Repository
public interface CustomSequenceTrackerRepository extends JpaRepository<CustomSequenceTracker, String> {

}
