/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.common.BaseBacking;
import io.urbis.registre.service.RegistreService;
import io.urbis.share.dto.ActeNaissanceDto;
import io.urbis.share.dto.RegistreDto;
import java.io.Serializable;
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
@Named(value = "acteNaissanceListBacking")
@ViewScoped
public class ListBacking extends BaseBacking implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ListBacking.class.getName());
    
    @Inject
    LazyDeclarationDataModel lazyDeclarationDataModel;
     
    @Inject 
    @RestClient
    RegistreService registreService;
    
    private String registreID;
    private RegistreDto registreDto;
    
    public void onload(){
        LOG.log(Level.INFO,"REGISTRE ID: {0}",registreID);
        registreDto = registreService.findById(registreID);
        lazyDeclarationDataModel.setRegistreID(registreID);
    }
    
    private ActeNaissanceDto selectedActe;
    
    @PostConstruct
    public void init(){
        
        
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
    
    
}
