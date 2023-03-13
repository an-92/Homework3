package com.example.boarder.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.object.SqlFunction;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "com.example.board.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {

    private final String MAPPER_XML_PATH;   //mapper.xml 경로를 읽을 PATH 지정

    //생성자로 주입할 때 매개 변수 앞에 @Value 를 작성, 이렇게 불변성을 유지하고 생성자 주입을 통해 클래스가 필요할 때 값을 주입받도록 할 수 있습니다.
    public DataSourceConfig(@Value("${mybatis.mapper-locations}") String MAPPER_XML_PATH) {
        this.MAPPER_XML_PATH = MAPPER_XML_PATH;
    }

    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource DataSource() {
        return DataSourceBuilder.create().build();
    }

    //xml 의 위치를 지정한 SqlSessionFactory 를 빈으로 설정해줍니다.
    //@Qualifier("dataSource") → 의존성 주입 시 지정한 Bean 을 우선적으로 찾아서 맵핑 시켜주는 역할을 합니다.
    @Bean(name = "SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource DataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(DataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources(MAPPER_XML_PATH)); //resources 에서의 위치
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.boarder.dto");

        return sqlSessionFactoryBean.getObject();
    }

    //템플릿 지정
    @Bean(name = "SessionTemplate")
    public SqlSessionTemplate SqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory firstSqlSessionFactory) {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }
}
