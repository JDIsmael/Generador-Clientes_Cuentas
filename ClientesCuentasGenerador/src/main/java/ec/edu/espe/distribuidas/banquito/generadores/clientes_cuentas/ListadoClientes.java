/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas;

import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class ListadoClientes {
    
    private List<Cliente> clientes;
    
    public ListadoClientes() {
        this.clientes = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.clientes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ListadoClientes other = (ListadoClientes) obj;
        if (!Objects.equals(this.clientes, other.clientes)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ListadoClientes{" + "cliente = " + clientes + '}';
    }
    
}
