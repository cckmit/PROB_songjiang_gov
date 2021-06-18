package com.enesource.jump.web.common;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;

@Configuration
public class DruidConfig {
	
	@Autowired
	private Environment env;
	
	@Value("${spring.datasource.url}")
	private String 		dbUrl;
	@Value("${spring.datasource.username}")
    private String 		username;
	@Value("${spring.datasource.password}")
    private String 		password;
	@Value("${spring.datasource.driverClassName}")
    private String 		driverClassName;
    
    @Value("${spring.druid.initialSize}")
    private int 		initialSize;
    @Value("${spring.druid.minIdle}")
    private int 		minIdle;
    @Value("${spring.druid.maxActive}")
    private int 		maxActive;
    @Value("${spring.druid.maxWait}")
    private int 		maxWait;
    @Value("${spring.druid.timeBetweenEvictionRunsMillis}")
    private int 		timeBetweenEvictionRunsMillis;
    @Value("${spring.druid.minEvictableIdleTimeMillis}")
    private int 		minEvictableIdleTimeMillis;
    @Value("${spring.druid.validationQuery}")
    private String 		validationQuery;
    @Value("${spring.druid.testWhileIdle}")
    private boolean 	testWhileIdle;
    @Value("${spring.druid.testOnBorrow}")
    private boolean 	testOnBorrow;
    @Value("${spring.druid.testOnReturn}")
    private boolean 	testOnReturn;
    @Value("${spring.druid.poolPreparedStatements}")
    private boolean 	poolPreparedStatements;
    @Value("${spring.druid.maxPoolPreparedStatementPerConnectionSize}")
    private int 		maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.druid.filters}")
    private String 		filters;
    @Value("${spring.druid.connectionProperties}")
    private String 		connectionProperties;
	
	@Bean(name = "dataSource")     //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() throws SQLException {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

        datasource.setFilters(filters);

        return datasource;
    }
    
	@Bean
    public ServletRegistrationBean registrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());        //添加初始化参数：initParams
        servletRegistrationBean.addUrlMappings("/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", "");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
    
    
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean bean=new SqlSessionFactoryBean();
        bean.setDataSource(dataSource());
        
        // 加入分页插件
        PageHelper pageHelper = new PageHelper();  
        Properties props = new Properties();  
        props.setProperty("reasonable", "true");  
        props.setProperty("supportMethodsArguments", "true");  
        props.setProperty("returnPageInfo", "check");  
        props.setProperty("params", "count=countSql");  
        props.setProperty("dialect", "mysql");
        pageHelper.setProperties(props);  
        //添加插件  
        bean.setPlugins(new Interceptor[]{pageHelper});  
        
        bean.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapperLocations")));
        
        
        return bean;
    }
    
    @Bean  
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {  
        return new SqlSessionTemplate(sqlSessionFactory);  
    }

    @Bean  
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		try {
			return new DataSourceTransactionManager(dataSource());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
