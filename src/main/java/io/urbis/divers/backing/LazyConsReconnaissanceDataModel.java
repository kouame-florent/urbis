/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.divers.api.ActeConsReconnaissanceService;
import io.urbis.divers.dto.ActeConsReconnaissanceDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author florent
 */
public class LazyConsReconnaissanceDataModel extends LazyDataModel<ActeConsReconnaissanceDto>{
    
    private static final Logger LOG = Logger.getLogger(LazyConsReconnaissanceDataModel.class.getName());
    
    @Inject
    ActeConsReconnaissanceService acteConsReconnaissanceService;
    
    private String registreID;
    
    List<ActeConsReconnaissanceDto> actes = new ArrayList<>();

    @Override
    public List<ActeConsReconnaissanceDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        actes = acteConsReconnaissanceService.findWithFilters(offset, pageSize,registreID);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", actes.size());
        int count = acteConsReconnaissanceService.count();
        setRowCount(count);
       
        return actes;
    }
    
    
    @Override
    public ActeConsReconnaissanceDto getRowData(String rowKey) { 
        //LOG.log(Level.INFO,"ROW KEY: {0}", rowKey);
        //LOG.log(Level.INFO,"DATA SIZE: {0}", actes.size());
        
        for(ActeConsReconnaissanceDto acte : actes){
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
    public String getRowKey(ActeConsReconnaissanceDto acte) {
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
        return acteConsReconnaissanceService.count();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
