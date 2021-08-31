package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientesCuentasApplication {

    @Autowired
    JobLauncher jobLauncher;
    
    @Autowired
    @Qualifier("generadorClientesCuentas")
    Job jobs;
    
	public static void main(String[] args) {
		SpringApplication.run(ClientesCuentasApplication.class, args);
	}

}
