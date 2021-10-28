/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.mention.api.DecesService;
import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.dto.DecesDto;
import io.urbis.mention.dto.DissolutionMariageDto;
import io.urbis.mention.dto.LegitimationDto;
import io.urbis.mention.dto.MariageDto;
import io.urbis.mention.dto.ReconnaissanceDto;
import io.urbis.mention.dto.RectificationDto;
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
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.api.NationaliteService;
import io.urbis.registre.api.OfficierService;
import io.urbis.registre.api.RegistreService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author florent
 */
@Named(value = "saisieActeExistantBacking")
@ViewScoped
public class SaisieActeExistantBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(SaisieActeExistantBacking.class.getName());
    
    
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
    
    @Inject
    @RestClient
    DecesService decesService;
    
    @Inject
    LazySaisieActeExistantDataModel lazySaisieActeExistantDataModel;
    
    private ViewMode viewMode;
   
    
    private String registreID;
    private RegistreDto registreDto;
    
    private ActeNaissanceDto selectedActe;
    
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
    
    //private boolean naissanceMultiple;
    private int nombreNaissance;
    private int rang = 1;
    
    
    private boolean pereDecede;
    private boolean mereDecede;
    
    private ActeNaissanceDto acteNaissanceDto;
    
    private AdoptionDto adoptionDto = new AdoptionDto();
    private List<AdoptionDto> adoptionDtos = new ArrayList<>();
    private DecesDto decesDto = new DecesDto();
    private List<DecesDto> decesDtos = new ArrayList<>();
    private DissolutionMariageDto dissolutionMariageDto = new DissolutionMariageDto();
    private LegitimationDto legitimationDto = new LegitimationDto();
    private MariageDto mariageDto = new MariageDto();
    private ReconnaissanceDto reconnaissanceDto = new ReconnaissanceDto();
    private RectificationDto rectificationDto = new RectificationDto();
    
   
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        numeroActe = acteNaissanceService.numeroActe(registreID);
        
        lazySaisieActeExistantDataModel.setRegistreID(registreID);
        
    }
    
    @PostConstruct
    public void init(){
        
        viewMode = ViewMode.NEW;
        
        officiers = officierService.findAll();
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        liensParenteDeclarant = lienDeclarantService.findAll();
        typesPiece = typePieceService.findAll();
        acteNaissanceDto = new ActeNaissanceDto();
        
    }
    
    public void onRowSelect(SelectEvent<ActeNaissanceDto> event){
        LOG.log(Level.INFO,"ENFANT NOM: {0}",selectedActe.getEnfantNom());
        LOG.log(Level.INFO,"SELECTED ACTE OFFICIER ID: {0}",selectedActe.getOfficierEtatCivilID());
        acteNaissanceDto = selectedActe;
        //change view mode to render maj commande button
        viewMode = ViewMode.UPDATE;
    }
    
    public void creer(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(Operation.SAISIE_ACTE_EXISTANT.name());
        acteNaissanceDto.setRegistreID(registreID);
        //acteNaissanceDto.setNumero(numeroActe);
        //acteNaissanceDto.setOfficierEtatCivilID(selectedOfficierId);
        
        String id = acteNaissanceService.create(acteNaissanceDto);
        LOG.log(Level.INFO,"--- ACTE NAISSANCE ID: {0}",id);
        creerMentions(id);
        resetActeDto();
        addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    private void creerMentions(String acteID){
        creerMentionDeces(acteID);
    }
    
    public void modifier(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(Operation.MISE_A_JOUR.name());
        //acteNaissanceDto.setRegistreID(registreID);
        acteNaissanceDto.setNumero(numeroActe);
        //acteNaissanceDto.setOfficierEtatCivilID(selectedOfficierId);
        
        acteNaissanceService.update(acteNaissanceDto.getId(),acteNaissanceDto);
        resetActeDto();
        addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
    }
    
    public void nouvelEngeristrement(){
        resetActeDto();
        viewMode = ViewMode.NEW;
    }
    
    public void resetActeDto(){
        acteNaissanceDto = new ActeNaissanceDto();
    
    }
    
    private void resetForNaissanceMultiple(ActeNaissanceDto old){
        acteNaissanceDto = new ActeNaissanceDto();
        acteNaissanceDto.setPereDateDeces(old.getPereDateDeces());
    }
    
    public void ajouterMentionAdoption(){
        
    }
     
    public void ajouterMentionDeces(){
       LOG.log(Level.INFO,"Ajouter mention décès...");
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       Validator validator = factory.getValidator();
       Set<ConstraintViolation<DecesDto>> violations = validator.validate(decesDto);
       if(violations.isEmpty()){
           decesDtos.add(decesDto);
           LOG.log(Level.INFO,"DECES DTO SIZE {0}",decesDtos.size());
           decesDto = new DecesDto();
       }else{
           violations.stream().forEach(v -> {
               addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
           });
       }
       
    }
    
    private void creerMentionDeces(@NotBlank String acteID){
       decesDtos.stream().forEach(d -> {
            decesDto.setActeNaissanceID(acteID);
            decesService.create(decesDto);
       });
      
    }
    
    private boolean skip;
    
    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
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

    public LazySaisieActeExistantDataModel getLazySaisieActeExistantDataModel() {
        return lazySaisieActeExistantDataModel;
    }

    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
    }

    public ViewMode getViewMode() {
        return viewMode;
    }

    public AdoptionDto getAdoptionDto() {
        return adoptionDto;
    }

    public void setAdoptionDto(AdoptionDto adoptionDto) {
        this.adoptionDto = adoptionDto;
    }

    public DecesDto getDecesDto() {
        return decesDto;
    }

    public void setDecesDto(DecesDto decesDto) {
        this.decesDto = decesDto;
    }

    public DissolutionMariageDto getDissolutionMariageDto() {
        return dissolutionMariageDto;
    }

    public void setDissolutionMariageDto(DissolutionMariageDto dissolutionMariageDto) {
        this.dissolutionMariageDto = dissolutionMariageDto;
    }

    public LegitimationDto getLegitimationDto() {
        return legitimationDto;
    }

    public void setLegitimationDto(LegitimationDto legitimationDto) {
        this.legitimationDto = legitimationDto;
    }

    public MariageDto getMariageDto() {
        return mariageDto;
    }

    public void setMariageDto(MariageDto mariageDto) {
        this.mariageDto = mariageDto;
    }

    public ReconnaissanceDto getReconnaissanceDto() {
        return reconnaissanceDto;
    }

    public void setReconnaissanceDto(ReconnaissanceDto reconnaissanceDto) {
        this.reconnaissanceDto = reconnaissanceDto;
    }

    public RectificationDto getRectificationDto() {
        return rectificationDto;
    }

    public void setRectificationDto(RectificationDto rectificationDto) {
        this.rectificationDto = rectificationDto;
    }

    public List<AdoptionDto> getAdoptionDtos() {
        return adoptionDtos;
    }

    public void setAdoptionDtos(List<AdoptionDto> adoptionDtos) {
        this.adoptionDtos = adoptionDtos;
    }

    public List<DecesDto> getDecesDtos() {
        return decesDtos;
    }

    public void setDecesDtos(List<DecesDto> decesDtos) {
        this.decesDtos = decesDtos;
    }

   
}
