/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
public class ActeConsReconnaissanceDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String id;
    
    @NotBlank
    private String operation;
       
    @NotBlank
    private String registreID;
    
    @Min(1)
    private int numero;
    
    private int registreAnnee;
    private int registreNumero;
    
    private String statut;
    
    @NotBlank
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilTitre;
    
    private LocalDate consentementDate;
    private String  consentementNatureCirconscription;
    private String  consentementNomCirconscription;
    private String  consentementNom;
    private String  consentementPrenoms;
    private String  consentementProfession;
    private String  consentementDomicile;
    
    private LocalDate enfantConsentiDateNaissance;
    private String  enfantConsentiNom;
    private String  enfantConsentiPrenoms;
    private String  enfantConsentiProfession;
    private String  enfantConsentiDomicile;
    
    private String  contractantNom;
    private String  contractantPrenoms;
    private String  contractantProfession;
    private String  contractantDomicile;
}
