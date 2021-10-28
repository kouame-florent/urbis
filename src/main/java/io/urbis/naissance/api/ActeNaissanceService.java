/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.api;

import io.urbis.common.util.ExceptionMapper;
import io.urbis.naissance.dto.ActeNaissanceDto;
import java.util.List;
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
@Path("/naissances")
@RegisterProvider(value = ExceptionMapper.class,priority = 50)
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface ActeNaissanceService {
    
    @GET @Path("{id}")
    public ActeNaissanceDto findById(@PathParam("id") String id);
    
    @POST
    public String create(ActeNaissanceDto acteNaissanceDto);
    
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, ActeNaissanceDto acteNaissanceDto);
    
    @GET
    public List<ActeNaissanceDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@QueryParam("registre-id") String registreID);
    
    @GET @Path("/count")
    public int count();
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID);
}
