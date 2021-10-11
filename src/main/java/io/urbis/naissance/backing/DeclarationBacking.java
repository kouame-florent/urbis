/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.naissance.service.ActeNaissanceService;
import io.urbis.naissance.service.ModeDeclarationService;
import io.urbis.naissance.service.TypeNaissanceService;
import io.urbis.registre.backing.BaseBacking;
import io.urbis.registre.backing.ValiderBacking;
import io.urbis.registre.service.CentreService;
import io.urbis.registre.service.LocaliteService;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TribunalService;
import io.urbis.share.dto.ActeNaissanceDto;
import io.urbis.share.dto.CentreDto;
import io.urbis.share.dto.LocaliteDto;
import io.urbis.share.dto.ModeDeclarationDto;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.TypeNaissanceDto;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author florent
 */
@Named(value = "declarationBacking")
@ViewScoped
public class DeclarationBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(DeclarationBacking.class.getName());
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject
    @RestClient
    ModeDeclarationService modeDeclarationService;
    
    @Inject
    @RestClient
    TypeNaissanceService typeNaissanceService;
    
    @Inject
    @RestClient
    ActeNaissanceService acteNaissanceService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private List<ModeDeclarationDto> modesDeclaration;
    private ModeDeclarationDto selectedModeDeclaration;
    
    private List<TypeNaissanceDto> typesNaissance;
    private TypeNaissanceDto selectedTypeNaissance;
    
    private boolean naissanceMultiple;
    
    private int extraitsExtraordinaires;
    private int nombreCopies;
    private int extraitsOrdinaires;
    
    private int numeroActe;
    
    private ActeNaissanceDto acteNaissanceDto;
    
   
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    @PostConstruct
    public void init(){
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        acteNaissanceDto = new ActeNaissanceDto();
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

    public ModeDeclarationDto getSelectedModeDeclaration() {
        return selectedModeDeclaration;
    }

    public void setSelectedModeDeclaration(ModeDeclarationDto selectedModeDeclaration) {
        this.selectedModeDeclaration = selectedModeDeclaration;
    }

    public List<ModeDeclarationDto> getModesDeclaration() {
        return modesDeclaration;
    }

    public int getExtraitsExtraordinaires() {
        return extraitsExtraordinaires;
    }

    public void setExtraitsExtraordinaires(int extraitsExtraordinaires) {
        this.extraitsExtraordinaires = extraitsExtraordinaires;
    }

    public int getNombreCopies() {
        return nombreCopies;
    }

    public void setNombreCopies(int nombreCopies) {
        this.nombreCopies = nombreCopies;
    }

    public int getExtraitsOrdinaires() {
        return extraitsOrdinaires;
    }

    public void setExtraitsOrdinaires(int extraitsOrdinaires) {
        this.extraitsOrdinaires = extraitsOrdinaires;
    }

    public int getNumeroActe() {
        return numeroActe;
    }

    public void setNumeroActe(int numeroActe) {
        this.numeroActe = numeroActe;
    }

    public TypeNaissanceDto getSelectedTypeNaissance() {
        return selectedTypeNaissance;
    }

    public void setSelectedTypeNaissance(TypeNaissanceDto selectedTypeNaissance) {
        this.selectedTypeNaissance = selectedTypeNaissance;
    }

    public List<TypeNaissanceDto> getTypesNaissance() {
        return typesNaissance;
    }

    public boolean isNaissanceMultiple() {
        return naissanceMultiple;
    }

    public void setNaissanceMultiple(boolean naissanceMultiple) {
        this.naissanceMultiple = naissanceMultiple;
    }

    public ActeNaissanceDto getActeNaissanceDto() {
        return acteNaissanceDto;
    }

    public void setActeNaissanceDto(ActeNaissanceDto acteNaissanceDto) {
        this.acteNaissanceDto = acteNaissanceDto;
    }
    
    
}
