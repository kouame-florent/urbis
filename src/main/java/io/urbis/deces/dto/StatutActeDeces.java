/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.deces.dto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutActeDeces {
    
    PROJET("projet"),
    VALIDE("validé"),
    ANNULE("annulé");
    
    public String value;
    
    private StatutActeDeces(String value){
        this.value = value;
    }
    
    public static StatutActeDeces fromString(String statutString){
        for(var t : StatutActeDeces.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActeDeces.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM STATUT FROM: %s", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
}
