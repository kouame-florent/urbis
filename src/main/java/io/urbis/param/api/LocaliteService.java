/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.param.api;

import io.urbis.param.dto.LocaliteDto;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/localites")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
//@RegisterClientHeaders(AuthHeader.class)
public interface LocaliteService {
    
    @POST
    public void create(LocaliteDto dto);
    
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, LocaliteDto dto);
    
    @GET
    public List<LocaliteDto> findAll();
    
    @Path("/active")
    @GET
    LocaliteDto findActive();
}
