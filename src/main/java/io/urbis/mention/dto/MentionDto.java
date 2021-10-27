/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author florent
 */
@Data
public abstract class MentionDto {
   
    private String id;
    
    private LocalDateTime created = LocalDateTime.now();
    private LocalDateTime updated = LocalDateTime.now();
   
    private String decision;
  
    private String officierEtatCivilID;
   
    private String acteNaissanceID;
}
