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
 * @author jdismael
 */
@Data
public class Persona {

    private String id;
    private String identificacion;
    private String nombres;
    private String apellidos;
    private String genero;
    private String provincia;
    private String canton;
    private String parroquia;
    private String codigoDactilar;
    private String nombrePadre;
    private String nombreMadre;
    private String estadoCivil;
    private Date fechaNacimiento;
    
}
