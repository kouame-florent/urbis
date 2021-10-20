/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistreDto {
    
    private String id ;
    
    private LocalDateTime created; 
    private LocalDateTime updated;
    
    private String typeRegistre;
    
    private String libelle;
   
    private String localite; 
    private String localiteID; 
    
    private String centre;
    private String centreID;
    
    private int annee;
    
    private int numero;
    
    private String tribunal;
    private String tribunalID;
    
    private String officierEtatCivilNomComplet;
    private String officierEtatCivilID;
    
    private int numeroPremierActe; 
    
    private int numeroDernierActe;
     
    private int nombreDeFeuillets;
    
    private int nombreActe;
    
    private String statut;
    
    private LocalDateTime dateAnnulation;
    
    @Size(max = 250)
    private String motifAnnulation;
}
