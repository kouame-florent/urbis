/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.common.util.ViewMode;
import io.urbis.param.api.CentreService;
import io.urbis.param.api.LocaliteService;
import io.urbis.param.api.OfficierService;
import io.urbis.registre.api.RegistreService;
import io.urbis.param.api.TribunalService;
import io.urbis.registre.dto.TypeRegistreDto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.urbis.registre.api.TypeRegistreService;
import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.dto.CentreDto;
import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.dto.TribunalDto;
import io.urbis.registre.dto.RegistreDto;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "registreEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    @Inject 
    @RestClient
    TypeRegistreService typeRegistreService;
    
    @Inject
    @RestClient
    LocaliteService localiteService;
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject 
    @RestClient
    CentreService centreService;
 
    @Inject 
    @RestClient
    TribunalService tribunalService;
    
    @Inject 
    @RestClient
    OfficierService officierService;
    
   // private String registreID;
   // private String modeParam;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    
    private LocaliteDto currentLocalite;
    private CentreDto currentCentre;
    private int annee;
    private int numeroRegistre;
    private int numeroPremierActe;
    private int nombreDeFeuillets;
    private TribunalDto currentTribunal;
    
    private List<OfficierEtatCivilDto> officiers = new ArrayList<>();
    private String selectedOfficierId;
    
    //private ViewMode viewMode; 
    
    private RegistreDto registreDto;
    
    
    @PostConstruct
    public void init(){
       typesRegistre = typeRegistreService.findAll();
       officiers = officierService.findAll();
       registreDto = new RegistreDto();
    }
    
    /*
    public void onload(){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreID);
        LOG.log(Level.INFO, "VIEW MODE: {0}", modeParam);
        
        if(modeParam != null && !modeParam.isEmpty()){
            viewMode = ViewMode.valueOf(modeParam);
            registreDto = registreService.findById(registreID);
        }
        
    }
    */
    
    
    public void onTypeRgistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        try{
            currentLocalite = localiteService.findActive();
        }catch(EntityNotFoundException e){
            addGlobalMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        
        currentCentre = centreService.findActive();
        currentTribunal = tribunalService.findActive();
        
        annee = registreService.anneeCourante();
        numeroRegistre = registreService.numeroRegistre(selectedType.getCode(),annee);
        numeroPremierActe = registreService.numeroPremierActeCourant(selectedType.getCode(),annee);
    }

    public void onOfficierSelect(){
        LOG.log(Level.INFO, "SELECTED OFFICIER: {0}", selectedOfficierId);
    }
    
    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public void setTypesRegistre(List<TypeRegistreDto> typesRegistre) {
        this.typesRegistre = typesRegistre;
    }

    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
    }
    
    public void creer(){
        LOG.log(Level.INFO, "CREATING REGISTRE ...");
     
       registreDto.setTypeRegistre(selectedType.getCode());
       registreDto.setLibelle(selectedType.getLibelle());
       registreDto.setLocalite(currentLocalite.getLibelle());
       registreDto.setLocaliteID(currentLocalite.getId());
       registreDto.setCentre(currentCentre.getLibelle());
       registreDto.setCentreID(currentCentre.getId());
       registreDto.setAnnee(annee);
       registreDto.setNumero(numeroRegistre);
       registreDto.setTribunal(currentTribunal.getLibelle());
       registreDto.setTribunalID(currentTribunal.getId());
       registreDto.setOfficierEtatCivilID(selectedOfficierId);
       registreDto.setNumeroPremierActe(numeroPremierActe);
       registreDto.setNumeroDernierActe(nombreDeFeuillets + numeroPremierActe - 1);
       registreDto.setNombreDeFeuillets(nombreDeFeuillets);
       registreDto.setNombreActe(0);
       registreDto.setStatut("");
       registreDto.setDateAnnulation(null);
       registreDto.setMotifAnnulation("");
       
       
        try{
                registreService.create(registreDto);
        }catch(EntityExistsException | ValidationException  e){
                LOG.log(Level.SEVERE, e.getMessage());
                addGlobalMessage(e.getMessage(), FacesMessage.SEVERITY_ERROR);
       }
        PrimeFaces.current().dialog().closeDynamic("");
    }

    public LocaliteDto getCurrentLocalite() {
        return currentLocalite;
    }

    public void setCurrentLocalite(LocaliteDto currentLocalite) {
        this.currentLocalite = currentLocalite;
    }

    public CentreDto getCurrentCentre() {
        return currentCentre;
    }

    public void setCurrentCentre(CentreDto currentCentre) {
        this.currentCentre = currentCentre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getNumeroRegistre() {
        return numeroRegistre;
    }

    public void setNumeroRegistre(int numeroRegistre) {
        this.numeroRegistre = numeroRegistre;
    }

    public int getNumeroPremierActe() {
        return numeroPremierActe;
    }

    public void setNumeroPremierActe(int numeroPremierActe) {
        this.numeroPremierActe = numeroPremierActe;
    }

    

    public TribunalDto getCurrentTribunal() {
        return currentTribunal;
    }

    public void setCurrentTribunal(TribunalDto currentTribunal) {
        this.currentTribunal = currentTribunal;
    }

    public String getSelectedOfficierId() {
        return selectedOfficierId;
    }

    public void setSelectedOfficierId(String selectedOfficierId) {
        this.selectedOfficierId = selectedOfficierId;
    }


    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public int getNombreDeFeuillets() {
        return nombreDeFeuillets;
    }

    public void setNombreDeFeuillets(int nombreDeFeuillets) {
        this.nombreDeFeuillets = nombreDeFeuillets;
    }

    
    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }

    
    
    
}
