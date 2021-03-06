/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;



import io.urbis.naissance.dto.StatutActeNaissance;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.RegistrePatchDto;
import io.urbis.registre.dto.StatutRegistre;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "registreAfficherBacking")
@ViewScoped
public class AfficherBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(AfficherBacking.class.getName());
    
    private String registreID;
    private RegistreDto registreDto;
     
    @Inject 
    @RestClient
    RegistreService registreService;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
    }
    
    public void valider(){
        registreService.patch(registreID,new RegistrePatchDto(StatutRegistre.VALIDE.name(),""));
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    public boolean rendererCmdValider(){
        return registreDto.getStatut()
                .equals(StatutRegistre.PROJET.name());
    }
    
    public String title(){
        return "Information du registre";
    }
    
    

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }

   
    
}
