/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.divers.dto.ActeDiversDto;
import io.urbis.divers.dto.Operation;
import io.urbis.registre.api.EtatService;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.StatutRegistre;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author florent
 */
@Named(value = "acteRecEnfNaturelListerBacking")
@ViewScoped
public class ListerRecEnfNaturelBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(io.urbis.mariage.backing.ListerBacking.class.getName());
    
    @Inject 
    @RestClient
    RegistreService registreService;  
    
    @Inject 
    @RestClient
    EtatService etatService;
    
    @Inject
    LazyRecEnfNaturelDataModel lazyRecEnfNaturelDataModel;
    
    private String registreID;
    private RegistreDto registreDto;
    
    String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyRecEnfNaturelDataModel.setRegistreID(registreID);
    }
    
    private ActeDiversDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
       
    public void openNewActeExistant(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer-reconnaissance-enfant-naturel", options, params);
    }
    
    public void openNewDeclaration(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.DECLARATION.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer-reconnaissance-enfant-naturel", options, params);
    }
    
    public boolean disableButtonsOpenNew(){
        LOG.log(Level.INFO, "REGISTRE DTO STATUT: {0}",registreDto.getStatut());
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        return true;
    }
    
    public void onNewActeReturn(SelectEvent event){
        LOG.log(Level.INFO, "RETURN FROM NEW ACTE...");
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

    public LazyRecEnfNaturelDataModel getLazyRecEnfNaturelDataModel() {
        return lazyRecEnfNaturelDataModel;
    }

    public void setLazyRecEnfNaturelDataModel(LazyRecEnfNaturelDataModel lazyRecEnfNaturelDataModel) {
        this.lazyRecEnfNaturelDataModel = lazyRecEnfNaturelDataModel;
    }
    
    
}
