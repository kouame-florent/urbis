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
    
    public String defuntNom;
    public String defuntPrenoms;
    public LocalDateTime defuntDateDeces;
    public String defuntProfession;
    public String defuntLieuDeces;
    public String defuntDomicile;
    public LocalDate defuntDateNaissance;
    public String defuntLieuNaissance;
    public String defuntLocalite;
    public String defuntSexe;
    public String defuntSituationMatrimoniale;
    

    private LocalDate dateDeclaration;
    private LocalDateTime dateDressage;
    
    private LocalDate jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
  
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private LocalDate pereDateNaissance;
    private LocalDate pereDateDeces;
    private String pereLieuDeces;
    private String pereLocalite;
    private String pereTypePiece;
    private String pereNumeroPiece;
    private LocalDate pereDatePiece;
    private String pereLieuPiece;
    
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private LocalDate mereDateNaissance;
    private LocalDate mereDateDeces;
    private String mereLieuDeces;
    private String mereLocalite;
    private String mereTypePiece;
    private String mereNumeroPiece;
    private LocalDate mereDatePiece;
    private String mereLieuPiece;
    
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private String declarantProfession;
    private String declarantLieuNaissance;
    private String declarantNationalite;
    private LocalDate declarantDateNaissance;
    private String declarantLocalite;
    private String declarantTypePiece;
    private String declarantNumeroPiece;
    private LocalDate declarantDatePiece;
    private String declarantLieuPiece;
    
    private String interpreteNom;
    private String interpretePrenoms;
    private String interpreteProfession;
    private LocalDate interpreteDateNaissance;
    private String interpreteDomicile;
    private String interpreteLangue;
    
    private String temoinPremierNom;
    private String temoinPremierPrenoms;
    private LocalDate temoinPremierDateNaissance;
    private String temoinPremierProfession;
    private String temoinPremierDomicile;
    
    private String temoinDeuxiemeNom;
    private String temoinDeuxiemePrenoms;
    private LocalDate temoinDeuxiemeDateNaissance;
    private String temoinDeuxiemeProfession;
    private String temoinDeuxiemeDomicile;
  
    private String statut;
        
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
  
}
