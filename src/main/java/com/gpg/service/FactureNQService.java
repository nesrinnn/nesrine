package com.gpg.service;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpg.dto.FacturNQDto;
import com.gpg.dto.ProductItemDto;
import com.gpg.entities.Facture;
import com.gpg.entities.Facture_Provider;
import com.gpg.entities.ProductItem;
import com.gpg.repository.FactureNQRepository;
import com.gpg.repository.FactureRepository;
import com.gpg.repository.ProductItemRepo;
import com.gpg.repository.ProductRepo;
import com.gpg.repository.ProviderNormalRepo;
import com.sun.xml.txw2.Document;
@Service
public class FactureNQService {

	@Autowired
	FactureNQRepository fact;
	@Autowired
	ProviderNormalRepo provider;
	@Autowired
	ProductRepo pro;
	@Autowired
	ProductItemRepo proo;
	@Autowired
	FactureRepository factureRepository;
	@Autowired
	ProductItemRepo productItemRepo;
	
    public static String DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/projectFile";
	
	public String add_FactureNQ(String factureNQDto, MultipartFile multipartFiles) throws IOException {
		   
		  FacturNQDto factureNQdto = new ObjectMapper().readValue(factureNQDto, FacturNQDto.class);
		  String filename = StringUtils.cleanPath(multipartFiles.getOriginalFilename());
          Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
          copy(multipartFiles.getInputStream(), fileStorage, REPLACE_EXISTING);
          factureNQdto.setPdfName(filename);
		
		Facture_Provider factureNQ= factureNQdto.FactureNQDtoToFactureNQ(factureNQdto, fact,provider,pro,proo);
		factureNQ.getProductItem().stream().forEach(l->{
			l.setFacture(factureNQ);
		});

		fact.save(factureNQ);
		return filename;
		
	}
	public Facture_Provider getFactureById(long factureId) {
		
	return	fact.findById(factureId).orElseThrow(() -> new IllegalArgumentException("id not found"));
	}

	public void updateFacture(Facture_Provider facture) {
//new
		List<ProductItem> productItemNew = facture.getProductItem();
		//
	Facture_Provider facturee=	fact.findById(facture.getId()).orElseThrow(() -> new IllegalArgumentException("id not found"));
	//old
	List<ProductItem> pp= facturee.getProductItem();
	for(ProductItem p:pp) {
	ProductItem productItem=	productItemRepo.findById(p.getId()).orElseThrow(() -> new IllegalArgumentException("id not found"));
	Optional<ProductItem> pi= productItemNew.stream().filter(pr->pr.getId()==(p.getId())).findAny();
	productItem.setQte(pi.get().getQte());
	productItem.setTottva(pi.get().getTottva());
	productItem.setTotht(pi.get().getTotht());
	productItem.setTotttc(pi.get().getTotttc());
	System.out.println(pi.get().getQte());
	productItemRepo.save(productItem);
	System.out.println(p.getQte());
	
	}
	facturee.setDate_echeance(facture.getDate_echeance());
	facturee.setDate_facture(facture.getDate_facture());

	facturee.setTotHt(facture.getTotHt());
	facturee.setTotTtc(facture.getTotTtc());
	facturee.setTotTva(facture.getTotTva());
	facturee.setPayements(facture.getPayements());
	facturee.setActv(true);
	facturee.setVu(true);
	facturee.setSommeResteImpaye(facture.getSommeResteImpaye());
	fact.save(facturee);
	}
	
	
	public byte[] getPdf(String pdfName) throws Exception {

		/*byte[] inFileBytes = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/projectFile/" + pdfName)); 
		byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
     return encoded;*/
		  
		return  Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/projectFile/" + pdfName));
	   

	}
	

}
