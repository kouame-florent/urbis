/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum StatutActeNaissance {
    
    PROJET("projet"),
    VALIDE("validé"),
    ANNULE("annulé");
    
    public String value;
    
    private StatutActeNaissance(String value){
        this.value = value;
    }
    
    public static StatutActeNaissance fromString(String statutString){
        for(var t : StatutActeNaissance.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutActeNaissance.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM STATUT FROM: %s", statutString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(statutString)).build();
        throw new WebApplicationException(res);
    }
}
