/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.service;

import io.urbis.share.dto.ActeNaissanceDto;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
    @GET
    @Path("/numero-acte/{id}")
    public int numeroActe(@PathParam("id") String registreID);
}
