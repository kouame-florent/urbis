/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.deces.api;

import io.urbis.common.util.ExceptionMapper;
import io.urbis.deces.dto.ActeDecesDto;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/deces")
@RegisterProvider(value = ExceptionMapper.class,priority = 50)
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface ActeDecesService {
    
    @POST
    public void create(ActeDecesDto dto);
    
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id, ActeDecesDto dto);
    
    @DELETE @Path("{id}")
    public boolean delete(@PathParam("id") String id);
    
    @GET @Path("{id}")
    public ActeDecesDto findById(@PathParam("id") String id);
    
    @GET
    public List<ActeDecesDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID);
    
    @GET @Path("/count")
    public int count();
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID);
}
