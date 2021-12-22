import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.*;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.experimental.Accessors;
import org.junit.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GodeTest {

    @Autowired
    private RedissonClient redissonClient;
    @Test
    public void test(){
        System.out.println(redissonClient);
    }

//        // 代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 2、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setOutputDir("/Users/kevintam/project/movie_parent/movie_service/movie_film" + "/src/main/java");
//        gc.setAuthor("kevintam");
//        gc.setOpen(false); //生成后是否打开资源管理器
//        gc.setFileOverride(false); //重新生成时文件是否覆盖
//        gc.setServiceName("%sService"); //去掉Service接口的首字母I
//        gc.setIdType(IdType.ID_WORKER); //主键策略
//        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
//        gc.setSwagger2(true);//开启Swagger2模式
//        mpg.setGlobalConfig(gc);
//// 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/movie_film?serverTimezone=GMT%2B8");
//        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("kevintam");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//// 4、包配置
//        PackageConfig pc = new PackageConfig();
//        pc.setModuleName("moviefilm"); //模块名
//        pc.setParent("com.serookie");
//        pc.setController("controller");
//        pc.setEntity("entity");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        mpg.setPackageInfo(pc);
//// 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        strategy.setInclude("movie");
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的
//        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段
//        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain =
//        strategy.setRestControllerStyle(true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
//        mpg.setStrategy(strategy);
//// 6、执行
//        mpg.execute();

}
