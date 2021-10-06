/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre;

import io.urbis.registre.service.CentreService;
import io.urbis.registre.service.LocaliteService;
import io.urbis.registre.service.OfficierService;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TribunalService;
import io.urbis.registre.service.TypeRegistreService;
import io.urbis.share.dto.CentreDto;
import io.urbis.share.dto.LocaliteDto;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.RegistrePatchDto;
import io.urbis.share.dto.StatutRegistre;
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
@Named(value = "validerBacking")
@ViewScoped
public class ValiderBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ValiderBacking.class.getName());
    
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
