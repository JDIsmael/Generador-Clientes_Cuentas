/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.config;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Admin
 */
@Component
@Slf4j
@ToString
public class ApplicationValues {
    
    private final String mongoHost;
    private final String mongoDB;
    private final String mongoAut;
    private final String mongoUser;
    private final String mongoPassword;
    private final String configFile;
    private final String dataPath;
    
    @Autowired
    public ApplicationValues(@Value("${clientesCuentas.mongo.host}") String mongoHost,
            @Value("${clientesCuentas.mongo.db}") String mongoDB,
            @Value("${clientesCuentas.mongo.aut}") String mongoAut,
            @Value("${clientesCuentas.mongo.usr}") String mongoUser,
            @Value("${clientesCuentas.mongo.pwd}") String mongoPassword,
            @Value("${clientesCuentas.config.file}") String configFile,
            @Value("${clientesCuentas.config.dataPath}") String dataPath)
    {
        this.mongoHost = mongoHost;
        this.mongoDB = mongoDB;
        this.mongoAut = mongoAut;
        this.mongoUser = mongoUser;
        this.mongoPassword = mongoPassword;
        this.configFile = configFile;
        this.dataPath = dataPath;
        log.info("Propiedades cargadas: " + this.toString());
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public String getMongoDB() {
        return mongoDB;
    }

    public String getMongoAut() {
        return mongoAut;
    }

    public String getMongoUser() {
        return mongoUser;
    }

    public String getMongoPassword() {
        return mongoPassword;
    }

    public static Logger getLog() {
        return log;
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getDataPath() {
        return dataPath;
    }
    
}
