/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.util;

import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

/**
 *
 * @author florent
 */
@Priority(4000)
public class ExceptionMapper implements ResponseExceptionMapper<RuntimeException>{
    
    private static final Logger LOG = Logger.getLogger(ExceptionMapper.class.getName());

    @Override
    public RuntimeException toThrowable(Response response) {
         int status = response.getStatus();
         
        String msg = getBody(response); 

        RuntimeException re ;
        switch (status) {
          case 412: 
            re = new ValidationException(msg);
            break;
          case 404: 
            LOG.log(Level.INFO, "---MAPPER ERROR: {0}", 404);
            re = new EntityNotFoundException(msg);
            break;
          default:
            LOG.log(Level.INFO, "---MAPPER ERROR: {0}", "DEFAULT");
            re = new WebApplicationException(msg,status);
        }
        return re;
    }
    
    private String getBody(Response response) {
        ByteArrayInputStream is = (ByteArrayInputStream) response.getEntity();
        byte[] bytes = new byte[is.available()];
        is.read(bytes,0,is.available());
        String body = new String(bytes);
        return body;
    }
    
}
