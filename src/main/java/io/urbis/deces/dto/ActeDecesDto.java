/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.deces.dto;

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
public class ActeDecesDto { 
    
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
    
    public String defuntNom;
    public String defuntPrenoms;
    public LocalDateTime defuntDateDeces;
    public String defuntProfession;
    public String defuntLieuDeces;
    public String defuntDomicile;
    public LocalDate defuntDateNaissance;
    public String defuntLieuNaissance;
    public String defuntLocaliteNaissance;
    public String defuntSexe;
    public String defuntSituationMatrimoniale;
    

    private LocalDate dateDeclaration;
    private LocalDateTime dateDressage;
    
    private LocalDate jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
  
    private boolean pereDecede;
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private LocalDate pereDateNaissance;
    private LocalDate pereDateDeces;
    private String pereLieuDeces;
    private String pereLocaliteDeces;
    
    private boolean mereDecede;
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private LocalDate mereDateNaissance;
    private LocalDate mereDateDeces;
    private String mereLieuDeces;
    private String mereLocaliteDeces;
      
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private LocalDate declarantDateNaissance;
    private String declarantProfession;
    private String declarantDomicile;
        
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
  
}
