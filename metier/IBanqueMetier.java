package com.felhi.metier;

import org.springframework.data.domain.Page;

import com.felhi.entities.Compte;
import com.felhi.entities.Operation;

public interface IBanqueMetier {
	public Compte ConsulterCompte(String codeCompte);
	public void verser(String codeCompte,double montant);
	public void retirer(String codeCompte,double montant);
	public void virement(String codeCompte1,String codeCompte2,double montant);
	public Page<Operation> listOperation(String codeCpt,int page,int size);
}
