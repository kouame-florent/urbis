/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.share.dto;


import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
public class ActeNaissanceDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String registreID;
    private int numero;
    
    private LocalDateTime enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    private String enfantLocalite;
    private String enfantNom;
    private String enfantPrenoms;
    private String enfantNationalite;  

    private LocalDateTime dateDeclaration;
    private LocalDateTime dateDressage;
    private LocalDateTime dateEnregistrement;
    
    private LocalDateTime jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
    
    private String modeDeclaration;
    private String typeNaissance;
    
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private LocalDateTime pereDateNaissance;
    private LocalDateTime pereDateDeces;
    private String pereLieuDeces;
    private String pereLocalite;
    private String pereTypePiece;
    private String pereNumeroPiece;
    private LocalDateTime pereDatePiece;
    private String pereLieuPiece;
    
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private LocalDateTime mereDateNaissance;
    private LocalDateTime mereDateDeces;
    private String mereLieuDeces;
    private String mereLocalite;
    private String mereTypePiece;
    private String mereNumeroPiece;
    private LocalDateTime mereDatePiece;
    private String mereLieuPiece;
    
    private String declarantNom;
    private String declarantPrenoms;
    private String declarantProfession;
    private String declarantLieuNaissance;
    private String declarantNationalite;
    private LocalDateTime declarantDateNaissance;
    private String declarantLocalite;
    private String declarantTypePiece;
    private String declarantNumeroPiece;
    private LocalDateTime declarantDatePiece;
    private String declarantLieuPiece;
    
    public String interpreteNom;
    public String interpretePrenoms;
    public String interpreteProfession;
    public LocalDateTime interpreteDateNaissance;
    public String interpreteDomicile;
    public String interpreteLangue;
    
    public String temoinPremierNom;
    public String temoinPremierPrenoms;
    public LocalDateTime temoinPremierDateNaissance;
    public String temoinPremierProfession;
    public String temoinPremierDomicile;
    
    public String temoinDeuxiemeNom;
    public String temoinDeuxiemePrenoms;
    public LocalDateTime temoinDeuxiemeDateNaissance;
    public String temoinDeuxiemeProfession;
    public String temoinDeuxiemeDomicile;
  
    private String statut;
    private String motifAnnulation;
    private int nombreCopiesIntegrales;
    private int nombreExtraits;
    private String officierEtatCivilID;
    
}