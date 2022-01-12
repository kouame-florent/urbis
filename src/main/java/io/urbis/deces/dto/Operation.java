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
public enum Operation {
    DECLARATION("Declaration"),
    SAISIE_ACTE_EXISTANT("Saisie acte existant"),
    MODIFICATION("Modification"),
    VALIDATION("Validation");
    
    private final String libelle;
    
    private Operation(String libelle){
        this.libelle = libelle;
    }
    
    public static Operation fromString(String operation){
        for(var t : Operation.values()){
            if(t.name().equalsIgnoreCase(operation)){
                return Operation.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM Operation FROM: %s", operation);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(operation)).build();
        throw new WebApplicationException(res);
    }

    public String getLibelle() {
        return libelle;
    }
    
    
}
