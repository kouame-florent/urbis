/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author florent
 */
@Data
public class MentionDto {
    
    private LocalDateTime created; 
    private LocalDateTime updated; 
    
    private String typeMention;
    
    private String decision;
    
    private String officierEtatCivilID;
    private String officierEtatCivilNom;
    private String officierEtatCivilPrenoms;
    private String officierEtatCivilQualite;
    private String officierEtatCivilTitre;
    
    private String acteNaissanceID;
}
