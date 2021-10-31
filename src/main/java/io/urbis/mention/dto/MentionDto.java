/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author florent
 */
@Data
public abstract class MentionDto {
   
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    @NotBlank(message = "L champ 'Decision' ne peut être vide")
    private String decision;
    
    private LocalDate dateDressage;
  
    @NotBlank(message = "Le champ 'Officier' ne peut être vide")
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private String acteNaissanceID;
}
