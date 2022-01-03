/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.api;

import io.urbis.common.util.ExceptionMapper;
import io.urbis.divers.dto.ActeRecEnfantNaturelDto;
import java.util.List;
import javax.transaction.Transactional;
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
@Path("/divers/reconnaissance-enfant-naturel")
@RegisterProvider(value = ExceptionMapper.class,priority = 50)
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface ActeRecEnfNaturelService {
    
    @Transactional
    @POST
    public void create(ActeRecEnfantNaturelDto dto);
    
    @Transactional
    @PUT @Path("{id}")
    public void update(@PathParam("id") String id,ActeRecEnfantNaturelDto dto);
    
    @GET @Path("{id}")
    public ActeRecEnfantNaturelDto findById(@PathParam("id") String id);
    
    @GET
    public List<ActeRecEnfantNaturelDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID);
    
    @GET @Path("/count")
    public int count();
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID);
}
