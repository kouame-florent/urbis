/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.naissance.service.ActeNaissanceService;
import io.urbis.naissance.service.LienDeclarantService;
import io.urbis.naissance.service.ModeDeclarationService;
import io.urbis.naissance.service.SexeService;
import io.urbis.naissance.service.TypeNaissanceService;
import io.urbis.naissance.service.TypePieceService;
import io.urbis.common.BaseBacking;
import io.urbis.registre.service.NationaliteService;
import io.urbis.registre.service.OfficierService;
import io.urbis.registre.service.RegistreService;
import io.urbis.share.dto.ActeNaissanceDto;
import io.urbis.share.dto.LienDeclarantDto;
import io.urbis.share.dto.ModeDeclarationDto;
import io.urbis.share.dto.NationaliteDto;
import io.urbis.share.dto.OfficierEtatCivilDto;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.SexeDto;
import io.urbis.share.dto.TypeNaissanceDto;
import io.urbis.share.dto.TypePieceDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
    SexeService sexeService;
    
    @Inject
    @RestClient
    NationaliteService nationaliteService;
    
    @Inject
    @RestClient
    TypePieceService  typePieceService;
    
    @Inject
    @RestClient
    LienDeclarantService lienDeclarantService;
    
    @Inject
    @RestClient
    ActeNaissanceService acteNaissanceService;
    
    @Inject 
    @RestClient
    OfficierService officierService;
    
    
    
    private String registreID;
    private RegistreDto registreDto;
    
    private List<ModeDeclarationDto> modesDeclaration;
    private String selectedModeDeclaration;
    
    private List<TypeNaissanceDto> typesNaissance;
    private String selectedTypeNaissance;
    
    private List<SexeDto> sexes;
    private String selectedSexe;
    
    private List<LienDeclarantDto> liensParenteDeclarant;
    private String selectedDeclarantLienParente;
    
    private List<TypePieceDto> typesPiece;
    /*
    private String selectedPereTypePiece;
    private String selectedMereTypePiece;
    private String selectedDeclarantTypePiece;
    */
    private List<NationaliteDto> nationalites;
    /*
    private String selectedPereNationalite;
    private String selectedMereNationalite;
    private String selectedDeclarantNationalite;
    */
    
    private List<OfficierEtatCivilDto> officiers;
    private String selectedOfficierId;
     
    private int extraitsExtraordinaires;
    private int nombreCopies;
    private int extraitsOrdinaires;
    
    private int numeroActe;
    
    private boolean naissanceMultiple;
    private int nombreNaissance;
    private int rang = 1;
    
    
    private boolean pereDecede;
    private boolean mereDecede;
    
    private ActeNaissanceDto acteNaissanceDto;
    
   
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    @PostConstruct
    public void init(){
        officiers = officierService.findAll();
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        liensParenteDeclarant = lienDeclarantService.findAll();
        typesPiece = typePieceService.findAll();
        acteNaissanceDto = new ActeNaissanceDto();
    }
    
    
    public void creer(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        
        acteNaissanceDto.setRegistreID(registreID);
        acteNaissanceDto.setNumero(numeroActe);
        acteNaissanceDto.setOfficierEtatCivilID(selectedOfficierId);
        
        acteNaissanceService.create(acteNaissanceDto);
        //reset acte dto
        acteNaissanceDto = new ActeNaissanceDto();
        addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        
        numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    private void resetForNaissanceMultiple(ActeNaissanceDto old){
        acteNaissanceDto = new ActeNaissanceDto();
        acteNaissanceDto.setPereDateDeces(old.getPereDateDeces());
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

    
    public List<SexeDto> getSexes() {
        return sexes;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public boolean isPereDecede() {
        return pereDecede;
    }

    public void setPereDecede(boolean pereDecede) {
        this.pereDecede = pereDecede;
    }


    public List<NationaliteDto> getNationalites() {
        return nationalites;
    }

    public boolean isMereDecede() {
        return mereDecede;
    }

    public void setMereDecede(boolean mereDecede) {
        this.mereDecede = mereDecede;
    }

    public List<LienDeclarantDto> getLiensParenteDeclarant() {
        return liensParenteDeclarant;
    }

    
    public List<TypePieceDto> getTypesPiece() {
        return typesPiece;
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

    public String getSelectedModeDeclaration() {
        return selectedModeDeclaration;
    }

    public void setSelectedModeDeclaration(String selectedModeDeclaration) {
        this.selectedModeDeclaration = selectedModeDeclaration;
    }

    public String getSelectedTypeNaissance() {
        return selectedTypeNaissance;
    }

    public void setSelectedTypeNaissance(String selectedTypeNaissance) {
        this.selectedTypeNaissance = selectedTypeNaissance;
    }

    public String getSelectedSexe() {
        return selectedSexe;
    }

    public void setSelectedSexe(String selectedSexe) {
        this.selectedSexe = selectedSexe;
    }

    public String getSelectedDeclarantLienParente() {
        return selectedDeclarantLienParente;
    }

    public void setSelectedDeclarantLienParente(String selectedDeclarantLienParente) {
        this.selectedDeclarantLienParente = selectedDeclarantLienParente;
    }

   
    

    
}
