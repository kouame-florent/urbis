/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.common.api;

import io.urbis.common.dto.NationaliteDto;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
//@RegisterClientHeaders(AuthHeader.class)
public interface NationaliteService {
    
    @Path("/nationalites")
    @GET
    List<NationaliteDto> findAll();  
}
