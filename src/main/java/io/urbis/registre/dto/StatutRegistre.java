/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

/**
 *
 * @author florent
 */
public enum StatutRegistre {
    
    PROJET("projet"),
    VALIDE("validé"),
    CLOTURE("cloturé"),
    ANNULE("annulé");
    
    public String value;
    
    private StatutRegistre(String value){
        this.value = value;
    }
    
    public static StatutRegistre fromString(String statutString){
        for(var t : StatutRegistre.values()){
            if(t.name().equalsIgnoreCase(statutString)){
                return StatutRegistre.valueOf(t.name());
            }
        }
        
        throw new IllegalArgumentException(statutString);
    }
}
