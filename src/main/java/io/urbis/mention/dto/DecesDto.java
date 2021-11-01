/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author florent
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
public class DecesDto extends MentionDto {
    
    private LocalDate date;
    private String lieu;

    private String localite;
    private LocalDate dateDressage;

}
