/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.dto.MariageDto;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
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
@Path("/mentions/mariages")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface MariageService {
    @POST
    public void create(@NotNull MariageDto dto);
    
    @GET
    public Set<MariageDto> findByActeNaissance(@QueryParam("acte-naissance-id") @NotBlank String acteNaissanceID);
}
