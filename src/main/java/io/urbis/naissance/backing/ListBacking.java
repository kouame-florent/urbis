/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.BaseBacking;
import io.urbis.registre.service.EtatService;
import io.urbis.registre.service.RegistreService;
import io.urbis.naissance.dto.ActeNaissanceDto;
import io.urbis.registre.dto.RegistreDto;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author florent
 */
@Named(value = "acteNaissanceListBacking")
@ViewScoped
public class ListBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListBacking.class.getName());
    
    @Inject
    LazyDeclarationDataModel lazyDeclarationDataModel;
     
    @Inject 
    @RestClient
    RegistreService registreService;
    
    @Inject 
    @RestClient
    EtatService etatService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    String selectedActeID;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyDeclarationDataModel.setRegistreID(registreID);
    }
    
    private ActeNaissanceDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
    }
    
    public StreamedContent download(){
       File file = etatService.downloadActeNaissance(selectedActeID);
       LOG.log(Level.INFO, "FILE NAME: {0}", file.getName());
       LOG.log(Level.INFO, "FILE ABSOLUTE PATH: {0}", file.getAbsolutePath());
       LOG.log(Level.INFO, "FILE LENGHT: {0}", file.length());
       
       StreamedContent content = null;
        try {
            InputStream input = new FileInputStream(file);
            content = DefaultStreamedContent.builder() 
                .name("acte_naissance.pdf")
                .contentType("application/pdf")
                .stream(() -> input).build();
                
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ListBacking.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       return content;
    }

    public LazyDeclarationDataModel getLazyDeclarationDataModel() {
        return lazyDeclarationDataModel;
    }

    public void setLazyDeclarationDataModel(LazyDeclarationDataModel lazyDeclarationDataModel) {
        this.lazyDeclarationDataModel = lazyDeclarationDataModel;
    }

    public ActeNaissanceDto getSelectedActe() {
        return selectedActe;
    }

    public void setSelectedActe(ActeNaissanceDto selectedActe) {
        this.selectedActe = selectedActe;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    public String getSelectedActeID() {
        return selectedActeID;
    }

    public void setSelectedActeID(String selectedActeID) {
        this.selectedActeID = selectedActeID;
    }
    
    
}
