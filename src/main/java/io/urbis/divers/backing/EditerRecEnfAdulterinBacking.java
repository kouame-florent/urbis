/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.common.api.SexeService;
import io.urbis.common.util.BaseBacking;
import io.urbis.divers.api.ActeRecEnfAdulterinService;
import io.urbis.divers.api.TypeConsentementService;
import io.urbis.divers.dto.ActeRecEnfantAdulterinDto;
import io.urbis.divers.dto.Operation;
import io.urbis.divers.dto.StatutActeDivers;
import io.urbis.divers.dto.TypeConsentementDto;
import io.urbis.common.dto.SexeDto;
import io.urbis.param.api.OfficierService;
import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
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
@Named(value = "acteRecEnfantAdulterinEditerBacking")
@ViewScoped
public class EditerRecEnfAdulterinBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerRecEnfNaturelBacking.class.getName());
    
    @Inject
    @RestClient
    SexeService sexeService;
    
    @Inject 
    @RestClient
    OfficierService officierService;
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject
    ActeRecEnfAdulterinService acteRecEnfAdulterinService;
    
    @Inject
    TypeConsentementService typeConsentementService ;
    
    @Inject
    LazyRecEnfNaturelDataModel lazyRecEnfNaturelDataModel;
    
    private String registreID;
    private RegistreDto registreDto;
    private String operationParam;
    private Operation operation;
    private String acteID;
    
    private ActeRecEnfantAdulterinDto acteDto;
    
    private List<SexeDto> sexes;
    private List<OfficierEtatCivilDto> officiers;
    
    private List<TypeConsentementDto> typesConsentement;
    
    
    @PostConstruct
    public void init(){
        LOG.log(Level.INFO,"--- INIT EditerRecEnfNaturelBacking ---");
        sexes = sexeService.findAll();
        officiers = officierService.findAll();
        typesConsentement = typeConsentementService.findAll();
    }
    
    public void onload(){
        LOG.log(Level.INFO,"--- ON LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"-- ON LOAD REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"---ON LOAD CURRENT OPERATION : {0}",operation.name());
        
        switch(operation){
            case DECLARATION:
                acteDto = new ActeRecEnfantAdulterinDto();
                //acteDto.setStatut(StatutActeDivers.PROJET.name());
                acteDto.setRegistreID(registreID);
                int numeroActe = acteRecEnfAdulterinService.numeroActe(registreID);
                acteDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteDto = new ActeRecEnfantAdulterinDto();
               // acteDto.setStatut(StatutActeDivers.PROJET.name());
                acteDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteDto = acteRecEnfAdulterinService.findById(acteID);
                break;
            case VALIDATION:
                acteDto = acteRecEnfAdulterinService.findById(acteID);
                break;
        }
        
        acteDto.setOperation(operation.name());
        lazyRecEnfNaturelDataModel.setRegistreID(registreID);
    }
    
    public void creer(){
        try{
            acteRecEnfAdulterinService.create(acteDto);
            resetActeDto();
            addGlobalMessage("D??claration enregistr??e avec succ??s", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    public void modifier(){
        
        LOG.log(Level.INFO,"Updating acte naissance...");
        acteDto.setOperation(Operation.MODIFICATION.name());
          
        try{
            acteRecEnfAdulterinService.update(acteDto.getId(),acteDto);
            acteDto = acteRecEnfAdulterinService.findById(acteDto.getId());
            addGlobalMessage("L'acte a ??t?? modifi?? avec succ??s", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
           LOG.log(Level.SEVERE,ex.getMessage());
           addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
    }
    
    public void valider(){
        try{
            acteDto.setStatut(StatutActeDivers.VALIDE.name());
            acteRecEnfAdulterinService.update(acteDto.getId(), acteDto);
            addGlobalMessage("Acte valid?? avec succ??s", FacesMessage.SEVERITY_INFO);
            PrimeFaces.current().dialog().closeDynamic(null);
        }catch(ValidationException ex){
            LOG.log(Level.SEVERE,ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private void resetActeDto(){
        acteDto = new ActeRecEnfantAdulterinDto();
        if(operation == Operation.DECLARATION){
            int numeroActe = acteRecEnfAdulterinService.numeroActe(registreID);
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
    
    public boolean renderedModifierButton(){
        if(operation != null){
            return operation == Operation.MODIFICATION ;
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

    
    
    public ActeRecEnfantAdulterinDto getActeDto() {
        return acteDto;
    }

    public void setActeDto(ActeRecEnfantAdulterinDto acteDto) {
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

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public void setOfficiers(List<OfficierEtatCivilDto> officiers) {
        this.officiers = officiers;
    }

    public List<TypeConsentementDto> getTypesConsentement() {
        return typesConsentement;
    }
    
    
}
