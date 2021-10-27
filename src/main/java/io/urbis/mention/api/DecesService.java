/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.DecesDto;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/mentions/deces")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface DecesService {
    
    @POST
    public void create(@NotNull DecesDto dto);
    
    @GET
    public List<DecesDto> findByActeNaissance(@NotBlank String acteNaissanceID);
}
