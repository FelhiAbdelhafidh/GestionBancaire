package com.felhi;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.felhi.dao.ClientRepository;
import com.felhi.dao.CompteRepository;
import com.felhi.dao.OperationRepository;
import com.felhi.entities.Client;
import com.felhi.entities.Compte;
import com.felhi.entities.CompteCourant;
import com.felhi.entities.CompteEpargne;
import com.felhi.entities.Operation;
import com.felhi.entities.Retrait;
import com.felhi.entities.Versement;
import com.felhi.metier.IBanqueMetier;

import groovyjarjarcommonscli.CommandLineParser;

 /*
  @SpringBootApplication
public class MaBanqueApplication {

	public static void main(String[] args) {
	ApplicationContext ctx=	SpringApplication.run(MaBanqueApplication.class, args);
	ClientRepository clientRepository=ctx.getBean(ClientRepository.class);
	clientRepository.save(new Client("felhi", "felhiabdelhafidh@gmail.com"));
	}
}*/
  
//methode2
@SpringBootApplication
public class MaBanqueApplication implements CommandLineRunner{
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	IBanqueMetier banqueMetier;
	public static void main(String[] args) {
		SpringApplication.run(MaBanqueApplication.class, args);
	
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		Client c1=clientRepository.save(new Client("felhi", "felhiabdelhafidh@gmail.com"));
		Client c2=clientRepository.save(new Client("Abdelhafidh", "felhiabdelhafidh@gmail.com"));
		Compte cp1=compteRepository.save(new CompteCourant("c1", new Date(), 90000, c1,6000 ));
		Compte cp2=compteRepository.save(new CompteEpargne("c2", new Date(), 90000, c2,5.5 ));
		operationRepository.save(new Versement(new Date(), 9000, cp1));
		operationRepository.save(new Retrait(new Date(), 8000, cp1));
		operationRepository.save(new Retrait(new Date(), 9000, cp1));
		operationRepository.save(new Versement(new Date(), 8000, cp1));
		
		operationRepository.save(new Retrait(new Date(), 2000, cp1));
		operationRepository.save(new Versement(new Date(), 9000, cp2));
		operationRepository.save(new Retrait(new Date(), 5000, cp2));
		operationRepository.save(new Retrait(new Date(), 3000, cp2));
		operationRepository.save(new Versement(new Date(), 8000, cp2));
		banqueMetier.verser("c1", 100);
	}
}

 