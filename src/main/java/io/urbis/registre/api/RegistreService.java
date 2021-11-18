/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import io.urbis.registre.dto.RegistreDto;
import io.urbis.registre.dto.RegistrePatchDto;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/registres")
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
//@RegisterClientHeaders(AuthHeader.class)
public interface RegistreService {
    
    
    @POST
    public RegistreDto create(RegistreDto registreDto);  
    
    /*
    @PATCH @Path("{id}")
    public void valider(@PathParam("id") String id);
    */
    
    @PATCH @Path("{id}")
    public void patch(@PathParam("id") String id,RegistrePatchDto patchDto);
    
    @GET
    public List<RegistreDto> findAll();
    
    @GET @Path("{id}")
    public RegistreDto findById(@PathParam("id") String id);
    
    
    @DELETE @Path("{id}")
    public void delete(@PathParam("id") String id);
    
    @GET @Path("/count/{type}")
    public int count(@PathParam("type") String typeRegistre,@QueryParam("annee") int annee,
            @QueryParam("numero") int numero);
    
    @GET 
    public List<RegistreDto> findWithFilters(@QueryParam("offset") int offset, @QueryParam("pageSize") int pageSize,
            @QueryParam("type") String typeRegistre,@QueryParam("annee") int annee, @QueryParam("numero") int numero);
    
    
    
    @Path("/annee-courante")
    @GET
    int anneeCourante();
   
    @Path("/numero/{type}/{annee}")
    @GET
    int numeroRegistre(@PathParam("type") String typeRegistre,@PathParam("annee") int annee);
    
    @GET @Path("numero-premier-acte/{type}/{annee}")
    public int numeroPremierActe(@PathParam("type") String typeRegistre,@PathParam("annee") int annee);

}
