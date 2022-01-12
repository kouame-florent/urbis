/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.deces.backing;

import io.urbis.deces.api.ActeDecesService;
import io.urbis.deces.dto.ActeDecesDto;
import java.util.ArrayList;
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
public class LazyActeDecesDataModel extends LazyDataModel<ActeDecesDto>{
    
     private static final Logger LOG = Logger.getLogger(LazyActeDecesDataModel.class.getName());
    
    @Inject
    ActeDecesService acteDecesService;
    
    private String registreID;
    
    List<ActeDecesDto> actes = new ArrayList<>();

    @Override
    public List<ActeDecesDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        actes = acteDecesService.findWithFilters(offset, pageSize,registreID);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", actes.size());
        int count = acteDecesService.count();
        setRowCount(count);
       
        return actes;
    }
    
    
    @Override
    public ActeDecesDto getRowData(String rowKey) { 
        //LOG.log(Level.INFO,"ROW KEY: {0}", rowKey);
        //LOG.log(Level.INFO,"DATA SIZE: {0}", actes.size());
        
        for(ActeDecesDto acte : actes){
            //LOG.log(Level.INFO,"--- CURRENT ID: {0}", acte.getId());
            if(acte.getId().equals(rowKey)){
                return acte;
            }
            
            
        }
        throw  new IllegalStateException("cannot get acteNaissanceDto from rowkey");
        /*
        return actes.stream().filter(a -> a.getId().equals(rowKey))
                .peek(a -> LOG.log(Level.INFO,"--- CURRENT ID: {0}", a.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("cannot get acteNaissanceDto from rowkey"));
        */
    }


    @Override
    public String getRowKey(ActeDecesDto acte) {
        return acte.getId();
    }

    public String getRegistreID() {
        return registreID;
    }

    public void setRegistreID(String registreID) {
        this.registreID = registreID;
    }

    
    @Override
    public int count(Map<String, FilterMeta> arg0) {
        return acteDecesService.count();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
