/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.naissance.api;

import io.urbis.naissance.dto.MentionDto;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/mentions")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface MentionService {
    
    @POST
    public void create(@NotNull MentionDto mentionDto);
    
    @GET 
    public List<MentionDto> findByActeNaissance(@QueryParam("acte-naissance-id") String id);
}
