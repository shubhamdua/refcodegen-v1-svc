package com.wp.refcodegen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wp.refcodegen.entity.StateCodes;

@Repository
public interface StateCodesRepository extends JpaRepository<StateCodes, String> {

}
