/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author florent
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MariageDto extends MentionDto{
    
    private String lieu;  
    private String date;
    private String dateDressage;
    private String epouseNom;
    private String epousePrenoms;
    private String epouseDomicile;
    private String epouseProfession;
    
    private String epouxNom;
    private String epouxPrenoms;
    private String epouxDomicile;
    private String epouxProfession;
}
