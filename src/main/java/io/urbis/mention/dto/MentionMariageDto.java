/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */
@Data
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
public class MentionMariageDto extends MentionDto{
    
    
    private String lieu;  
    private LocalDate date;
    private String conjointNom;
    private String conjointPrenoms;
    private String conjointDomicile;
    private String conjointProfession;
}
