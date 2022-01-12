/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.deces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author florent
 */
@Data
@AllArgsConstructor
public class LienDeclarantDto {
    
    private String code;
    private String libelle;
}
