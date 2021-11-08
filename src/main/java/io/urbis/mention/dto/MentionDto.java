/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class MentionDto {
   
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    @EqualsAndHashCode.Include
    @NotNull
    private String id = UUID.randomUUID().toString();;
    
    @NotBlank(message = "Le champ 'Decision' ne peut être vide")
    private String decision;
    
    @NotNull
    private LocalDate dateDressage;
  
    @NotBlank(message = "Le champ 'Officier' ne peut être vide")
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private String acteNaissanceID;
}
