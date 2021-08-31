/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas;

import ch.qos.logback.core.net.server.Client;
import javax.ws.rs.client.WebTarget;
import org.aopalliance.intercept.Invocation;
import com.google.gson.Gson;
import io.micrometer.core.ipc.http.HttpSender.Response;
import javax.ws.rs.client.ClientBuilder;
import org.springframework.batch.core.Entity;

/**
 *
 * @author Admin
 */
public class ClienteApi extends Thread {

    private String res = "";
    private String URL = "http://localhost:8080/api/registrocivil/generador/";
    private ListadoClientes clientes;

    public ClienteApi(ListadoClientes clientes) {
        this.clientes = clientes;
    }

    public void send() {

    }

    @Override
    public void run() {
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URL);
            Invocation.Builder solicitud = target.request();
            Gson gson = new Gson();
            String jsonString = gson.toJson(this.clientes);
            Response post = solicitud.post(Entity.json(jsonString));
            String responseJson = post.readEntity(String.class);
            res = responseJson;
            
            System.out.println("Estatus: " + post.getStatus());

        } catch (Exception e) {
            res = e.toString();
        }
        System.out.println(res); 
    }

}
