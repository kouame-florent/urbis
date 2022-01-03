/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.divers.dto.ActeRecEnfantNaturelDto;
import io.urbis.common.api.SexeService;
import io.urbis.divers.api.ActeRecEnfNaturelService;
import io.urbis.divers.dto.Operation;
import io.urbis.divers.dto.StatutActeDivers;
import io.urbis.naissance.dto.SexeDto;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.StatutRegistre;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ValidationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "acteDecEnfantNaturelEditerBacking")
@ViewScoped
public class EditerRecEnfNaturelBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerRecEnfNaturelBacking.class.getName());
    
    @Inject
    @RestClient
    SexeService sexeService;
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject
    ActeRecEnfNaturelService acteRecEnfNaturelService;
    
    private String registreID;
    private RegistreDto registreDto;
    private String operationParam;
    private Operation operation;
    
    private String acteID;
    
    private ActeRecEnfantNaturelDto acteDto;
    
    private List<SexeDto> sexes;
    
    
    @PostConstruct
    public void init(){
        LOG.log(Level.INFO,"--- INIT EditerRecEnfNaturelBacking ---");
        sexes = sexeService.findAll();
        
    }
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
    }
    
    public void creer(){
        try{
            acteRecEnfNaturelService.create(acteDto);
            resetActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
            LOG.log(Level.INFO,"ERROR MESSAGE: {0}",ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void valider(){
        try{
            acteDto.setStatut(StatutActeDivers.VALIDE.name());
            acteRecEnfNaturelService.update(acteDto.getId(), acteDto);
            addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
            PrimeFaces.current().dialog().closeDynamic(null);
        }catch(ValidationException ex){
            LOG.log(Level.INFO,"ERROR MESSAGE: {0}",ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void resetActeDto(){
        acteDto = new ActeRecEnfantNaturelDto();
        if(operation == Operation.DECLARATION){
            int numeroActe = acteRecEnfNaturelService.numeroActe(registreID);
            acteDto.setNumero(numeroActe);
        }
       // selectedActe = null;
    
    }
    
    public boolean renderedValiderButton(){
        if(operation != null){
            return operation == Operation.VALIDATION && 
                    acteDto.getStatut().equals(StatutActeDivers.PROJET.name());
        }
        
        return false;
    }
    
    public boolean renderedCreerButton(){
        if(operation != null){
            return operation == Operation.SAISIE_ACTE_EXISTANT || operation == Operation.DECLARATION;
        }
        
        return false;
    }
    
    public void closeView(){
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

    public String getActeID() {
        return acteID;
    }

    public void setActeID(String acteID) {
        this.acteID = acteID;
    }

    
    
    public ActeRecEnfantNaturelDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeRecEnfantNaturelDto acteDto) {
        this.acteDto = acteDto;
    }

    public List<SexeDto> getSexes() {
        return sexes;
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
    
    
    
}
