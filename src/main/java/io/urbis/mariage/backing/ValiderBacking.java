/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mariage.backing;

import io.urbis.common.util.BaseBacking;
import io.urbis.mariage.dto.ActeMariageDto;
import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author florent
 */
@Named(value = "acteMariageValiderBacking")
@ViewScoped
public class ValiderBacking extends EditerBacking implements Serializable{
        
    
    
}
