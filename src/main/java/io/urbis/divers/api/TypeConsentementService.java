/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.divers.api;

import io.urbis.common.util.ExceptionMapper;
import io.urbis.divers.dto.TypeConsentementDto;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 *
 * @author florent
 */
@Path("/divers/types-consentement")
@RegisterProvider(value = ExceptionMapper.class,priority = 50)
@RegisterRestClient(baseUri = "http://127.0.0.1:8181")
public interface TypeConsentementService {
    
    @GET
    public List<TypeConsentementDto> findAll();
}
