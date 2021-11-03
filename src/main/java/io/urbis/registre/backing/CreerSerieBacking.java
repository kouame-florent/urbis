/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.registre.api.CentreService;
import io.urbis.registre.api.LocaliteService;
import io.urbis.registre.api.OfficierService;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.api.TribunalService;
import io.urbis.registre.api.TypeRegistreService;
import io.urbis.registre.dto.CentreDto;
import io.urbis.registre.dto.LocaliteDto;
import io.urbis.naissance.dto.OfficierEtatCivilDto;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.TribunalDto;
import io.urbis.registre.dto.TypeRegistre;
import io.urbis.registre.dto.TypeRegistreDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "creerSerieBacking")
@ViewScoped
public class CreerSerieBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(CreerSerieBacking.class.getName());
    
    @Inject 
    @RestClient
    TypeRegistreService typeRegistreService;
     
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
    LocaliteService localiteService;
    
    @Inject 
    @RestClient
    OfficierService officierService;
    
    @Inject
    FacesContext facesContext;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    
    private int premier;
    private int dernier;
    
    private int nombreDeFeuillets;
    private int annee;
    private String libelle;
    private LocaliteDto currentLocalite;
    private CentreDto currentCentre;
    private TribunalDto currentTribunal;
    
    private List<OfficierEtatCivilDto> officiers = new ArrayList<>();
    private String selectedOfficierId;
    
    @PostConstruct
    public void init(){
       typesRegistre = typeRegistreService.findAll();
       officiers = officierService.findAll();
    }
    
    
    public void onTypeRgistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        
        currentLocalite = localiteService.currentLocalite();
        currentCentre = centreService.currentCentre();
        currentTribunal = tribunalService.currentTribunal();
       // anneeCourante = registreService.anneeCourante();
       // numeroRegistre = registreService.numeroRegistre(selectedType.getCode());
       // numeroPremierActe = registreService.numeroPremierActe(selectedType.getCode());
    }
    
    public boolean renderedLibelle(){
        if(selectedType != null){
            return selectedType.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name())
                || selectedType.getCode().equals(TypeRegistre.SPECIAL_DECES.name());
        }
        return false;
    
    }
    
    public boolean requiredLibelle(){
        if(selectedType != null){
            return selectedType.getCode().equals(TypeRegistre.SPECIAL_NAISSANCE.name())
                || selectedType.getCode().equals(TypeRegistre.SPECIAL_DECES.name());
        }
        return false;
    }
    
    
    public void creer(){
        if(dernier < premier){
            FacesMessage message = new FacesMessage("le numero du dernier registre doit etre inférieur au premier");
            facesContext.addMessage("contenttForm:creer",message);
        }
        if(dernier == 0){
            FacesMessage message = new FacesMessage("le numero du premier registre ne peut être '0'");
            facesContext.addMessage("contenttForm:creer",message);
        }
        for (int i = premier; i <= dernier; i++ ){
            LOG.log(Level.INFO, "CREATING REGISTRE ...");
            //int numeroRegistre = registreService.numeroRegistre(selectedType.getCode(),anneeCourante);
            int numeroPremierActe = registreService.numeroPremierActe(selectedType.getCode(),annee);
            LOG.log(Level.INFO, "NUMERO PREMIER ACTE: {0}", numeroPremierActe);
            var reg = new RegistreDto(
                    "", 
                    null, 
                    null,
                    selectedType.getCode(), 
                    selectedType.getLibelle(),
                    currentLocalite.getLibelle(), 
                    currentLocalite.getId(), 
                    currentCentre.getLibelle(), 
                    currentCentre.getId(), 
                    annee, 
                    i, 
                    currentTribunal.getLibelle(), 
                    currentTribunal.getId(), 
                    "",
                    selectedOfficierId, 
                    numeroPremierActe, 
                    numeroPremierActe + nombreDeFeuillets - 1, 
                    nombreDeFeuillets, 
                    0,
                    "", 
                    null, 
                    "");
            
            RegistreDto regRes = registreService.create(reg);
        }
        
       PrimeFaces.current().dialog().closeDynamic("");
    }
    
    private String registreLibelle(TypeRegistreDto type){
        return "";
    }
    
    private void clear(){
        annee = 0;
        premier = 0;
        dernier = 0;
        nombreDeFeuillets = 0;
    }

    public int getPremier() {
        return premier;
    }

    public void setPremier(int premier) {
        this.premier = premier;
    }

    public int getDernier() {
        return dernier;
    }

    public void setDernier(int dernier) {
        this.dernier = dernier;
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

    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
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

    

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
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

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    
   
}
