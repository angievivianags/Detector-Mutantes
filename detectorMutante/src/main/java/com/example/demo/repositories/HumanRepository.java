package com.example.demo.repositories;

import com.example.demo.models.HumanModel;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends CrudRepository<HumanModel, Long> {
	
    public abstract HumanModel findByADNSequence(String ADNSequence);
    
    public abstract ArrayList<HumanModel> findByIsMutant(boolean isMutanta);

}