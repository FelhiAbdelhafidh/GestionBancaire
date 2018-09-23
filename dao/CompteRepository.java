package com.felhi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.felhi.entities.Compte;

public interface CompteRepository  extends JpaRepository<Compte, String>{

}
