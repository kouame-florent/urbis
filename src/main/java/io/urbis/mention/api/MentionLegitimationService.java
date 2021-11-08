/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.MentionLegitimationDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/mentions/legitimations")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface MentionLegitimationService {
    
    @POST
    public void create(@NotNull MentionLegitimationDto dto);
    
    @GET
    public List<MentionLegitimationDto> findByActeNaissance(@QueryParam("acte-naissance-id") @NotBlank String acteNaissanceID);
    
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id);
}
