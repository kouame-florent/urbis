/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.dto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutActeDivers {
    
    PROJET("Projet"),
    VALIDE("Validé"),
    ANNULE("Annulé");
    
    public String value;
    
    private StatutActeDivers(String value){
        this.value = value;
    }
    
   public static StatutActeDivers fromString(String statutString){
        for(var t : StatutActeDivers.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActeDivers.valueOf(t.name());
            }
        }
        
        Logger log = Logger.getLogger(StatutActeDivers.class.getName());
        log.log(Level.SEVERE,"cannot get Enum 'StatutActeMariage' from: {0}", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
}
