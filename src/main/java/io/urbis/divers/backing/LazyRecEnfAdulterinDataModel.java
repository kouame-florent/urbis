/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.backing;

import io.urbis.divers.api.ActeRecEnfAdulterinService;
import io.urbis.divers.dto.ActeRecEnfantAdulterinDto;
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
public class LazyRecEnfAdulterinDataModel extends LazyDataModel<ActeRecEnfantAdulterinDto>{
    
     private static final Logger LOG = Logger.getLogger(LazyRecEnfAdulterinDataModel.class.getName());
    
    @Inject
    ActeRecEnfAdulterinService ActeRecEnfAdulterinService;
    
    private String registreID;
    
    List<ActeRecEnfantAdulterinDto> actes = new ArrayList<>();

    @Override
    public List<ActeRecEnfantAdulterinDto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        LOG.log(Level.INFO,"Loading the lazy data between {0} and {1}", new Object[]{offset, offset+pageSize} );
        actes = ActeRecEnfAdulterinService.findWithFilters(offset, pageSize,registreID);
        LOG.log(Level.INFO,"LOADED DATA SIZE: {0}", actes.size());
        int count = ActeRecEnfAdulterinService.count();
        setRowCount(count);
       
        return actes;
    }
    
    
    @Override
    public ActeRecEnfantAdulterinDto getRowData(String rowKey) { 
        //LOG.log(Level.INFO,"ROW KEY: {0}", rowKey);
        //LOG.log(Level.INFO,"DATA SIZE: {0}", actes.size());
        
        for(ActeRecEnfantAdulterinDto acte : actes){
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
    public String getRowKey(ActeRecEnfantAdulterinDto acte) {
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
        return ActeRecEnfAdulterinService.count();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
