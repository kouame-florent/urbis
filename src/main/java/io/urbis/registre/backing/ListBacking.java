/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.registre.service.EtatService;
import io.urbis.registre.service.RegistreService;
import io.urbis.registre.service.TypeRegistreService;
import io.urbis.share.dto.RegistreDto;
import io.urbis.share.dto.TypeRegistreDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "listBacking")
@ViewScoped
public class ListBacking extends BaseBacking implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ListBacking.class.getName());
    
    @Inject 
    @RestClient
    TypeRegistreService typeRegistreService;
    
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject
    @RestClient
    EtatService etatService;
    
    
    @Inject
    LazyRegistreDataModel lazyRegistreDataModel;
    
    @Inject
    FilterData filterData;
    
   // private LazyDataModel<RegistreDto> lazyModel;
    
    private List<RegistreDto> registres = new ArrayList<>();
    private RegistreDto selectedRegistre;
    private int nombreActe;
    
    private List<TypeRegistreDto> typesRegistre;
    private TypeRegistreDto selectedType;
    
   // private int annee;
    
    @PostConstruct
    public void init(){
        typesRegistre = typeRegistreService.findAll();
        
        lazyRegistreDataModel.setTypeRegistre("naissance");  
        selectedType = defaultSelectedType();
    }
    
    public TypeRegistreDto defaultSelectedType(){
       return typesRegistre.stream()
                .peek(c -> LOG.log(Level.INFO, "TYPE NAME: {0}", c.getCode()))
                //.filter(t -> t.getCode() == TypeRegistre.NAISSANCE.name())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot get type registre 'NAISSANCE'"));
    }
    
    public void filtrer(){
        LOG.log(Level.INFO, "FILTRER...");
       
        LOG.log(Level.INFO, "FILTRE ANNEE: {0}", filterData.getAnnee());   
        LOG.log(Level.INFO, "FILTRE NUMERO: {0}", filterData.getNumero());
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        lazyRegistreDataModel.setTypeRegistre(selectedType.getCode());
        lazyRegistreDataModel.setAnnee(filterData.getAnnee());
        lazyRegistreDataModel.setNumero(filterData.getNumero());
    
    }
    
    public void effacerFiltres(){
        filterData.setAnnee(0);
        filterData.setNumero(0);
       // lazyRegistreDataModel.setAnnee(filterData.getAnnee());
    }
    
   
    public void onTypeRgistreSelect(){
        LOG.log(Level.INFO, "SELECTED TYPE: {0}", selectedType);
        lazyRegistreDataModel.setTypeRegistre(selectedType.getCode());
       // lazyRegistreDataModel.setAnnee(2018);
        
    }
    
    public void showCreerView(){
        Map<String,Object> options = getDialogOptions(90, 90, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("creer", options, null);
    }
    
    public void showCreerSerieView(){
        Map<String,Object> options = getDialogOptions(90, 90, true);
        options.put("resizable", false);
        PrimeFaces.current().dialog().openDynamic("creer-serie", options, null);
    }
    
    public void showValiderView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("valider", getDialogOptions(70,95,true), params);
    
    }
    
    public void showAnnulerView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("annuler", getDialogOptions(70,95,true), params);
    
    }
    
    public void showDeclarationView(RegistreDto registreDto){
        LOG.log(Level.INFO, "REGISTRE ID: {0}", registreDto.getId());
        var values = List.of(registreDto.getId());
        Map<String, List<String>> params = Map.of("id", values);
        PrimeFaces.current().dialog().openDynamic("/naissance/declaration", getDialogOptions(98,98,true), params);
    }
    
    public void returnToCaller(SelectEvent event){
        
    }
    
    public void onCreate(SelectEvent event){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de registre", "Registre crée avec succès");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
        
    
    
    
    public void onSerieCreated(SelectEvent event){
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Création de registre", "Serie créee avec succès");
        
        FacesContext.getCurrentInstance().addMessage(null, message);
    
    }
    
    public String statutSeverity(String statut){
        
        if(statut.equalsIgnoreCase("PROJET")){
            return "warning";
        }
         
        if(statut.equalsIgnoreCase("VALIDE")){
            return "success";
        }
        
        if(statut.equalsIgnoreCase("CLOTURE")){
            return "info";
        }
        
         if(statut.equalsIgnoreCase("ANNULE")){
            return "danger";
        }
        
        return "";
    }
    
    
    public StreamedContent download(){
       File file = etatService.downloadRegistre();
       LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
       LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
       LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
       
       StreamedContent content = null;
        try {
            InputStream input = new FileInputStream(file);
            content = DefaultStreamedContent.builder()
                .name("registres.pdf")
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
    }
    

    public List<RegistreDto> getRegistres() {
        return registres;
    }

    public RegistreDto getSelectedRegistre() {
        return selectedRegistre;
    }

    public void setSelectedRegistre(RegistreDto selectedRegistre) {
        this.selectedRegistre = selectedRegistre;
    }

    public int getNombreActe() {
        return nombreActe;
    }

    public TypeRegistreDto getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(TypeRegistreDto selectedType) {
        this.selectedType = selectedType;
    }

    public List<TypeRegistreDto> getTypesRegistre() {
        return typesRegistre;
    }

    public LazyRegistreDataModel getLazyRegistreDataModel() {
        return lazyRegistreDataModel;
    }

    public FilterData getFilterData() {
        return filterData;
    }

    public void setFilterData(FilterData filterData) {
        this.filterData = filterData;
    }

    

   
    
    
}
