/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.ViewMode;
import io.urbis.common.util.BaseBacking;
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
import java.util.HashSet;
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
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import io.urbis.mention.api.MentionAdoptionService;
import io.urbis.mention.api.MentionDecesService;
import io.urbis.mention.api.MentionDissolutionService;
import io.urbis.mention.api.MentionReconnaissanceService;
import io.urbis.mention.api.MentionRectificationService;
import io.urbis.mention.api.MentionMariageService;
import io.urbis.mention.api.MentionLegitimationService;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceEditerBacking")
@ViewScoped
public class EditerBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(EditerBacking.class.getName());
    
    
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
    MentionDecesService mentionDecesService;
    
    @Inject
    @RestClient
    MentionAdoptionService mentionAdoptionService;
    
    @Inject
    @RestClient
    MentionDissolutionService mentionDissolutionService;
    
    @Inject
    @RestClient
    MentionLegitimationService mentionLegitimationService;
    
    @Inject
    @RestClient
    MentionMariageService mentionMariageService;
    
    @Inject
    @RestClient
    MentionReconnaissanceService reconnaissanceService;
    
    @Inject
    @RestClient
    MentionRectificationService rectificationService;
    
    @Inject
    LazySaisieActeExistantDataModel lazySaisieActeExistantDataModel;
    
    private ViewMode viewMode;
   
    
    private String registreID;
    private RegistreDto registreDto;
    
    private String acteNaissanceID;
    
    private String operationParam;
    private Operation operation;
    
    private ActeNaissanceDto selectedActe;
    
       
    private List<ModeDeclarationDto> modesDeclaration;
    //private String selectedModeDeclaration;
    
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
    
   // private int numeroActe;
    
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
    private List<DissolutionMariageDto> dissolutionMariageDtos = new ArrayList<>();
    
    private LegitimationDto legitimationDto = new LegitimationDto();  
    private List<LegitimationDto> legitimationDtos = new ArrayList<>();
    
    private MariageDto mariageDto = new MariageDto();
    private Set<MariageDto> mariageDtos = new HashSet<>();
    private MariageDto selectedMentionMariage;
    
    private ReconnaissanceDto reconnaissanceDto = new ReconnaissanceDto();
    private List<ReconnaissanceDto> reconnaissanceDtos = new ArrayList<>();
    
    private RectificationDto rectificationDto = new RectificationDto();
    private List<RectificationDto> rectificationDtos = new ArrayList<>();
    
   
   
    
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
    
     public void onload(){
        LOG.log(Level.INFO,"LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
      
        operation = Operation.fromString(operationParam);
        lazySaisieActeExistantDataModel.setRegistreID(registreID);
             
        LOG.log(Level.INFO,"--- CURRENT OPERATION : {0}",operation.name());
        if(operation == Operation.DECLARATION_JUGEMENT){
            int numeroActe = acteNaissanceService.numeroActe(registreID);
            acteNaissanceDto.setNumero(numeroActe);
        }
        
        if(operation == Operation.MODIFICATION){
            acteNaissanceDto = acteNaissanceService.findById(acteNaissanceID);
            viewMode = ViewMode.UPDATE;
            selectedActe = acteNaissanceDto;
        }
        
    }
    
    public void onRowSelect(SelectEvent<ActeNaissanceDto> event){
        LOG.log(Level.INFO,"ENFANT NOM: {0}",selectedActe.getEnfantNom());
        LOG.log(Level.INFO,"SELECTED ACTE OFFICIER ID: {0}",selectedActe.getOfficierEtatCivilID());
        acteNaissanceDto = selectedActe;
        LOG.log(Level.INFO,"SELECTED ACTE NUM: {0}",selectedActe.getNumero());
        mariageDtos = mentionMariageService.findByActeNaissance(selectedActe.getId());
        //change view mode to render maj commande button
        viewMode = ViewMode.UPDATE;
    }
    
    public void onMentionMariageRowSelect(SelectEvent<MariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void deleteMentionMariage(MariageDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        mentionMariageService.delete(dto.getId());
        mariageDtos.remove(dto);
    }
   
    
    public void creer(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(operation.name());
        acteNaissanceDto.setRegistreID(registreID);
        
        try{
            String id = acteNaissanceService.create(acteNaissanceDto);
            LOG.log(Level.INFO,"--- ACTE NAISSANCE ID: {0}",id);
            creerMentions(id);
            resetActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
            LOG.log(Level.INFO,"ERROR MESSAGE: {0}",ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
   
    public void modifier(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(Operation.MODIFICATION.name());
          
        acteNaissanceService.update(acteNaissanceDto.getId(),acteNaissanceDto);
        creerMentions(acteNaissanceDto.getId());
        resetActeDto();
        addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        viewMode = ViewMode.NEW;
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
    }
    
    public void nouvelEngeristrement(){
        resetActeDto();
        viewMode = ViewMode.NEW;
    }
    
    private void resetActeDto(){
        acteNaissanceDto = new ActeNaissanceDto();
        if(operation == Operation.DECLARATION_JUGEMENT && viewMode == ViewMode.NEW){
            int numeroActe = acteNaissanceService.numeroActe(registreID);
            acteNaissanceDto.setNumero(numeroActe);
        }
        selectedActe = null;
    
    }
    
    private void resetMentions(){
    
    }
    
    /*
    private void resetForNaissanceMultiple(ActeNaissanceDto old){
        acteNaissanceDto = new ActeNaissanceDto();
        acteNaissanceDto.setPereDateDeces(old.getPereDateDeces());
    }
*/
    
    public void ajouterMentionAdoption(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AdoptionDto>> violations = validator.validate(adoptionDto);
        if(violations.isEmpty()){
            
            adoptionDtos.add(adoptionDto);
            adoptionDto = new AdoptionDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionDissolution(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<DissolutionMariageDto>> violations = validator.validate(dissolutionMariageDto);
        if(violations.isEmpty()){
            
            dissolutionMariageDtos.add(dissolutionMariageDto);
            dissolutionMariageDto = new DissolutionMariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionReconnaissance(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ReconnaissanceDto>> violations = validator.validate(reconnaissanceDto);
        if(violations.isEmpty()){
            
            reconnaissanceDtos.add(reconnaissanceDto);
            reconnaissanceDto = new ReconnaissanceDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionLegitimation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<LegitimationDto>> violations = validator.validate(legitimationDto);
        if(violations.isEmpty()){
            
            legitimationDtos.add(legitimationDto);
            legitimationDto = new LegitimationDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionRectification(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RectificationDto>> violations = validator.validate(rectificationDto);
        if(violations.isEmpty()){
            
            rectificationDtos.add(rectificationDto);
            rectificationDto = new RectificationDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
     
    public void ajouterMentionDeces(){
       LOG.log(Level.INFO,"Ajouter mention décès...");
       ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
       Validator validator = factory.getValidator();
       Set<ConstraintViolation<DecesDto>> violations = validator.validate(decesDto);
       if(violations.isEmpty()){
           LOG.log(Level.INFO,"--MENTION DECES DTO OFFICIER ID {0}",decesDto.getOfficierEtatCivilID());
           //var dcs = new DecesDto(decesDto);
           decesDtos.add(decesDto);
           LOG.log(Level.INFO,"--DECES DTO SIZE {0}",decesDtos.size());
           decesDto = new DecesDto();
       }else{
           violations.stream().forEach(v -> {
               addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
           });
       }
       
    }
    
    public void ajouterMentionMariage(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MariageDto>> violations = validator.validate(mariageDto);
        if(violations.isEmpty()){
            
            mariageDtos.add(mariageDto);
            mariageDto = new MariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
                
            });
        }
    
    }
    
    private void creerMentions(String acteID){
        creerMentionMariage(acteID);
        creerMentionDeces(acteID);
    }
    
    
    private void creerMentionMariage(@NotBlank String acteID){
        mariageDtos.stream().forEach(m -> {
            m.setActeNaissanceID(acteID);
            mentionMariageService.create(m);
       });
    }
    
    private void creerMentionDeces(@NotBlank String acteID){
       decesDtos.stream().forEach(d -> {
            d.setActeNaissanceID(acteID);
            mentionDecesService.create(d);
       });
      
    }
    
    private boolean skip;
    
    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false; //reset in case user goes back
            return "mariage";
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

    public List<DissolutionMariageDto> getDissolutionMariageDtos() {
        return dissolutionMariageDtos;
    }

    public void setDissolutionMariageDtos(List<DissolutionMariageDto> dissolutionMariageDtos) {
        this.dissolutionMariageDtos = dissolutionMariageDtos;
    }

    public List<LegitimationDto> getLegitimationDtos() {
        return legitimationDtos;
    }

    public void setLegitimationDtos(List<LegitimationDto> legitimationDtos) {
        this.legitimationDtos = legitimationDtos;
    }

    public Set<MariageDto> getMariageDtos() {
        return mariageDtos;
    }

    public void setMariageDtos(Set<MariageDto> mariageDtos) {
        this.mariageDtos = mariageDtos;
    }

    
    public List<ReconnaissanceDto> getReconnaissanceDtos() {
        return reconnaissanceDtos;
    }

    public void setReconnaissanceDtos(List<ReconnaissanceDto> reconnaissanceDtos) {
        this.reconnaissanceDtos = reconnaissanceDtos;
    }

    public List<RectificationDto> getRectificationDtos() {
        return rectificationDtos;
    }

    public void setRectificationDtos(List<RectificationDto> rectificationDtos) {
        this.rectificationDtos = rectificationDtos;
    }

    public MariageDto getSelectedMentionMariage() {
        return selectedMentionMariage;
    }

    public void setSelectedMentionMariage(MariageDto selectedMentionMariage) {
        this.selectedMentionMariage = selectedMentionMariage;
    }

    public String getOperationParam() {
        return operationParam;
    }

    public void setOperationParam(String operationParam) {
        this.operationParam = operationParam;
    }

    public String getActeNaissanceID() {
        return acteNaissanceID;
    }

    public void setActeNaissanceID(String acteNaissanceID) {
        this.acteNaissanceID = acteNaissanceID;
    }

    

   
}
