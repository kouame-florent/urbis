/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.api;


import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/naissances/etats")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181/")
public interface ActeNaissanceEtatService {
    
    @GET
    @Path("/acte_naissance/{tenant}")
    public File downloadActeNaissance(@PathParam("tenant") String tenant,@QueryParam("ACTE_NAISSANCE_ID") String acteNaissanceID);
}
