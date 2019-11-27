package com.example.nosqlfinalhomework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
@Configuration
public class SpringBootMongoDBConfig {
    @Configuration
    public class SpringMongoConfig {
        @Autowired
        private MongoDbFactory mongoDbFactory;

        @Autowired
        private MongoMappingContext mongoMappingContext;

        @Bean
        public MappingMongoConverter mappingMongoConverter() {
            DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory);
            MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
            //此处是去除插入数据库的 _class 字段
            converter.setTypeMapper(new DefaultMongoTypeMapper(null));

            return converter;
        }
    }

}
