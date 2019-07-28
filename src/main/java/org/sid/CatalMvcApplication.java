package org.sid;

import org.sid.dao.ProduitRepository;
import org.sid.entities.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalMvcApplication implements CommandLineRunner {

	@Autowired
	private ProduitRepository produitRepository;
	public static void main(String[] args) {
		SpringApplication.run(CatalMvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		produitRepository.save( new Produit(null,"pc",600,12));
		produitRepository.save(new Produit(null,"hp",700,22));
		produitRepository.findAll().forEach(p->{System.out.println(p.getDesignation());
	});

}}
