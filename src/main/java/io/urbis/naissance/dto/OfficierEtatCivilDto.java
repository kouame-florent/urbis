/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;

import java.time.LocalDateTime;
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
public class OfficierEtatCivilDto {
    
    private String id ;
    
    private LocalDateTime created ;
    private LocalDateTime updated ;
       
    private String nom;
    private String prenoms;
    private String qualite;
    private String titre;
    private boolean actif;
    private boolean maire;
}
