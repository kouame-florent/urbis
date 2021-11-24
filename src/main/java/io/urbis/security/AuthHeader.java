/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.urbis.security;

import java.security.Principal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;




/**
 *
 * @author florent
 */
@ApplicationScoped
public class AuthHeader implements ClientHeadersFactory{
   
    private static final Logger LOG = Logger.getLogger(AuthHeader.class.getName());
    
    //@Context SecurityContext securityContext;
    
    @Inject
    FacesContext facesContext;
    
    
    
    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders, 
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        System.out.print("----- ADDING HEADERS ----");
        //JsonWebToken jwt = CDI.current().select(JsonWebToken.class).get();
       // Principal principal = securityContext.getCallerPrincipal();
        //LOG.log(Level.INFO, "----JWT: {0}", principal.getName());
        
       // final MultivaluedMap<String, String> headers =  new MultivaluedHashMap<String, String>(incomingHeaders);
       // headers.putAll(clientOutgoingHeaders);
        
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>(); 
        String bearer = "Bearer " + getRawToken();
       
        result.add("Authorization", bearer);
        //headers.put("Authorization",List.of(bearer)); 
        return result;
    }
    
    private String getRawToken(){
        KeycloakPrincipal principal = (KeycloakPrincipal)facesContext.getExternalContext().getUserPrincipal();
        //LOG.log(Level.INFO, "--------- KEYCLOAK PRINCIPAL: {0}", principal); 
        LOG.log(Level.INFO, "--------- KEYCLOAK PRINCIPAL: {0}", principal.getKeycloakSecurityContext()); 
        RefreshableKeycloakSecurityContext ctx = (RefreshableKeycloakSecurityContext)principal.getKeycloakSecurityContext();
        LOG.log(Level.INFO, "--------- PREFERRED USER NAME: {0}", ctx.getIdToken().getPreferredUsername());  
        
        AccessToken accessToken = principal.getKeycloakSecurityContext().getToken();
        LOG.log(Level.INFO, "--------- SCOPE: {0}", accessToken.getScope()); 
        
        //LOG.log(Level.INFO, "--------- TOKEN: {0}", ctx.getTokenString()); 
        return ctx.getTokenString();
        //return "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIxY2Y4ZFVWYWdJekN0ekM2eUZBcUpkOW1Va1hhZG13OVNUbTNTTHoyT1lBIn0.eyJleHAiOjE2MzczNTc1NDMsImlhdCI6MTYzNzM1NzI0MywianRpIjoiMDI3ZDA2MTQtMjAwNC00ZTRjLWJkMTQtNzBmOTBiYWUzZDU3IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTg1L2F1dGgvcmVhbG1zL3VyYmlzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6Ijc3YzdhNmZiLTQyM2QtNDAwZS05ODYzLTk0NDdmYjgwMzhmZiIsInR5cCI6IkJlYXJlciIsImF6cCI6InVyYmlzLWpzZiIsInNlc3Npb25fc3RhdGUiOiI2ZDg0NDMxOS1mYTcxLTQ3M2QtOTllNS03YjEyZjg5ODkxYTQiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtdXJiaXMiLCJSRUdJU1RSRV9SRUFEIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlVTRVIiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiI2ZDg0NDMxOS1mYTcxLTQ3M2QtOTllNS03YjEyZjg5ODkxYTQiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJsaXNhIHNpbXBzb24iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJsaXNhIiwiZ2l2ZW5fbmFtZSI6Imxpc2EiLCJmYW1pbHlfbmFtZSI6InNpbXBzb24iLCJlbWFpbCI6Imxpc2FAZ21haWwuY29tIn0.CZjOM-ovPjyU_s7SRRDRUU5cxi0GzW6f_Kr4P0WK1i_qH6bpXArPpqfEMnISCSHkd8UORu7nzH0h21ATXh2kJOat-D0cU3-p8jYY5ejR5LkBzEIjVoxnonBAEbhAkL7R0cwaqM5v68b7RbQzcbMXicVhj6h1dI7QfuffeNbvt210nFzbHRi2LdrZn6XvIpHsefnQfibzHm2b8tImfCx64gifTxutnkKeXMHbO7Hq5qBOUREE_n5NeaGyxqJx4oGLoMbvD7fvGN57EdAfCcFzfykSxFCtNE4aWf_AKKyd3NBPFc7FkY6k0836mQLYZ0j-TpyVNg3MDQW7VFkiqem7WA";
      
        
        
    }
    
}
