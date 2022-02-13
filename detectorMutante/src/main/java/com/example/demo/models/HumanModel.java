package com.example.demo.models;

import javax.persistence.*;

/**
 * Clase que Representa la entidad Humano 
 * @author Angie Viviana Galindo Suarez
 *
 */
@Entity
@Table(name = "Human")
public class HumanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String ADNSequence;
    
    private boolean isMutant = false;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the ADNSequence
	 */
	public String getSecuenciaADN() {
		return ADNSequence;
	}

	/**
	 * @param ADNSequence the ADNSequence to set
	 */
	public void setSecuenciaADN(String ADNSequence) {
		this.ADNSequence = ADNSequence;
	}

	/**
	 * @return the isMutant
	 */
	public boolean isMutant() {
		return isMutant;
	}

	/**
	 * @param isMutant the isMutant to set
	 */
	public void setMutant(boolean isMutant) {
		this.isMutant = isMutant;
	}
    
}