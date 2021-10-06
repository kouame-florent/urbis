/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.share.dto;

/**
 *
 * @author florent
 */
public enum TypeRegistre {
    
    NAISSANCE("Registre de naissance"),
    MARIAGE("Registre de mariage"),
    DECES("Registre de decès"),
    DIVERS("Registre d'actes divers"),
    SPECIAL_NAISSANCE("Registre spécial de naissance");
    
    public String libelle;
    
    private TypeRegistre(String libelle){
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
    
}
