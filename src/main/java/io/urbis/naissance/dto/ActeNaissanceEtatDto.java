/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
public class ActeNaissanceEtatDto {
    
    private String id;
    
    private String acteNaissanceID;
    private String extraitTexte;
    private String copieTexte;
    private String mentionMariageLieu;
    private LocalDate mentionMariageDate;
    private String mentionMariageConjointNomComplet;
    
    
}
