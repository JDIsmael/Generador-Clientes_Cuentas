/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.tasks;

import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.config.ApplicationValues;
import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model.Cliente;
import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model.Cuenta;
import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.model.Persona;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Admin
 */
@Slf4j
public class GeneracionClientesCuentas implements Tasklet, StepExecutionListener {

    private final ApplicationValues applicationValues;
    private final String DIRECCION_FILE = "dir.txt";
    private RestTemplate restTemplate = new RestTemplate();
   
    private Integer clientes;
    private Integer porcentajeGanaDiario;
    private Integer porcentajeTarjetaCredito;
    
    private String tipoIdentificacion[] = {"CED", "PAS"};
    private String dominioEmail[] = {"@hotmail.com", "@outlook.com", "@espe.edu.ec"
            , "@gmail.com", "@yahoo.com", "@live.com"};
    
    private List<String> direcciones = new ArrayList<>();

    public GeneracionClientesCuentas(ApplicationValues applicationValues) {
        this.applicationValues = applicationValues;
    }

    @Override
    public void beforeStep(StepExecution se) {
        try {
            
            Path fileDirecciones = Paths.get(this.applicationValues.getDataPath() + this.DIRECCION_FILE);
           this.direcciones = Files.readAllLines(fileDirecciones);
            
            ExecutionContext sc = se.getJobExecution().getExecutionContext();
            this.clientes = (Integer) sc.get("clientes");
            this.porcentajeGanaDiario = (Integer) sc.get("porcentGanaDiario");
            this.porcentajeTarjetaCredito = (Integer) sc.get("porcentTajetaCredito");
            log.info("Va a generar {} clientes", this.clientes);
            
            log.info("{} porcentaje cuentas Gana Diario", this.porcentajeGanaDiario);
            log.info("{} porcentaje cuentas Tarjeta de Credito", this.porcentajeTarjetaCredito);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {

        ExecutionContext exc = cc.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
        this.clientes = (Integer) exc.get("clientes");
        log.info("Nº Clientes en execute: {}", this.clientes);

        Random r = new Random();
        List<String> ids = new ArrayList<>();
        boolean check;
        Persona persona;

        for (int i = 0; i < this.clientes; i++) {
           do {
                check = false;
                persona = restTemplate.getForObject("http://34.135.165.97:8001/api/registrocivil/ramdon/", Persona.class);
                
                //log.info("persona: {}",persona);
                for (String id : ids)
                    if (id.equals(persona.getIdentificacion())) {
                        log.info("repetido: {}",persona);
                        check = true;
                        break;
                    }
            } while (check);
            
            ids.add(persona.getIdentificacion());
            
            Cuenta cuenta = new Cuenta();
            Cliente cliente = new Cliente();
            String nombres[] = persona.getNombres().split(" ");
            String apellidos[] = persona.getApellidos().split(" ");
            
            cliente.setTipoIdentificacion(this.tipoIdentificacion[r.nextInt(2)]);
            cliente.setIdentificacion(persona.getIdentificacion());
            
            cliente.setApellidoPaterno(apellidos[0]);
            cliente.setApellidoMaterno(apellidos[1]);
            
            cliente.setNombre1(nombres[0]);
            cliente.setNombre2(nombres[1]);
            
            cliente.setProvincia(persona.getProvincia());
            cliente.setCanton(persona.getCanton());
            cliente.setParroquia(persona.getParroquia());
            
            cliente.setDireccion(this.direcciones.get(r.nextInt(this.direcciones.size())));
            
            cliente.setTelefono("09" + RandomUtils.nextInt(10000001, 99999999));
            cliente.setEmail(cliente.getApellidoPaterno().substring(0, 2).toUpperCase()
                    .concat(cliente.getNombre1().toLowerCase())
                    .concat(this.dominioEmail[r.nextInt(this.dominioEmail.length)]));
            
            cliente.setFechaNacimiento(persona.getFechaNacimiento());
            cliente.setEstadoCivil(persona.getEstadoCivil());
            
            
            log.info("cliente: {}",cliente);
            
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://34.135.165.97:8002/api/cliente/return/", cliente, String.class);
            log.info("insertado id: {}",responseEntity.getBody());
            cuenta.setCodCliente(responseEntity.getBody());
            
            
            cuenta.setCodProductoPasivo("GanaDiario");
            
            
            
          
        }
       
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        return ExitStatus.COMPLETED;
    }
}
