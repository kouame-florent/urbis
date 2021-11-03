/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author florent
 */
public enum TypeRegistre {
    
    NAISSANCE("Naissance"),
    MARIAGE("Mariage"),
    DIVERS("Divers"),
    DECES("Décès"),
    SPECIAL_NAISSANCE("Spécial de naissance"),
    SPECIAL_DECES("Spécial de décès");
    
    private final String libelle;
    
    private TypeRegistre(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
    public static TypeRegistre fromString(String typeString){
        for(var t : TypeRegistre.values()){
            if(t.name().equalsIgnoreCase(typeString)){
                return TypeRegistre.valueOf(t.name());
            }
        }
        System.out.printf("CANNOT GET ENUM TYPE REGISTRE FROM: %s", typeString);
        Response res = Response.status(Response.Status.BAD_REQUEST)
                   .entity(new IllegalArgumentException(typeString)).build();
        throw new WebApplicationException(res);
         
    }
    
}
