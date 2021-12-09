/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mariage.backing;

import io.urbis.common.util.BaseBacking;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import io.urbis.registre.dto.RegistreDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import io.urbis.mariage.dto.ActeMariageDto;
import io.urbis.mariage.dto.Operation;
import io.urbis.mariage.dto.StatutActeMariage;
import java.util.List;
import java.util.Map;
import org.primefaces.PrimeFaces;
import io.urbis.registre.api.EtatService;
import io.urbis.registre.api.RegistreService;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageListerBacking")
@ViewScoped
public class ListerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListerBacking.class.getName());
    
    @Inject
    LazyActeMariageDataModele lazyActeMariageDataModele;
     
    @Inject 
    @RestClient
    RegistreService registreService;  
    
    @Inject 
    @RestClient
    EtatService etatService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyActeMariageDataModele.setRegistreID(registreID);
    }
    
    private ActeMariageDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public StreamedContent download(){
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
            Logger.getLogger(ListerBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
    }
    
    public void onActeValidated(SelectEvent event){
        if(event.getObject() != null){
            WebApplicationException ex = (WebApplicationException)event.getObject();
            addGlobalMessage(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        addGlobalMessage("L'acte de naissance a été validé avec succès", FacesMessage.SEVERITY_INFO);
    }
    
    public void openModifierActeView(ActeMariageDto dto){
        var ids = List.of(registreID);
        var operations = List.of(Operation.MODIFICATION.name());
        var acteIds = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("id", ids,"acte-id",acteIds,"operation",operations);
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
    
    public void showValiderActeView(ActeMariageDto dto){
        LOG.log(Level.INFO, "ACTE ID: {0}", dto.getId());
        var values = List.of(dto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("/acte/naissance/valider", getDialogOptions(96,96,true), params);
    }
    
    public boolean disableMenuValiderActe(ActeMariageDto dto){
       return !dto.getStatut().equals(StatutActeMariage.PROJET.name()); 
    }
    
    public void openNewActeExistant(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.SAISIE_ACTE_EXISTANT.name());
        Map<String, List<String>> params = Map.of("id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
    }
    
    public void openNewDeclaration(){
        var ids = List.of(registreID);
        var operations = List.of(Operation.DECLARATION_JUGEMENT.name());
        Map<String, List<String>> params = Map.of("id", ids,"operation",operations);
        Map<String,Object> options = getDialogOptions(100, 100, false);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("editer", options, params);
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

    public LazyActeMariageDataModele getLazyActeMariageDataModele() {
        return lazyActeMariageDataModele;
    }

    public void setLazyActeMariageDataModele(LazyActeMariageDataModele lazyActeMariageDataModele) {
        this.lazyActeMariageDataModele = lazyActeMariageDataModele;
    }

    public ActeMariageDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeMariageDto selectedActe) {
        this.selectedActe = selectedActe;
    }
    
    
}
