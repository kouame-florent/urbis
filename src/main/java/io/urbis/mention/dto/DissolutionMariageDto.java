/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DissolutionMariageDto extends MentionDto{
    
    private String nom;
    private String prenoms;
    private String domicile;
    private String profession;
    private String lieu;
    private String dateMariage;
    
    private String numeroJugement;
    private String dateJugement;
    
    private String typeDissolution;
    
    private String tribunalID;
    private String dateDressage;
    
    
}
