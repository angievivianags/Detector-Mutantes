package com.example.demo.modelsDTO;

/**
 * Clase que representa las estadisticas a 
 * enviar al hacer la consulta de estas 
 * @author Angie Viviana Galindo Suarez
 *
 */
public class StatDTO {

	public Long count_mutant_dna; 
	public Long count_human_dna; 
	public Double ratio;
	/**
	 * @return the count_mutant_dna
	 */
	public Long getCount_mutant_dna() {
		return count_mutant_dna;
	}
	/**
	 * @param count_mutant_dna the count_mutant_dna to set
	 */
	public void setCount_mutant_dna(Long count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}
	/**
	 * @return the count_human_dna
	 */
	public Long getCount_human_dna() {
		return count_human_dna;
	}
	/**
	 * @param count_human_dna the count_human_dna to set
	 */
	public void setCount_human_dna(Long count_human_dna) {
		this.count_human_dna = count_human_dna;
	}
	/**
	 * @return the ratio
	 */
	public Double getRatio() {
		return ratio;
	}
	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	} 

}
