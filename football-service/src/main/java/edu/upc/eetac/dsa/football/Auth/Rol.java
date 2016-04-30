package edu.upc.eetac.dsa.football.Auth;

import javax.ws.rs.core.SecurityContext;

/**
 * Created by mbmarkus on 30/04/16.
 */
public class Rol {

    /**
     * Función que comprueba el permiso para ejecutar recursos.
     * @param idusuario
     * @param securityContext
     * @return
     */

    public boolean permisoAdmin_Usuario(String idusuario, SecurityContext securityContext ){

        String userid = securityContext.getUserPrincipal().getName();
        boolean resultado = false;

        if (userid.equals(idusuario))
            resultado=true;
        if (securityContext.isUserInRole("admin"))
            resultado=true;

        return resultado;
    }
}
