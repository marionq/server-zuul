package mx.com.autofin.zuul.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


/**
 * Clase Manejador mensajes de errores durante autenticacion
 * y autorizacion
 * 
 * @author MNQ
 * @version 1.0
 *
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler{
    
    public RestAccessDeniedHandler() {
        super();
    }
    
    private static final String MESSAGE_ERROR = "Access Denied";

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), MESSAGE_ERROR);
    }
}