/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.naissance.dto.ActeNaissanceDto;
import java.util.List;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/naissances")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface ActeNaissanceService {
    
    @POST
    public void create(ActeNaissanceDto acteNaissanceDto);
    
    @PUT @Path("{id}")
    public void update(@PathParam("id")String id, ActeNaissanceDto acteNaissanceDto);
    
    @GET @Path("/{registre-id}")
    public List<ActeNaissanceDto> findWithFilters(@QueryParam("offset") int offset, 
            @QueryParam("page-size") int pageSize,@PathParam("registre-id") String registreID);
    
    @GET @Path("/count")
    public int count();
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID);
}
