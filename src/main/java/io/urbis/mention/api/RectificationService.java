/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.mention.api;

import io.urbis.mention.dto.AdoptionDto;
import io.urbis.mention.dto.RectificationDto;
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
@Path("/mentions/rectifications")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface RectificationService {
    @POST
    public void create(@NotNull RectificationDto dto);
    
    @GET
    public List<RectificationDto> findByActeNaissance(@NotBlank String acteNaissanceID);
}
