package com.revature.pms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.pms.model.Allergy;

public interface AllergyRepository extends JpaRepository<Allergy, Integer> {

}
