package com.example.demo.modelsDTO;

/**
 * Clase que Representa la entidad Humano 
 * que llega por parametro en los servicios
 * @author Angie Viviana Galindo Suarez
 *
 */
public class HumanDTO {

    private Long id;

    private String[] adn;
    	
	
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
	 * @return the aDNsequence
	 */
	public String[] getADN() {
		return adn;
	}

	/**
	 * @param aDNsequence the aDNsequence to set
	 */
	public void setADN(String[] adn) {
		this.adn = adn;
	}
	    
}