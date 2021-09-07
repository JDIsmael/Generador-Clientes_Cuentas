/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.tasks;

import ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.config.ApplicationValues;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

/**
 *
 * @author Admin
 */
@Slf4j
public class LeerCondiciones implements Tasklet, StepExecutionListener{
    
    private final ApplicationValues applicationValues;

    public LeerCondiciones(ApplicationValues applicationValues) {
        this.applicationValues = applicationValues;
    }

    @Override
    public void beforeStep(StepExecution se) {
   
    }
    
    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        
        log.info("Va a ejecutar la tarea leer condiciones");
        log.info("El archivo con condiciones es: {}", this.applicationValues.getConfigFile());
        Properties props = new Properties();
        try {
            
            Path path = Path.of(this.applicationValues.getConfigFile());
            props.load(new FileInputStream(this.applicationValues.getConfigFile()));

            Integer clientes;
            Integer porcentajeGanaDiario;
            Integer porcentajeTarjetaCredito;
            Integer monthMin;
            Integer yearMin;
            
            try {
                clientes = Integer.parseInt(props.getProperty("clientes"));
                porcentajeGanaDiario = Integer.parseInt(props.getProperty("porcentajeClientesGanaDiario"));
                porcentajeTarjetaCredito = Integer.parseInt(props.getProperty("porcentajeClientesTarjetaCredito"));
                monthMin = Integer.parseInt(props.getProperty("monthMin"));
                yearMin = Integer.parseInt(props.getProperty("yearMin"));
                log.info("Va a generar {} clientes", clientes);
                log.info("{} porcentaje cuentas Gana Diario", porcentajeGanaDiario);
                log.info("{} porcentaje cuentas Tarjeta de Credito", porcentajeTarjetaCredito);
                
                ExecutionContext jobContext = cc.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
                jobContext.put("clientes", clientes);
                jobContext.put("porcentGanaDiario", porcentajeGanaDiario);
                jobContext.put("porcentTajetaCredito", porcentajeTarjetaCredito);
                jobContext.put("monthMin", monthMin);
                jobContext.put("yearMin", yearMin);
                
            } catch (NumberFormatException e) {
                log.error("Invalid values");
            }

        } catch (IOException e) {
            log.error("Propertie file does not exists");
        }
        
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        log.info("Finalizo la ejecucion");
        return ExitStatus.COMPLETED;
    }
}
