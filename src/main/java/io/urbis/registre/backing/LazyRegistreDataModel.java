/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.backing;

import io.urbis.registre.api.RegistreService;
import io.urbis.registre.dto.RegistreDto;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author florent
 */
@Dependent 
public class LazyRegistreDataModel extends LazyDataModel<RegistreDto> {
    
   // private List<RegistreDto> datasource;
    
    private static final Logger LOG = Logger.getLogger(LazyRegistreDataModel.class.getName());

    
    @Inject 
    @RestClient
    RegistreService registreService;

    
    private String typeRegistre;
    private int annee;
    private int numero;
    

    @Override
    public List<RegistreDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy car data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        if(typeRegistre == null){
            throw new IllegalStateException("'typeRegistre' cannot be null");
        }
        List<RegistreDto> regs = registreService.findWithFilters(offset, pageSize, typeRegistre, annee, numero);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", regs.size());
        int count = registreService.count(typeRegistre,annee,numero);
        setRowCount(count);
       
        return regs;
    }

    public void setTypeRegistre(String typeRegistre) {
        this.typeRegistre = typeRegistre;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    
    
}
