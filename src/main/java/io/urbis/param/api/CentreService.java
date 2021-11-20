/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.registre.dto.CentreDto;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/centres")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
//@RegisterClientHeaders(AuthHeader.class)
public interface CentreService {
    
    static final Logger LOG = Logger.getLogger(CentreService.class.getName());
    
    default String lookupAuth() {
     LOG.log(Level.INFO, "-------AUTH LOOKUP");
     return "";
    }
    
    @Path("/active")
    @GET
    CentreDto currentCentre();
}
