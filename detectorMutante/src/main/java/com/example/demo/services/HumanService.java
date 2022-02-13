package com.example.demo.services;

import java.util.ArrayList;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.models.HumanModel;
import com.example.demo.modelsDTO.HumanDTO;
import com.example.demo.modelsDTO.StatDTO;
import com.example.demo.repositories.HumanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* Clase que establece las operaciones a nivel de capa de logica de negocio para
* la entidad Human
* 
* * @author Angie Viviana Galindo Suarez <ingangievgs@gmail.com> 
*
*/

@Service
public class HumanService {
    @Autowired
    HumanRepository humanRepository;
    
    //Expresion regular que permite validar que las letras de la secuencia
    //de ADN contiene solo las letras A, T, G, o C
	private final String BASE_ADN_REPRESENTACION = "(A|T|G|C)+" ;

	//Permite realizar los comentarios en el log del servidor
	private static final Logger LOGGER = LoggerFactory.getLogger(HumanService.class);

	/**
	 * Metodo que consulta en la base de datos todos los Humanos registrados
	 * @return lista de Humanos registrados 
	 */
    public ArrayList<HumanModel> obtenerHumans(){
        return (ArrayList<HumanModel>) humanRepository.findAll();
    }

    /**
     * Metodo que permite guardar un humano
     * @param human
     * @return
     */
    public HumanModel saveHuman(HumanDTO human){
    	LOGGER.info(
				new StringBuilder("{} Negocio-INI: ").append(Thread.currentThread().getStackTrace()[1].getMethodName())
						.append(" -> ").toString(),
				new StringBuilder(this.getClass().getSimpleName()).append(" - ").append("Save Human"));

		HumanModel humanModel = new HumanModel();
		String adn = convertArrayToString(human.getADN());
		if(humanRepository.findByADNSequence(adn) != null) {
			humanModel.setSecuenciaADN(adn);
			humanModel.setMutant(isMutant(human.getADN()));
			humanModel = humanRepository.save(humanModel);
			if (humanModel != null) {
			LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
					.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
					this.getClass().getSimpleName());
			return humanModel;
			}else {
				LOGGER.error("{} Error: [{}] ", "Save Human", "Human no se pudo guardar");
				return  null;
			}

		}else {
			LOGGER.error("{} Error: [{}] ", "Save Human", "El Humano ya se encuentra registrado");
			return  null;
		}
	}
    
    /**
     * Metodo que busca el humano por id 
     * @param id
     * @return el humano encontrado con dicho id
     */
    public Optional<HumanModel> getById(Long id){
    	LOGGER.info(
				new StringBuilder("{} Negocio-INI: ").append(Thread.currentThread().getStackTrace()[1].getMethodName())
						.append(" -> ").toString(),
				new StringBuilder(this.getClass().getSimpleName()).append(" - ").append("Find Human by id"));
    	Optional<HumanModel> optional = humanRepository.findById(id);
    	if ( optional != null && !optional.isEmpty()) {
    		LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
					.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
					this.getClass().getSimpleName());
            return optional;
		}else {
			LOGGER.error("{} Error: [{}] ", "Find Human by id", "No Se encontro ningun humano con ese id");
			LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
					.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
					this.getClass().getSimpleName());
			return optional;
		}
    }
    
    
    /**
     * metodo que busca si algun humano registrado tiene la secuencia de ADN registrada
     * @param ADNSequence
     * @return el humano encontrado con la secuencia ingresada
     */
    public HumanModel getBySequenceADN(String[] ADNSequence){
    	String serialADNFind = convertArrayToString(ADNSequence);
    	if (!serialADNFind.equals("")) {
            return humanRepository.findByADNSequence(serialADNFind);
		}else {
			return null;
		}
     	
    }

    /**
     * Metodo que elimina el humano de la base de datos 
     * @param id
     * @return
     */
    public boolean deleteHuman(Long id) {
    	LOGGER.info(
				new StringBuilder("{} Negocio-INI: ").append(Thread.currentThread().getStackTrace()[1].getMethodName())
						.append(" -> ").toString(),
				new StringBuilder(this.getClass().getSimpleName()).append(" - ").append("Delete Human"));
    	try{
            humanRepository.deleteById(id);
        	LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
					.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
					this.getClass().getSimpleName());
            return true;
        }catch(Exception err){
			LOGGER.error("{} Error: [{}] ", "Delete Human", "Humano no se pudo eliminar");
            return false;
        }
    }

    /**
     * Metodo que valida si el humano es mutante, antes de hacer esta validacion 
     * se verifica que este registrado el adn para no procesar 2 veces el adn para la validacion
     * @param human -> Humano a validar
     * @return true si es mutante , false si no lo es 
     */
	public boolean isMutant(HumanDTO human) {
		LOGGER.info(
				new StringBuilder("{} Negocio-INI: ").append(Thread.currentThread().getStackTrace()[1].getMethodName())
						.append(" -> ").toString(),
				new StringBuilder(this.getClass().getSimpleName()).append(" - ").append("Is Mutant"));
		String adn = convertArrayToString(human.getADN());
		
		HumanModel humanConsult = humanRepository.findByADNSequence(adn);
		
		if (humanConsult == null) {
			HumanModel humanModel = new HumanModel();
			humanModel.setSecuenciaADN(adn);
			Boolean isMutant = isMutant(human.getADN());
			humanModel.setMutant(isMutant);
			humanRepository.save(humanModel);
	        LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
						.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
						this.getClass().getSimpleName());
			return isMutant;
			
		
		} else {
	        	LOGGER.info(new StringBuilder("{} NEGOCIO-FIN: ")
						.append(Thread.currentThread().getStackTrace()[1].getMethodName()).append(" -> ").toString(),
						this.getClass().getSimpleName());
			return humanConsult.isMutant();
		
		}
		
	}

	/**
	 * Metodo que valida si una secuencia de ADN es o no de un mutante
	 * @param dna -> secuencia de ADN
	 * @return true si es mutante , false si no lo es
	 */
	public boolean isMutant(String[] dna) {
		// Se valida si la secuencia de entrada del ADN no es nula
		if (dna != null) {
			// Se convierte en matriz para realizar un mejor recorrido de la secuencia de adn
			String[][] vector = getVectorN_N(dna);
			//variable que cuenta el # de series de 4 letras iguales consecutivas se encuentran
			int serie = 0;
			//valida la no nulidad del vector generado
			if (vector != null) {
				
				//se recorren las filas del vector y las columnas hasta -3 debido 
				//a que se va validando de a 4 posiciones la igualdad
				for (int i = 0; i < vector.length; i++) {
					for (int j = 0; j < vector.length-3; j++) {
						
						//se valida si a partir de la posicion [i][j]
						//las siguientes 3 posiciones horizontalmente son
						//o no iguales si lo son suman 1 al # de series
						if (vector[i][j].equals(vector[i][j+1]) && 
							vector[i][j].equals(vector[i][j+2]) &&
							vector[i][j].equals(vector[i][j+3]) ) {
							serie++;

						}
						//se valida si a partir de la posicion [i][j]
						//las siguientes 3 posiciones verticales son
						//o no iguales si lo son suman 1 al # de series
						if (vector[j][i].equals(vector[j+1][i]) && 
							vector[j][i].equals(vector[j+2][i]) &&
							vector[j][i].equals(vector[j+3][i])) {
							serie++;

						}
						//se valida si a partir de la posicion [i][j]
						//las siguientes 3 posiciones diagonales son
						//o no iguales si lo son suman 1 al # de series
						if (i+3 < vector[j].length) {
							if (vector[j][i].equals(vector[j+1][i+1]) &&
									vector[j][i].equals(vector[j+2][i+2]) &&
									vector[j][i].equals(vector[j+3][i+3]) ) {
									serie++;
							}	
						}
						//valida que la seria sea mayor que 1 debido a que si esto sucede 
						//quiere decir que si es mutante
						if (serie > 1) {
							return true;
						}
					}	
				}
			}else {
				return false;
			}
		}
		return false;
				
	}
	
	/**
	 * metodo que convierte un vector de tamaño n en matriz de tamaño n*n
	 * @param dna -> vector de tamaño n a convertir
	 * @return matriz de tamaño n*n si es posible la conversion , si  no retorna null
	 */
	private String[][] getVectorN_N(String[] dna) {
		//se obtiene el tamaño del vector
		int tamanioDNA = dna.length;
	
		String[][] vector= new String[tamanioDNA][tamanioDNA];
				for (int i = 0; i < tamanioDNA; i++) {
					//se valida que el string en la posicion i del 
					//vector tenga el mismo tamanio del vector para generar la matriz
					if (tamanioDNA == dna[i].length()) {
						//se verifica que el string del vector en la posicion i contenga unicamente
						//los caracteres especificados para la representacion del adn
						if (verificarBaseNitrogenada(dna[i])) {
							vector[i] = dna[i].split("");
						}else {
							return null;
						}
					}else {
						return null;
					}
				}
		
		return vector;
	}
	
	/**
	 * Metodo que verifica que el string contenga unicamente
	 * los caracteres especificados para la representacion del adn
	 * @param string
	 * @return true si contiene los caracteres especificados
	 * 			false si contiene otros caracteres
	 */
	public boolean verificarBaseNitrogenada(String baseDNA) {
		 Pattern pat = Pattern.compile(BASE_ADN_REPRESENTACION);
	     Matcher mat = pat.matcher(baseDNA);                                                                           
	     if (mat.matches()) {
	         return true;
	     } else {
	         return false;
	     }
		
	}
	
	/**
	 * Metodo que convierte un array de string en un string
	 * @param adn -> vector a convertir
	 * @return el string obtenido al recorrer el string 
	 * cada elemento del vector separado por coma
	 */
	public String convertArrayToString(String[] adn){
		String ADNSequence = new String();
		if (adn != null) {
			for (int i = 0; i < adn.length; i++) {
				ADNSequence =ADNSequence + adn[i];
				if (i+1 < adn.length) {
					ADNSequence += ",";
				}
			}
			return ADNSequence;
		}
		return null;
	}
    
	
	public StatDTO getStats(){
		StatDTO statDTO = new StatDTO();
		Long totalPeople = 0L;
		Long totalMutants = 0L;

		ArrayList<HumanModel> mutants = (ArrayList<HumanModel>) humanRepository.findByIsMutant(true);
		ArrayList<HumanModel> humans =(ArrayList<HumanModel>) humanRepository.findAll();
		if (humans != null) {
			totalPeople = (long) humans.size();
		}
		if (mutants != null) {
			totalMutants = (long) mutants.size();
		}
		
		Double ratio = 0.0;
		if (totalPeople != 0L) {
			ratio =  (double) ((totalMutants*100)/totalPeople);
		}
				
		statDTO.setCount_human_dna(totalPeople);
		statDTO.setRatio(ratio);
		statDTO.setCount_mutant_dna(totalMutants);
		return statDTO;
	}
}