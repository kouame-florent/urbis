/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;


import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.dto.DecesDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
@EqualsAndHashCode
public class ActeNaissanceDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String operation;
    
    @NotBlank
    private String id;
    
    @NotBlank
    private String registreID;
    @Min(1)
    private int numero;
    
    private String enfantDateNaissance;
    private String enfantLieuNaissance;
    private String enfantSexe;
    private String enfantLocalite;
    private String enfantNom;
    private String enfantPrenoms;
    private String enfantNationalite;  

    private String dateDeclaration;
    private String dateDressage;
   // private String dateEnregistrement;
    
    private String jugementDate;
    private String jugementNumero;
    private String jugementTribunal;
    
    private String modeDeclaration;
    private String typeNaissance;
    
    private String pereNom;
    private String perePrenoms;
    private String pereProfession;
    private String pereLieuNaissance;
    private String pereNationalite;
    private String pereDateNaissance;
    private String pereDateDeces;
    private String pereLieuDeces;
    private String pereLocalite;
    private String pereTypePiece;
    private String pereNumeroPiece;
    private String pereDatePiece;
    private String pereLieuPiece;
    
    private String mereNom;
    private String merePrenoms;
    private String mereProfession;
    private String mereLieuNaissance;
    private String mereNationalite;
    private String mereDateNaissance;
    private String mereDateDeces;
    private String mereLieuDeces;
    private String mereLocalite;
    private String mereTypePiece;
    private String mereNumeroPiece;
    private String mereDatePiece;
    private String mereLieuPiece;
    
    private String declarantLien;
    private String declarantNom;
    private String declarantPrenoms;
    private String declarantProfession;
    private String declarantLieuNaissance;
    private String declarantNationalite;
    private String declarantDateNaissance;
    private String declarantLocalite;
    private String declarantTypePiece;
    private String declarantNumeroPiece;
    private String declarantDatePiece;
    private String declarantLieuPiece;
    
    public String interpreteNom;
    public String interpretePrenoms;
    public String interpreteProfession;
    public String interpreteDateNaissance;
    public String interpreteDomicile;
    public String interpreteLangue;
    
    public String temoinPremierNom;
    public String temoinPremierPrenoms;
    public String temoinPremierDateNaissance;
    public String temoinPremierProfession;
    public String temoinPremierDomicile;
    
    public String temoinDeuxiemeNom;
    public String temoinDeuxiemePrenoms;
    public String temoinDeuxiemeDateNaissance;
    public String temoinDeuxiemeProfession;
    public String temoinDeuxiemeDomicile;
  
    private String statut;
    private String motifAnnulation;
    private int nombreCopiesIntegrales;
    private int nombreExtraits;
    
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private int registreAnnee;
    private int registreNumero;
    
    private List<AdoptionDto> adoptionDtos = new ArrayList<>();
    private List<DecesDto> decesDtos = new ArrayList<>();
    
}