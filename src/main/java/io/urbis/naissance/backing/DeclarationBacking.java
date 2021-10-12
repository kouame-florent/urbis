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
import io.urbis.registre.backing.BaseBacking;
import io.urbis.registre.service.NationaliteService;
import io.urbis.registre.service.RegistreService;
import io.urbis.share.dto.ActeNaissanceDto;
import io.urbis.share.dto.LienDeclarantDto;
import io.urbis.share.dto.ModeDeclarationDto;
import io.urbis.share.dto.NationaliteDto;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.SexeDto;
import io.urbis.share.dto.TypeNaissanceDto;
import io.urbis.share.dto.TypePieceDto;
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
    SexeService sexeService;
    
    @Inject
    @RestClient
    NationaliteService nationaliteService;
    
    @Inject
    @RestClient
    LienDeclarantService lienDeclarantService;
    
    @Inject
    @RestClient
    ActeNaissanceService acteNaissanceService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    private List<ModeDeclarationDto> modesDeclaration;
    private ModeDeclarationDto selectedModeDeclaration;
    
    private List<TypeNaissanceDto> typesNaissance;
    private TypeNaissanceDto selectedTypeNaissance;
    
    private List<SexeDto> sexes;
    private SexeDto selectedSexe;
    
    private List<LienDeclarantDto> liensParenteDeclarant;
    private LienDeclarantDto selectedLienParenteDeclarant;
    
    private List<TypePieceDto> typesPiece;
    private TypePieceDto selectedPereTypePiece;
    private TypePieceDto selectedMereTypePiece;
    private TypePieceDto selectedDeclarantTypePiece;
    
    private List<NationaliteDto> nationalites;
    private NationaliteDto selectedPereNationalite;
    private NationaliteDto selectedMereNationalite;
    
    private boolean naissanceMultiple;
    
    private int extraitsExtraordinaires;
    private int nombreCopies;
    private int extraitsOrdinaires;
    
    private int numeroActe;
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
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        liensParenteDeclarant = lienDeclarantService.findAll();
        acteNaissanceDto = new ActeNaissanceDto();
    }
    
    
    public void creer(){
        acteNaissanceDto.setPereNationalite(selectedPereNationalite.getCode());
        
    
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

    public SexeDto getSelectedSexe() {
        return selectedSexe;
    }

    public void setSelectedSexe(SexeDto selectedSexe) {
        this.selectedSexe = selectedSexe;
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

    public NationaliteDto getSelectedPereNationalite() {
        return selectedPereNationalite;
    }

    public void setSelectedPereNationalite(NationaliteDto selectedPereNationalite) {
        this.selectedPereNationalite = selectedPereNationalite;
    }

    public NationaliteDto getSelectedMereNationalite() {
        return selectedMereNationalite;
    }

    public void setSelectedMereNationalite(NationaliteDto selectedMereNationalite) {
        this.selectedMereNationalite = selectedMereNationalite;
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

    public LienDeclarantDto getSelectedLienParenteDeclarant() {
        return selectedLienParenteDeclarant;
    }

    public void setSelectedLienParenteDeclarant(LienDeclarantDto selectedLienParenteDeclarant) {
        this.selectedLienParenteDeclarant = selectedLienParenteDeclarant;
    }

    public List<LienDeclarantDto> getLiensParenteDeclarant() {
        return liensParenteDeclarant;
    }

    public TypePieceDto getSelectedPereTypePiece() {
        return selectedPereTypePiece;
    }

    public void setSelectedPereTypePiece(TypePieceDto selectedPereTypePiece) {
        this.selectedPereTypePiece = selectedPereTypePiece;
    }

    public TypePieceDto getSelectedMereTypePiece() {
        return selectedMereTypePiece;
    }

    public void setSelectedMereTypePiece(TypePieceDto selectedMereTypePiece) {
        this.selectedMereTypePiece = selectedMereTypePiece;
    }

    public TypePieceDto getSelectedDeclarantTypePiece() {
        return selectedDeclarantTypePiece;
    }

    public void setSelectedDeclarantTypePiece(TypePieceDto selectedDeclarantTypePiece) {
        this.selectedDeclarantTypePiece = selectedDeclarantTypePiece;
    }

    public List<TypePieceDto> getTypesPiece() {
        return typesPiece;
    }
    
    
}
