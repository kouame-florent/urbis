/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.dto.LocaliteDto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/localites")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
@RegisterClientHeaders(AuthHeader.class)
public interface LocaliteService {
    
    @Path("/active")
    @GET
    LocaliteDto currentLocalite();
}
