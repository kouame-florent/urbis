/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.ViewMode;
import io.urbis.common.util.BaseBacking;
import io.urbis.mention.dto.MentionAdoptionDto;
import io.urbis.mention.dto.MentionDecesDto;
import io.urbis.mention.dto.MentionDissolutionMariageDto;
import io.urbis.mention.dto.MentionLegitimationDto;
import io.urbis.mention.dto.MentionMariageDto;
import io.urbis.mention.dto.MentionReconnaissanceDto;
import io.urbis.mention.dto.MentionRectificationDto;
import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.naissance.dto.LienDeclarantDto;
import io.urbis.naissance.dto.ModeDeclarationDto;
import io.urbis.naissance.dto.NationaliteDto;
import io.urbis.param.dto.OfficierEtatCivilDto;
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
import io.urbis.param.api.OfficierService;
import io.urbis.registre.api.RegistreService;
import java.io.Serializable;
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
import java.util.Map;
import org.primefaces.PrimeFaces;

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
    MentionReconnaissanceService mentionReconnaissanceService;
    
    @Inject
    @RestClient
    MentionRectificationService mentionRectificationService;
    
    @Inject
    LazyActeNaissanceDataModel lazyActeNaissanceDataModel;
    
    //private ViewMode viewMode;
    
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
    
    private MentionAdoptionDto adoptionDto = new MentionAdoptionDto();
    private MentionAdoptionDto selectedMentionAdoption;
    
    private MentionDecesDto decesDto = new MentionDecesDto();
    private MentionDecesDto selectedMentionDeces;
    
    private MentionDissolutionMariageDto dissolutionMariageDto = new MentionDissolutionMariageDto();
    private MentionDissolutionMariageDto selectedMentionDissolutionMariage;
    
    private MentionLegitimationDto legitimationDto = new MentionLegitimationDto();  
    private MentionLegitimationDto selectedMentionLegitimation;

    
    private MentionMariageDto mariageDto = new MentionMariageDto();
    private MentionMariageDto selectedMentionMariage;
    
    private MentionReconnaissanceDto reconnaissanceDto = new MentionReconnaissanceDto();
    private MentionReconnaissanceDto selectedMentionReconnaissance;
    
    private MentionRectificationDto rectificationDto = new MentionRectificationDto();
    private MentionRectificationDto selectedMentionRectification;
    
   
   
    
    @PostConstruct
    public void init(){
        
        //viewMode = ViewMode.NEW;
        
        officiers = officierService.findAll();
        modesDeclaration = modeDeclarationService.findAll();
        typesNaissance = typeNaissanceService.findAll();
        sexes = sexeService.findAll();
        nationalites = nationaliteService.findAll();
        liensParenteDeclarant = lienDeclarantService.findAll();
        typesPiece = typePieceService.findAll();
        
       //acteNaissanceDto = new ActeNaissanceDto();
        
    }
    
     public void onload(){
        LOG.log(Level.INFO,"LOAD REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        
        operation = Operation.fromString(operationParam);
        LOG.log(Level.INFO,"--- CURRENT OPERATION : {0}",operation.name());
        
        switch(operation){
            case DECLARATION_JUGEMENT:
                acteNaissanceDto = new ActeNaissanceDto();
                acteNaissanceDto.setRegistreID(registreID);
                int numeroActe = acteNaissanceService.numeroActe(registreID);
                acteNaissanceDto.setNumero(numeroActe);
                
                break;
            case SAISIE_ACTE_EXISTANT:
                acteNaissanceDto = new ActeNaissanceDto();
                acteNaissanceDto.setRegistreID(registreID);
                break;
            case MODIFICATION:
                acteNaissanceDto = acteNaissanceService.findById(acteNaissanceID);
                break;
        }
        
        acteNaissanceDto.setOperation(operation.name());
        lazyActeNaissanceDataModel.setRegistreID(registreID);
       
        /*
        if(operation == Operation.DECLARATION_JUGEMENT){
            int numeroActe = acteNaissanceService.numeroActe(registreID);
            acteNaissanceDto.setNumero(numeroActe);
        }
        */
        
        /*
        if(operation == Operation.MODIFICATION){
            acteNaissanceDto = acteNaissanceService.findById(acteNaissanceID);
            viewMode = ViewMode.UPDATE;
            selectedActe = acteNaissanceDto;
        }
        */
    }
    
    public void onRowSelect(SelectEvent<ActeNaissanceDto> event){
        /*
        LOG.log(Level.INFO,"ENFANT NOM: {0}",selectedActe.getEnfantNom());
        LOG.log(Level.INFO,"SELECTED ACTE OFFICIER ID: {0}",selectedActe.getOfficierEtatCivilID());
        acteNaissanceDto = selectedActe;
        LOG.log(Level.INFO,"SELECTED ACTE NUM: {0}",selectedActe.getNumero());
       // mariageDtos = mentionMariageService.findByActeNaissance(selectedActe.getId());
        //change view mode to render maj commande button
        //viewMode = ViewMode.UPDATE;
        */
    }
    
    /*
    public void openNewActe(){
        Map<String,Object> options = getDialogOptions(100, 100, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("infos-acte", options, null);
    }
    */
    
    public void onMentionMariageRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionDecesRowSelect(SelectEvent<MentionMariageDto> event){
        decesDto = selectedMentionDeces;
    }
    
    public void onMentionDissolutionRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionLegitimationRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionAdoptionRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionReconnaissanceRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void onMentionRectificationRowSelect(SelectEvent<MentionMariageDto> event){
        mariageDto = selectedMentionMariage;
    }
    
    public void deleteMentionMariage(MentionMariageDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionMariageDtos().remove(dto);
      
    }
    
    public void deleteMentionAdoptionMariage(MentionAdoptionDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionAdoptionDtos().remove(dto);
      
    }
    
    public void deleteMentionDeces(MentionDecesDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionDecesDtos().remove(dto);
       
    }
    
    public void deleteMentionDissolutionMariage(MentionDissolutionMariageDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionDissolutionMariageDtos().remove(dto);
        
    }
    
    public void deleteMentionLegitimation(MentionLegitimationDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionLegitimationDtos().remove(dto);
        
    }
    
    public void deleteMentionReconnaisance(MentionReconnaissanceDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionReconnaissanceDtos().remove(dto);
        
    }
        
    public void deleteMentionRectification(MentionRectificationDto dto){
        LOG.log(Level.INFO,"Deleting mention mariage...");
        acteNaissanceDto.getMentionRectificationDtos().remove(dto);
        
    }
    
    
    
    public void creer(){
        LOG.log(Level.INFO,"Creating acte naissance...");
         
       // LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
      //  acteNaissanceDto.setOperation(operation.name());
      //  LOG.log(Level.INFO,"--- CREATE REGISTRE ID: {0}",acteNaissanceDto.getRegistreID());
        //acteNaissanceDto.setRegistreID(registreID);
        
        try{
            String id = acteNaissanceService.create(acteNaissanceDto);
            LOG.log(Level.INFO,"--- ACTE NAISSANCE ID: {0}",id);
            //creerMentions(id);
            resetActeDto();
            addGlobalMessage("Déclaration enregistrée avec succès", FacesMessage.SEVERITY_INFO);
        }catch(ValidationException ex){
            LOG.log(Level.INFO,"ERROR MESSAGE: {0}",ex.getMessage());
            addGlobalMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
        }
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
   
    public void modifier(){
        
        LOG.log(Level.INFO,"Updating acte naissance...");
         
        LOG.log(Level.INFO,"ENFANT DATE NAISSANCE: {0}",acteNaissanceDto.getEnfantDateNaissance());
        acteNaissanceDto.setOperation(Operation.MODIFICATION.name());
          
        acteNaissanceService.update(acteNaissanceDto.getId(),acteNaissanceDto);
        //creerMentions(acteNaissanceDto.getId());
        resetActeDto();
        addGlobalMessage("L'acte a été modifié avec succès", FacesMessage.SEVERITY_INFO);
        //viewMode = ViewMode.NEW;
        
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
    }
    
    public void nouvelEngeristrement(){
        resetActeDto();
        //viewMode = ViewMode.NEW;
    }
    
    private void resetActeDto(){
        acteNaissanceDto = new ActeNaissanceDto();
        if(operation == Operation.DECLARATION_JUGEMENT){
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
        Set<ConstraintViolation<MentionAdoptionDto>> violations = validator.validate(adoptionDto);
        if(violations.isEmpty()){
            
           // adoptionDtos.add(adoptionDto);
            acteNaissanceDto.getMentionAdoptionDtos().add(adoptionDto);
            adoptionDto = new MentionAdoptionDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionDissolution(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionDissolutionMariageDto>> violations = validator.validate(dissolutionMariageDto);
        if(violations.isEmpty()){
            
           // dissolutionMariageDtos.add(dissolutionMariageDto);
            acteNaissanceDto.getMentionDissolutionMariageDtos().add(dissolutionMariageDto);
            dissolutionMariageDto = new MentionDissolutionMariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionReconnaissance(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionReconnaissanceDto>> violations = validator.validate(reconnaissanceDto);
        if(violations.isEmpty()){
            
           // reconnaissanceDtos.add(reconnaissanceDto);
            acteNaissanceDto.getMentionReconnaissanceDtos().add(reconnaissanceDto);
            reconnaissanceDto = new MentionReconnaissanceDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionLegitimation(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionLegitimationDto>> violations = validator.validate(legitimationDto);
        if(violations.isEmpty()){
            
           // legitimationDtos.add(legitimationDto);
            acteNaissanceDto.getMentionLegitimationDtos().add(legitimationDto);
            legitimationDto = new MentionLegitimationDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
            });
        }
    }
    
    public void ajouterMentionRectification(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionRectificationDto>> violations = validator.validate(rectificationDto);
        if(violations.isEmpty()){
            
           // rectificationDtos.add(rectificationDto);
            acteNaissanceDto.getMentionRectificationDtos().add(rectificationDto);
            rectificationDto = new MentionRectificationDto();
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
       Set<ConstraintViolation<MentionDecesDto>> violations = validator.validate(decesDto);
       if(violations.isEmpty()){
           LOG.log(Level.INFO,"--MENTION DECES DTO OFFICIER ID {0}",decesDto.getOfficierEtatCivilID());
           //var dcs = new MentionDecesDto(decesDto);
          // decesDtos.add(decesDto);
          // LOG.log(Level.INFO,"--DECES DTO SIZE {0}",decesDtos.size());
           acteNaissanceDto.getMentionDecesDtos().add(decesDto);
           decesDto = new MentionDecesDto();
       }else{
           violations.stream().forEach(v -> {
               addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
           });
       }
       
    }
    
    public void ajouterMentionMariage(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<MentionMariageDto>> violations = validator.validate(mariageDto);
        if(violations.isEmpty()){
            
           // mariageDtos.add(mariageDto);
           // mariageDto.setActeNaissanceID(selectedActe.getId());
            acteNaissanceDto.getMentionMariageDtos().add(mariageDto);
            mariageDto = new MentionMariageDto();
        }else{
            violations.stream().forEach(v -> {
                addGlobalMessage(v.getMessage(), FacesMessage.SEVERITY_ERROR);
                
            });
        }
    
    }
    
    /*
    private void creerMentions(String acteID){
        
    }
    */
    /*
    private void creerMentionMariage(@NotBlank String acteID){
        mariageDtos.stream().forEach(m -> {
            m.setActeNaissanceID(acteID);
            mentionMariageService.create(m);
       });
    }
*/
   /*
    private void creerMentionDeces(@NotBlank String acteID){
       decesDtos.stream().forEach(d -> {
            d.setActeNaissanceID(acteID);
            mentionDecesService.create(d);
       });
      
    }
*/
    
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

    public LazyActeNaissanceDataModel getLazyActeNaissanceDataModel() {
        return lazyActeNaissanceDataModel;
    }

    
    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
    }
   

    public MentionAdoptionDto getAdoptionDto() {
        return adoptionDto;
    }

    public void setAdoptionDto(MentionAdoptionDto adoptionDto) {
        this.adoptionDto = adoptionDto;
    }

    public MentionDecesDto getDecesDto() {
        return decesDto;
    }

    public void setDecesDto(MentionDecesDto decesDto) {
        this.decesDto = decesDto;
    }

    public MentionDissolutionMariageDto getDissolutionMariageDto() {
        return dissolutionMariageDto;
    }

    public void setDissolutionMariageDto(MentionDissolutionMariageDto dissolutionMariageDto) {
        this.dissolutionMariageDto = dissolutionMariageDto;
    }

    public MentionLegitimationDto getLegitimationDto() {
        return legitimationDto;
    }

    public void setLegitimationDto(MentionLegitimationDto legitimationDto) {
        this.legitimationDto = legitimationDto;
    }

    public MentionMariageDto getMariageDto() {
        return mariageDto;
    }

    public void setMariageDto(MentionMariageDto mariageDto) {
        this.mariageDto = mariageDto;
    }

    public MentionReconnaissanceDto getReconnaissanceDto() {
        return reconnaissanceDto;
    }

    public void setReconnaissanceDto(MentionReconnaissanceDto reconnaissanceDto) {
        this.reconnaissanceDto = reconnaissanceDto;
    }

    public MentionRectificationDto getRectificationDto() {
        return rectificationDto;
    }

    public void setRectificationDto(MentionRectificationDto rectificationDto) {
        this.rectificationDto = rectificationDto;
    }

   
    public MentionMariageDto getSelectedMentionMariage() {
        return selectedMentionMariage;
    }

    public void setSelectedMentionMariage(MentionMariageDto selectedMentionMariage) {
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

    public MentionAdoptionDto getSelectedMentionAdoption() {
        return selectedMentionAdoption;
    }

    public void setSelectedMentionAdoption(MentionAdoptionDto selectedMentionAdoption) {
        this.selectedMentionAdoption = selectedMentionAdoption;
    }

    public MentionDecesDto getSelectedMentionDeces() {
        return selectedMentionDeces;
    }

    public void setSelectedMentionDeces(MentionDecesDto selectedMentionDeces) {
        this.selectedMentionDeces = selectedMentionDeces;
    }

    public MentionDissolutionMariageDto getSelectedMentionDissolutionMariage() {
        return selectedMentionDissolutionMariage;
    }

    public void setSelectedMentionDissolutionMariage(MentionDissolutionMariageDto selectedMentionDissolutionMariage) {
        this.selectedMentionDissolutionMariage = selectedMentionDissolutionMariage;
    }

    public MentionLegitimationDto getSelectedMentionLegitimation() {
        return selectedMentionLegitimation;
    }

    public void setSelectedMentionLegitimation(MentionLegitimationDto selectedMentionLegitimation) {
        this.selectedMentionLegitimation = selectedMentionLegitimation;
    }

    public MentionReconnaissanceDto getSelectedMentionReconnaissance() {
        return selectedMentionReconnaissance;
    }

    public void setSelectedMentionReconnaissance(MentionReconnaissanceDto selectedMentionReconnaissance) {
        this.selectedMentionReconnaissance = selectedMentionReconnaissance;
    }

    public MentionRectificationDto getSelectedMentionRectification() {
        return selectedMentionRectification;
    }

    public void setSelectedMentionRectification(MentionRectificationDto selectedMentionRectification) {
        this.selectedMentionRectification = selectedMentionRectification;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    

    

   
}
