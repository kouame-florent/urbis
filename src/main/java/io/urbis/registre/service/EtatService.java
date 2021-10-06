/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.service;

import java.io.File;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/etats")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181/")
public interface EtatService {
    
    @GET
    @Path("/registres")
    public File downloadRegistre();
}
