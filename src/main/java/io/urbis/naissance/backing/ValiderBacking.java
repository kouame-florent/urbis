/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.mention.api.MentionAdoptionService;
import io.urbis.mention.api.MentionDecesService;
import io.urbis.mention.api.MentionDissolutionService;
import io.urbis.mention.api.MentionLegitimationService;
import io.urbis.mention.api.MentionMariageService;
import io.urbis.mention.api.MentionReconnaissanceService;
import io.urbis.mention.api.MentionRectificationService;
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
import io.urbis.naissance.dto.StatutActeNaissance;
import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.api.NationaliteService;
import io.urbis.param.api.OfficierService;
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
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceValiderBacking")
@ViewScoped
public class ValiderBacking extends BaseBacking implements Serializable{
    private static final Logger LOG = Logger.getLogger(ValiderBacking.class.getName());
    
    /*
    @Inject 
    @RestClient
    OfficierService officierService;
    
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
    */
    
    @Inject
    @RestClient
    ActeNaissanceService acteNaissanceService;
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
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
    
     
    private String acteNaissanceID;
    private ActeNaissanceDto acteNaissanceDto;
    
    private RegistreDto registreDto;
    
    
    
   // private ActeNaissanceDto acteNaissanceDto;
    
    private List<MentionAdoptionDto> adoptionDtos = new ArrayList<>();
    
    private List<MentionDecesDto> decesDtos = new ArrayList<>(); 
    
    private List<MentionDissolutionMariageDto> dissolutionMariageDtos = new ArrayList<>();
      
    private List<MentionLegitimationDto> legitimationDtos = new ArrayList<>();
    
    private Set<MentionMariageDto> mariageDtos = new HashSet<>();
    
    private List<MentionReconnaissanceDto> reconnaissanceDtos = new ArrayList<>();
    
    private List<MentionRectificationDto> rectificationDtos = new ArrayList<>();
    
   
    
    @PostConstruct
    public void init(){
        
    }
    
    
    public void onload(){
        LOG.log(Level.INFO,"ACTE ID: {0}",acteNaissanceID);
        acteNaissanceDto = acteNaissanceService.findById(acteNaissanceID);
        registreDto = registreService.findById(acteNaissanceDto.getRegistreID());
        //LOG.log(Level.INFO,"REGISTRE LIBELLE: {0}",registreDto.getLibelle());
        //numeroActe = acteNaissanceService.numeroActe(registreID);
        
        adoptionDtos = mentionAdoptionService.findByActeNaissance(acteNaissanceID);
        dissolutionMariageDtos = mentionDissolutionService.findByActeNaissance(acteNaissanceID);
        decesDtos = mentionDecesService.findByActeNaissance(acteNaissanceID);
        legitimationDtos = mentionLegitimationService.findByActeNaissance(acteNaissanceID);
        mariageDtos = mentionMariageService.findByActeNaissance(acteNaissanceID);
        reconnaissanceDtos = mentionReconnaissanceService.findByActeNaissance(acteNaissanceID);
        rectificationDtos = mentionRectificationService.findByActeNaissance(acteNaissanceID);
        
    }
    
    
    public void valider(){
        acteNaissanceDto.setStatut(StatutActeNaissance.VALIDE.name());
        //acteNaissanceDto.setOperation(Operation.MODIFICATION.name());
       // try{
            acteNaissanceService.update(acteNaissanceID, acteNaissanceDto);
       // }catch(WebApplicationException ex){
       //     PrimeFaces.current().dialog().closeDynamic(ex);
       // }
        
        //addGlobalMessage("Acte validé avec succès", FacesMessage.SEVERITY_INFO);
        PrimeFaces.current().dialog().closeDynamic(null);
    }
    
    
    

    public String getActeNaissanceID() {
        return acteNaissanceID;
    }

    public void setActeNaissanceID(String acteNaissanceID) {
        this.acteNaissanceID = acteNaissanceID;
    }

    public ActeNaissanceDto getActeNaissanceDto() {
        return acteNaissanceDto;
    }

    public void setActeNaissanceDto(ActeNaissanceDto acteNaissanceDto) {
        this.acteNaissanceDto = acteNaissanceDto;
    }

    public RegistreDto getRegistreDto() {
        return registreDto;
    }

    public void setRegistreDto(RegistreDto registreDto) {
        this.registreDto = registreDto;
    }
   
    
    public List<MentionAdoptionDto> getAdoptionDtos() {
        return adoptionDtos;
    }

    public List<MentionDecesDto> getDecesDtos() {
        return decesDtos;
    }

    public List<MentionDissolutionMariageDto> getDissolutionMariageDtos() {
        return dissolutionMariageDtos;
    }

    public List<MentionLegitimationDto> getLegitimationDtos() {
        return legitimationDtos;
    }

    public Set<MentionMariageDto> getMariageDtos() {
        return mariageDtos;
    }

    public List<MentionReconnaissanceDto> getReconnaissanceDtos() {
        return reconnaissanceDtos;
    }

    public List<MentionRectificationDto> getRectificationDtos() {
        return rectificationDtos;
    }
    
    
}
