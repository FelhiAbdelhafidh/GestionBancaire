package com.felhi.metier;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felhi.dao.CompteRepository;
import com.felhi.dao.OperationRepository;
import com.felhi.entities.Compte;
import com.felhi.entities.CompteCourant;
import com.felhi.entities.Operation;
import com.felhi.entities.Retrait;
import com.felhi.entities.Versement;

@Service
@Transactional
public class BanqueMetierImpl implements IBanqueMetier {
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;

	public Compte ConsulterCompte(String codeCompte) {
		Compte compte = compteRepository.findOne(codeCompte);
		if (compte == null)
			throw new RuntimeException("Compte introuvable");
		return compte;
	}

	public void verser(String codeCompte, double montant) {
		Compte cp = ConsulterCompte(codeCompte);
		Versement v = new Versement(new Date(), montant, cp);
		operationRepository.save(v);
		cp.setSolde(cp.getSolde() + montant);
		compteRepository.save(cp);

	}

	public void retirer(String codeCompte, double montant) {

		Compte cp = ConsulterCompte(codeCompte);
		double facilitCaisse = 0;
		if (cp instanceof CompteCourant)
			facilitCaisse = ((CompteCourant) cp).getDecouvert();
		if (cp.getSolde() + facilitCaisse < montant)
			throw new RuntimeException("Solde insuffisant");

		Retrait r = new Retrait(new Date(), montant, cp);
		operationRepository.save(r);
		cp.setSolde(cp.getSolde() - montant);
		compteRepository.save(cp);
	}

	public void virement(String codeCompte1, String codeCompte2, double montant) {
		retirer(codeCompte1, montant);
		verser(codeCompte2, montant);

	}

	public Page<Operation> listOperation(String codeCpt, int page, int size) {
		return operationRepository.listOperation(codeCpt, new PageRequest(page, size));
	}

}
