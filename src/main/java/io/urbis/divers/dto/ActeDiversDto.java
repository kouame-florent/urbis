/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 *
 * @author florent
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "type")
@JsonSubTypes({ 
  @Type(value = ActeRecEnfantNaturelDto.class, name = "reconnaissance_enfant_naturel"), 
  @Type(value = ActeRecEnfantAdulterinDto.class, name = "reconnaissance_enfant_adulterin") 
})
public class ActeDiversDto {
    
}
