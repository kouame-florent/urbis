/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.DissolutionMariageDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/mentions/dissolutions")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface MentionDissolutionService {
    
    @POST
    public void create(@NotNull DissolutionMariageDto dto);
    
    @GET
    public List<DissolutionMariageDto> findByActeNaissance(@NotBlank String acteNaissanceID);
    
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id);
}