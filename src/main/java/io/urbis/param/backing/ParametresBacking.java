/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.param.api.CentreService;
import io.urbis.param.api.LocaliteService;
import io.urbis.param.api.TitreOfficierService;
import io.urbis.param.api.TribunalService;
import io.urbis.param.api.TypeLocaliteService;
import io.urbis.param.dto.CentreDto;
import io.urbis.param.dto.LocaliteDto;
import io.urbis.param.dto.OfficierEtatCivilDto;
import io.urbis.param.dto.TitreOfficierDto;
import io.urbis.param.dto.TribunalDto;
import io.urbis.param.dto.TypeLocaliteDto;
import io.urbis.param.api.OfficierService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author florent
 */
@Named(value = "parametresBacking")
@ViewScoped
public class ParametresBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOG = Logger.getLogger(ParametresBacking.class.getName());
    
    @Inject
    LocaliteService localiteService;
    
    @Inject
    CentreService centreService;
    
    @Inject
    TypeLocaliteService typeLocaliteService ;
    
    @Inject
    TribunalService tribunalService;
    
    @Inject
    OfficierService officierService;
    
    @Inject
    TitreOfficierService titreOfficierService;
    
    private List<LocaliteDto> localites = List.of();
    
    private List<TypeLocaliteDto> typesLocalite = List.of();
    private LocaliteDto selectedLocalite;
    
    private List<CentreDto> centres = List.of();
    private CentreDto selectedCentre;
    
    private List<TribunalDto> tribunaux = List.of();
    private TribunalDto selectedTribunal;
    
    private List<OfficierEtatCivilDto> officiers = List.of();
    private OfficierEtatCivilDto selectedOfficier;
    
    private List<TitreOfficierDto> titres = List.of();
    
    @PostConstruct
    public void init(){
        localites = localiteService.findAll();
        typesLocalite = typeLocaliteService.findAll();
        centres = centreService.findAll();
        tribunaux = tribunalService.findAll();
        officiers = officierService.findAll();
        titres = titreOfficierService.findAll();
    }
    
    public void openNewLocalite(){
        selectedLocalite = new LocaliteDto();
    }
    
    public void openNewCentre(){
        selectedCentre = new CentreDto();
    }
    
    public void openNewTribunal(){
        selectedTribunal = new TribunalDto();
    }
    
    public void openNewOfficier(){
        selectedOfficier = new OfficierEtatCivilDto();
    }
    
    public void enregistrerLocalite(){
        if(selectedLocalite.getId() == null){
            localiteService.create(selectedLocalite);
            
        }else{
            localiteService.update(selectedLocalite.getId(), selectedLocalite);
            
        }
        
        localites = localiteService.findAll();
      
        
    }
    
    public void enregistrerCentre(){
        if(selectedCentre.getId() == null){
            LocaliteDto loc = localiteService.findActive();
            if(loc == null){
                addGlobalMessage("aucune localité active", FacesMessage.SEVERITY_ERROR);
                return;
            }
            LOG.log(Level.INFO, "---LOCALITE ID: {0}", loc.getId());
            selectedCentre.setLocaliteID(loc.getId());
            centreService.create(selectedCentre);
            
        }else{
            centreService.update(selectedCentre.getId(), selectedCentre);
            
        }
        centres = centreService.findAll();
        
    }
    
    public void enregistrerTribunal(){
        if(selectedTribunal.getId() == null){
            tribunalService.create(selectedTribunal);
        }else{
            tribunalService.update(selectedTribunal.getId(), selectedTribunal);
        }
        
        tribunaux = tribunalService.findAll();
    }
    
    public void enregistrerOfficier(){
        if(selectedOfficier.getId() == null){
            officierService.create(selectedOfficier);
        }else{
            officierService.update(selectedOfficier.getId(), selectedOfficier);
        }
        
        officiers = officierService.findAll();
    }

    public List<LocaliteDto> getLocalites() {
        return localites;
    }

    public void setLocalites(List<LocaliteDto> localites) {
        this.localites = localites;
    }

    public LocaliteDto getSelectedLocalite() {
        return selectedLocalite;
    }

    public void setSelectedLocalite(LocaliteDto selectedLocalite) {
        this.selectedLocalite = selectedLocalite;
    }

    public List<TypeLocaliteDto> getTypesLocalite() {
        return typesLocalite;
    }

    public List<CentreDto> getCentres() {
        return centres;
    }

    public CentreDto getSelectedCentre() {
        return selectedCentre;
    }

    public void setSelectedCentre(CentreDto selectedCentre) {
        this.selectedCentre = selectedCentre;
    }

    public TribunalDto getSelectedTribunal() {
        return selectedTribunal;
    }

    public void setSelectedTribunal(TribunalDto selectedTribunal) {
        this.selectedTribunal = selectedTribunal;
    }

    public List<TribunalDto> getTribunaux() {
        return tribunaux;
    }

    public OfficierEtatCivilDto getSelectedOfficier() {
        return selectedOfficier;
    }

    public void setSelectedOfficier(OfficierEtatCivilDto selectedOfficier) {
        this.selectedOfficier = selectedOfficier;
    }

    public List<OfficierEtatCivilDto> getOfficiers() {
        return officiers;
    }

    public List<TitreOfficierDto> getTitres() {
        return titres;
    }
    
    
    
    
}
