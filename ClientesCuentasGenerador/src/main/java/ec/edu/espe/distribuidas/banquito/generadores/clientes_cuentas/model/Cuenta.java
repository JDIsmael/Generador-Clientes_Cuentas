/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model;

import java.security.Timestamp;
import java.time.LocalDateTime;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Cuenta {
    
    private String codigoProductoPasivo;
    private String codigoCliente;
    private LocalDateTime fechaCreacion;
}
