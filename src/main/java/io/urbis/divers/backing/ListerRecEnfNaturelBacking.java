/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.divers.api.ActeRecEnfNaturelService;
import io.urbis.divers.dto.ActeDiversDto;
import io.urbis.divers.dto.ActeRecEnfantNaturelDto;
import io.urbis.divers.dto.Operation;
import io.urbis.divers.dto.StatutActeDivers;

import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.StatutRegistre;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

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
    ActeRecEnfNaturelService acteRecEnfNaturelService;
    
    @Inject
    LazyRecEnfNaturelDataModel lazyRecEnfNaturelDataModel;
    
    private String registreID;
    private RegistreDto registreDto;
    private String operationParam;
    private Operation operation;
    private String acteID;
    
    private String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyRecEnfNaturelDataModel.setRegistreID(registreID);
    }
    
    private ActeDiversDto selectedActe;
    
    @PostConstruct
    public void init(){
        LOG.log(Level.INFO,"--- ON INIT ListerRecEnfNaturelBacking");
        
    }
       
    public void openNewActeExistant(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(98, 98, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/editer-reconnaissance-enfant-naturel", options, params);
    }
    
    public void openNewDeclaration(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.DECLARATION.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(98, 98, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/editer-reconnaissance-enfant-naturel", options, params);
    }
    
    /*
    public boolean disableButtonsOpenNew(){
        
        LOG.log(Level.INFO, "--- REGISTRE DTO STATUT: {0}",registreDto.getStatut());
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        
        return true;
        
    }
*/
    
    public StreamedContent download(){
        /*
       File file = etatService.downloadActeNaissance(selectedActeID);
       LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
       LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
       LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
       
       StreamedContent content = null;
        try {
            InputStream input = new FileInputStream(file);
            content = DefaultStreamedContent.builder() 
                .name("acte_naissance.pdf")
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListerRecEnfNaturelBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
       */
        return null;
    }
    
    public void openModifierActeView(ActeRecEnfantNaturelDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/editer-reconnaissance-enfant-naturel", 
                getDialogOptions(100,100,false), params);
    }
    
     public void openValiderActeView(ActeRecEnfantNaturelDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.VALIDATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/divers/editer-reconnaissance-enfant-naturel", 
                getDialogOptions(100,100,true), params);
    }
     
    public boolean disableMenuValiderActe(ActeRecEnfantNaturelDto dto){
       return !dto.getStatut().equals(StatutActeDivers.PROJET.name()); 
    }
    
    public void onActeValidated(SelectEvent event){
        if(event.getObject() != null){
            WebApplicationException ex = (WebApplicationException)event.getObject();
            addGlobalMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        addGlobalMessage("L'acte de naissance a ??t?? valid?? avec succ??s", FacesMessage.SEVERITY_INFO);
    }
    
    public void supprimer(@NotBlank String id){
       boolean result = acteRecEnfNaturelService.delete(id);
       if(!result){
           addGlobalMessage("L'acte ne peut ??tre supprim??!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
    
    public String statutSeverity(String statut){
        
        if(statut.equalsIgnoreCase("PROJET")){
            return "warning";
        }
         
        if(statut.equalsIgnoreCase("VALIDE")){
            return "success";
        }
        
        if(statut.equalsIgnoreCase("ANNULE")){
            return "danger";
        }
        
        return "";
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

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }
    
    
    
}
