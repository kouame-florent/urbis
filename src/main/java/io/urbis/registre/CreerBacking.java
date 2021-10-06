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
import io.urbis.share.dto.TypeRegistreDto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import io.urbis.registre.service.TypeRegistreService;
import io.urbis.share.dto.CentreDto;
import io.urbis.share.dto.LocaliteDto;
import io.urbis.share.dto.OfficierEtatCivilDto;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.TribunalDto;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author florent
 */
@Named(value = "creerBacking")
@ViewScoped
public class CreerBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(CreerBacking.class.getName());
    
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
        annee = registreService.annee();
        numeroRegistre = registreService.numero(selectedType.getCode(),annee);
        numeroPremierActe = registreService.numeroPremierActe(selectedType.getCode());
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
                numeroRegistre, 
                currentTribunal.getLibelle(), 
                currentTribunal.getId(), 
                "",
                selectedOfficierId, 
                numeroPremierActe, 
                0, 
                nombreDeFeuillets, 
                "", 
                null, 
                "");
        
        RegistreDto regRes = registreService.create(reg);
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

    
    
}
