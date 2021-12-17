/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mariage.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.mariage.api.ActeMariageService;
import io.urbis.mariage.api.SituationMatrimonialeService;
import io.urbis.mariage.dto.ActeMariageDto;
import io.urbis.mariage.dto.Operation;
import io.urbis.mariage.dto.SituationMatrimonialeDto;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject 
    @RestClient
    ActeMariageService acteMariageService;
    
    @Inject 
    @RestClient
    SituationMatrimonialeService situationMatrimonialeService;
   
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteMariageID;
    
    private ActeMariageDto acteDto;
    
    private String operationParam;
    private Operation operation;
    
    private List<SituationMatrimonialeDto> situations;
    
    
    @Inject
    LazyActeMariageDataModel lazyActeMariageDataModel;
    
    
    public void onload(){
        LOG.log(Level.INFO,"LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"--- CURRENT OPERATION : {0}",operation.name());
        
        situations = situationMatrimonialeService.findAll();
        
        switch(operation){
            case DECLARATION:
                acteDto = new ActeMariageDto();
                acteDto.setRegistreID(registreID);
                int numeroActe = acteMariageService.numeroActe(registreID);
                acteDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteDto = new ActeMariageDto();
                acteDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteDto = acteMariageService.findById(acteMariageID);
                break;
        }
        
        acteDto.setOperation(operation.name());
        lazyActeMariageDataModel.setRegistreID(registreID);
        
    }
    
    public String onFlowProcess(FlowEvent event) {
       return event.getNewStep();
       
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

    public String getActeMariageID() {
        return acteMariageID;
    }

    public void setActeMariageID(String acteMariageID) {
        this.acteMariageID = acteMariageID;
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

    public ActeMariageDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeMariageDto acteDto) {
        this.acteDto = acteDto;
    }

    public LazyActeMariageDataModel getLazyActeMariageDataModel() {
        return lazyActeMariageDataModel;
    }

    public List<SituationMatrimonialeDto> getSituations() {
        return situations;
    }
    
    
    
}
