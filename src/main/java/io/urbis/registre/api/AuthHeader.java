/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.registre.api;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;




/**
 *
 * @author florent
 */
@ApplicationScoped
public class AuthHeader implements ClientHeadersFactory{
   
    private static final Logger LOG = Logger.getLogger(AuthHeader.class.getName());
    
    //@Context SecurityContext securityContext;
    
    
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, 
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        System.out.print("----- ADDING HEADERS ----");
        //JsonWebToken jwt = CDI.current().select(JsonWebToken.class).get();
       // Principal principal = securityContext.getCallerPrincipal();
        //LOG.log(Level.INFO, "----JWT: {0}", principal.getName());
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>(); 
        //String bearer = "Bearer " + jwt.getRawToken();
        //result.add("Authorization: ", bearer);
        return result;
    }
    
}
