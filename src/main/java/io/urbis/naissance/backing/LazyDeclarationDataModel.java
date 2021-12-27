/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.backing;

import io.urbis.naissance.api.ActeNaissanceService;
import io.urbis.naissance.dto.ActeNaissanceDto;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author florent
 */
@Dependent
public class LazyDeclarationDataModel extends LazyDataModel<ActeNaissanceDto>{
    
    private static final Logger LOG = Logger.getLogger(LazyDeclarationDataModel.class.getName());
    
    @Inject
    ActeNaissanceService acteNaissanceService;
    
    private String registreID;
    
    @Override
    public List<ActeNaissanceDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy car data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        
        List<ActeNaissanceDto> actes = acteNaissanceService.findWithFilters(offset, pageSize,registreID);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", actes.size());
        int count = acteNaissanceService.count();
        setRowCount(count);
       
        return actes;
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    
    @Override
    public int count(Map<String, FilterMeta> arg0) {
        return acteNaissanceService.count();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
