package com.gpg.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import com.gpg.entities.Facture_Provider;
import com.gpg.entities.Product;
import com.gpg.entities.ProductItem;

import com.gpg.entities.Provider;
import com.gpg.entities.Status;
import com.gpg.repository.FactureNQRepository;
import com.gpg.repository.ProductItemRepo;
import com.gpg.repository.ProductRepo;
import com.gpg.repository.ProviderNormalRepo;

public class FacturNQDto {

	private String num;
	@JsonDeserialize(using = LocalDateDeserializer.class)

	private LocalDate date_facture;
	@JsonDeserialize(using = LocalDateDeserializer.class)

	private LocalDate date_echeance;
	private float totTtc;
	private List<ProductItemDto> productItem = new ArrayList<>();
	private float totTva;
	private float totHt;
	private String email;
	private String pdfName;
	private String rib;
	private String nomBanque;
	private String numCarte;

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getNomBanque() {
		return nomBanque;
	}

	public void setNomBanque(String nomBanque) {
		this.nomBanque = nomBanque;
	}

	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public FacturNQDto() {

	}

	public List<ProductItemDto> getProductItem() {
		return productItem;
	}

	public void setProductItem(List<ProductItemDto> productItem) {
		this.productItem = productItem;
	}

	public Facture_Provider FactureNQDtoToFactureNQ(FacturNQDto factureNQ, FactureNQRepository fq, ProviderNormalRepo pp,
			ProductRepo productRepo, ProductItemRepo productItemRepo) {
		Provider provider = pp.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Id not found"));

		ProductItemDto pp1 = new ProductItemDto();

		List<ProductItem> liste = new ArrayList<>();

		factureNQ.getProductItem().forEach(l -> {

			if (productRepo.findByReference(l.getReference()).isPresent()
					&& productRepo.findByName(l.getName()).isPresent()
					&& productRepo.findByPrice(l.getPrice()).isPresent()) {

				Product prod = productRepo.findByName(l.getName())
						.orElseThrow(() -> new UsernameNotFoundException("Not pp found: "));
				
				ProductItem pp2 = new ProductItem(l.getTottva(),l.getTotttc(),l.getTotht(),l.getQte(), prod);

				liste.add(pp2);

			}
			else if(productRepo.findByReference(l.getReference()).isPresent()
				&& productRepo.findByName(l.getName()).isPresent()
				&& !(productRepo.findByPrice(l.getPrice()).isPresent())) {

				Product prod = productRepo.findByName(l.getName())
						.orElseThrow(() -> new UsernameNotFoundException("Not pp found: "));
				prod.setPrice(l.getPrice());
				ProductItem pp2 = new ProductItem(l.getTottva(),l.getTotttc(),l.getTotht(),l.getQte(), prod);

				liste.add(pp2);
				
			}
			else {

				ProductItem pp2 = pp1.convert(l);

				liste.add(pp2);
				productItemRepo.save(pp2);

			}
		});

		Facture_Provider facture = new Facture_Provider(factureNQ.getNum(), factureNQ.getDate_facture(),
				factureNQ.getDate_echeance(), factureNQ.getTotTtc(),Status.Non_Payee,false,false,
				factureNQ.getTotTva(), factureNQ.getTotHt(), liste, factureNQ.getRib(), factureNQ.getNumCarte(),
				factureNQ.getNomBanque(),factureNQ.getPdfName(),provider);

		return facture;
	}

	public LocalDate getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(LocalDate date_facture) {
		this.date_facture = date_facture;
	}

	public LocalDate getDate_echeance() {
		return date_echeance;
	}

	public void setDate_echeance(LocalDate date_echeance) {
		this.date_echeance = date_echeance;
	}

	public float getTotTtc() {
		return totTtc;
	}

	public void setTotTtc(float totTtc) {
		this.totTtc = totTtc;
	}

	public float getTotTva() {
		return totTva;
	}

	public void setTotTva(float totTva) {
		this.totTva = totTva;
	}

	public float getTotHt() {
		return totHt;
	}

	public void setTotHt(float totHt) {
		this.totHt = totHt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public FacturNQDto(String num, LocalDate date_facture, LocalDate date_echeance, float totTtc,
			List<ProductItemDto> productItem, float totTva, float totHt, String email, String pdf) {

		this.num = num;
		this.date_facture = date_facture;
		this.date_echeance = date_echeance;
		this.totTtc = totTtc;
		this.productItem = productItem;
		this.totTva = totTva;
		this.totHt = totHt;
		this.pdfName = pdf;
		this.email = email;
	}

}
