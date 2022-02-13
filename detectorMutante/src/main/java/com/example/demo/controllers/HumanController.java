package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.models.HumanModel;
import com.example.demo.modelsDTO.HumanDTO;
import com.example.demo.modelsDTO.StatDTO;
import com.example.demo.services.HumanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Clase que sirve para exponer los servicios para los humanos de la aplicación
 * @author Angie Viviana Galindo Suarez <ingangievgs@gmail.com>
 *
 */
@RestController

@RequestMapping("/human")
public class HumanController {
	
    @Autowired
    HumanService humanService;

    /**
     * Metodo que expone el servicio para obtener
     *  todos los Humanos registrados 
     * @return la lista de humanos registrados
     */
    @GetMapping()
    public ArrayList<HumanModel> getHumans(){
        return humanService.obtenerHumans();
    }

    /**
     * Metodo que expone el servicio que registra 
     * los humanos en la aplicacion 
     * @return el humano resgistrado 
     */
    @PostMapping()
    public HumanModel saveHuman(@RequestBody HumanDTO human){
        return this.humanService.saveHuman(human);
    }

    /**
     * Metodo que expone el servicio para consultar el humano por id
     * @param id -> el id del humano 
     * @return
     */
    @GetMapping( path = "/{id}")
    public Optional<HumanModel> getHumanById(@PathVariable("id") Long id) {
        return this.humanService.getById(id);
    }
    
    /**
     * Metodo que expone el servicio para consultar los datos estadisticos 
     * de los humanos registrados (Numero Humanos, Numero Mutantes, proporción)
     * si no hay humanos registrados todos los datos son cero
     * @return StatDTO que contiene Numero Humanos, Numero Mutantes, proporción
     */
    @GetMapping( path = "/stats")
    public  StatDTO getStats() {
        return this.humanService.getStats();
    }
    
    /**
     * Metodo que expone el servicio para verificar si el adn de un humano
     * tiene las condiciones de mutante. Registra la consulta si la serie
     * de adn no esta registrada si ya se encuentra registrada consulta si es
     * o no mutante
     * @return true si es mutante y false si no lo es
     */
    @PostMapping(value = "/mutant/")
    public Boolean isMutant(@RequestBody HumanDTO human,  HttpServletResponse response){ 

    	if (this.humanService.isMutant(human)) {
            return true;
		}else {
		    response.setStatus(403);
			return false;

		}
    }

    /**
     * Metodo que expone el servicio para eliminar un humano guardado
     * @param id-> id del humano que se desea eliminar
     * @return true si se encontro y elimino el humano o false si no
     */
    @DeleteMapping( path = "/{id}")
    public String deleteHumanById(@PathVariable("id") Long id){
        boolean ok = this.humanService.deleteHuman(id);
        if (ok){
            return "Se eliminó el human con id " + id;
        }else{
            return "No pudo eliminar el human con id" + id;
        }
    }

}