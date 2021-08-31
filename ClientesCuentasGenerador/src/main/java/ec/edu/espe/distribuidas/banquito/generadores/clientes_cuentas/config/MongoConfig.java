/*
 * Copyright (c) 2021 Admin.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Admin - initial API and implementation and/or initial documentation
 */
package ec.edu.espe.distribuidas.banquito.generadores.clientes_cuentas.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.math.BigDecimal;
import java.util.Arrays;
import org.bson.types.Decimal128;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 *
 * @author Admin
 */
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    
    @Autowired
    private ApplicationValues applicationValues;
    
    @Override
    protected String getDatabaseName() {
        return applicationValues.getMongoDB();
    }

    @Override
    public MongoClient mongoClient() {
        if("1".equals(applicationValues.getMongoAut())){
            return MongoClients.create("mongodb://" + applicationValues.getMongoUser() + ":" + applicationValues + "8"
                    + applicationValues.getMongoHost() + "/" + applicationValues.getMongoDB() + "?authSource=admin");
        }else{
            return MongoClients.create("mongodb://" + applicationValues.getMongoHost() + "/" + applicationValues.getMongoDB());
        }
     
    }

    @Override
    public boolean autoIndexCreation() {
        return true;
    }

    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory databaseFactory, MongoCustomConversions customConversions, MongoMappingContext mappingContext) {
        MappingMongoConverter converter = super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

    /**
     * Inject a CustomConversions bean to overwrite the default mapping of BigDecimal.
     *
     * @return a new instance of CustomConversons
     */
    @Bean
    @Override
    public MongoCustomConversions customConversions() {
        Converter<Decimal128, BigDecimal> decimal128ToBigDecimal = new Converter<>() {
            @Override
            public BigDecimal convert(Decimal128 s) {
                return s == null ? null : s.bigDecimalValue();
            }
        };

        Converter<BigDecimal, Decimal128> bigDecimalToDecimal128 = new Converter<>() {
            @Override
            public Decimal128 convert(BigDecimal s) {
                return s == null ? null : new Decimal128(s);
            }
        };

        return new MongoCustomConversions(Arrays.asList(decimal128ToBigDecimal, bigDecimalToDecimal128));
    }
}
