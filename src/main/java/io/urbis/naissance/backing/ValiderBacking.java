/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.dto.LienDeclarantDto;
import io.urbis.naissance.dto.ModeDeclarationDto;
import io.urbis.naissance.dto.NationaliteDto;
import io.urbis.naissance.dto.OfficierEtatCivilDto;
import io.urbis.naissance.dto.Operation;
import io.urbis.naissance.dto.SexeDto;
import io.urbis.naissance.dto.TypeNaissanceDto;
import io.urbis.naissance.dto.TypePieceDto;
import io.urbis.naissance.api.ActeNaissanceService;
import io.urbis.naissance.api.LienDeclarantService;
import io.urbis.naissance.api.ModeDeclarationService;
import io.urbis.naissance.api.SexeService;
import io.urbis.naissance.api.TypeNaissanceService;
import io.urbis.naissance.api.TypePieceService;
import io.urbis.naissance.dto.StatutActeNaissance;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.api.NationaliteService;
import io.urbis.registre.api.OfficierService;
import io.urbis.registre.api.RegistreService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "validerNaissanceBacking")
@ViewScoped
public class ValiderBacking extends BaseBacking implements Serializable{
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
     
    
    
    private String acteNaissanceID;
    private ActeNaissanceDto acteNaissanceDto;
    
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
    private List<NationaliteDto> nationalites;
     
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
    
   // private ActeNaissanceDto acteNaissanceDto;
    
   
    public void onload(){
        LOG.log(Level.INFO,"ACTE ID: {0}",acteNaissanceID);
        acteNaissanceDto = acteNaissanceService.findById(acteNaissanceID);
        registreDto = registreService.findById(acteNaissanceDto.getRegistreID());
        //LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    @PostConstruct
    public void init(){
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        officiers = officierService.findAll();
        /*
       
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        
        
        liensParenteDeclarant = lienDeclarantService.findAll();
        typesPiece = typePieceService.findAll();
        acteNaissanceDto = new ActeNaissanceDto();
        */
    }
    
    
    public void valider(){
        acteNaissanceDto.setStatut(StatutActeNaissance.VALIDE.name());
        acteNaissanceDto.setOperation(Operation.MISE_A_JOUR.name());
       // try{
            acteNaissanceService.update(acteNaissanceID, acteNaissanceDto);
       // }catch(WebApplicationException ex){
       //     PrimeFaces.current().dialog().closeDynamic(ex);
       // }
        
        //addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    
    
    private void resetForNaissanceMultiple(ActeNaissanceDto old){
        acteNaissanceDto = new ActeNaissanceDto();
        acteNaissanceDto.setPereDateDeces(old.getPereDateDeces());
    }
    
   
   
    public String getActeNaissanceID() {
        return acteNaissanceID;
    }

    public void setActeNaissanceID(String acteNaissanceID) {
        this.acteNaissanceID = acteNaissanceID;
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

    public int getNombreNaissance() {
        return nombreNaissance;
    }

    public void setNombreNaissance(int nombreNaissance) {
        this.nombreNaissance = nombreNaissance;
    }

    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }
    
    
}