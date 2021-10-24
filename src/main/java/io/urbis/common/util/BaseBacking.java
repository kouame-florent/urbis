/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.util;

import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author florent
 */
public abstract class BaseBacking {
    
    @Inject
    FacesContext facesContext;
    
    protected Map<String,Object> getDialogOptions(int widthPercent,int heightPercent,boolean closable){
      
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("closable", false);
        options.put("width", widthPercent+"vw");
        options.put("height", heightPercent+"vh");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "95%");
        
        if(closable)options.put("closable", true) ;
        
        return options;
   }
   
    public void addGlobalMessage(String message,FacesMessage.Severity severity){
        FacesMessage msg = new FacesMessage(severity,message ,"");
       facesContext.addMessage(null, msg);
       
    }
    
    public void addMessage(String clientId,FacesMessage msg){
        facesContext.addMessage(clientId, msg);
    }
    
   
    
}
