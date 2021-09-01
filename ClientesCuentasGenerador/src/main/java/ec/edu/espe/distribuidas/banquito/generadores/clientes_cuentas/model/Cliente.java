/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Admin
 */
@Data
public class Cliente {

    private String tipoIdentificacion;
    private String identificacion;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre1;
    private String nombre2;
    private String provincia;
    private String canton;
    private String parroquia;
    private String direccion;
    private String telefono;
    private String email;
    private Date fechaNacimiento;
    private String estadoCivil;
}
