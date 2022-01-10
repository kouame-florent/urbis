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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ActeRecEnfantAdulterinDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    @NotBlank
    private String operation;
   
    @NotBlank
    private String id;
    
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
    
    private LocalDateTime reconnaissanceDate;
    private String reconnaissanceLieu;
    private String reconnaissanceNatureCirconscription;
    private String reconnaissanceNomCirconscription;
    
    private int enfantNumeroActe;
    private LocalDate enfantDateActe;
    private String enfantNom;
    private String enfantPrenoms;
    private LocalDate enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    
    private String reconnaissantNom;
    private String reconnaissantPrenoms;
    private LocalDate reconnaissantDateNaissance;
    private String reconnaissantProfession;
    private String reconnaissantDomicile;
       
    private String consentementEpouseNom;
    private String consentementEpousePrenoms;
    private String consentementEpouseType;
}
