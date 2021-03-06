/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.naissance.api.ActeNaissanceService;
import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.dto.Operation;
import io.urbis.naissance.dto.StatutActeNaissance;

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
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import io.urbis.naissance.api.ActeNaissanceEtatService;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyActeNaissanceDataModel lazyActeNaissanceDataModel;
     
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    @Inject 
    @RestClient
    ActeNaissanceEtatService acteNaissanceEtatService;
    
    @Inject
    @ConfigProperty(name = "URBIS_TENANT", defaultValue = "standard")
    String tenant;
    
    
    private String registreID;
    private RegistreDto registreDto;
    
    String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"----- URBIS TENANT: {0}",tenant);
        LOG.log(Level.INFO,"----- REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyActeNaissanceDataModel.setRegistreID(registreID);
    }
    
    private ActeNaissanceDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public StreamedContent download(){
       File file = acteNaissanceEtatService.downloadActeNaissance(tenant, selectedActeID);
       LOG.log(Level.INFO, "TENANT: {0}", tenant);
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
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
    }
    
    public void onActeValidated(SelectEvent event){
        if(event.getObject() != null){
            WebApplicationException ex = (WebApplicationException)event.getObject();
            addGlobalMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        addGlobalMessage("L'acte de naissance a ??t?? valid?? avec succ??s", FacesMessage.SEVERITY_INFO);
    }
    
    public void openModifierActeView(ActeNaissanceDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,false), params);
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
    
    public void openValiderActeView(ActeNaissanceDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var ids = List.of(registreID);
        var operations = List.of(Operation.VALIDATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("reg-id", ids,"acte-id",acteIds,"operation",operations);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/editer", getDialogOptions(98,98,true), params);
    }
    
    public void supprimer(@NotNull String id){
       boolean result = acteNaissanceService.delete(id);
       if(!result){
           addGlobalMessage("L'acte ne peut ??tre supprim??!", FacesMessage.SEVERITY_ERROR);
       }
    }
    
    public boolean disableMenuValiderActe(ActeNaissanceDto dto){
       return !dto.getStatut().equals(StatutActeNaissance.PROJET.name());
    }
    
    public void openNewActeExistant(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public void openNewDeclaration(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.DECLARATION_JUGEMENT.name());
        Map<String, List<String>> params = Map.of("reg-id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public boolean disableButtonsOpenNew(){
        LOG.log(Level.INFO, "REGISTRE DTO STATUT: {0}",registreDto.getStatut());
        if(registreDto != null){
            return !registreDto.getStatut().equals(StatutRegistre.VALIDE.name());
          
        }
        return true;
    }

    public LazyActeNaissanceDataModel getLazyActeNaissanceDataModel() {
        return lazyActeNaissanceDataModel;
    }

    public void setLazyActeNaissanceDataModel(LazyActeNaissanceDataModel lazyActeNaissanceDataModel) {
        this.lazyActeNaissanceDataModel = lazyActeNaissanceDataModel;
    }

   

    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }

    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }
    
    
}
