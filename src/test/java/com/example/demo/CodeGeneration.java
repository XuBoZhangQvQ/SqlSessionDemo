package com.example.demo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxubo
 * @ClassName: CodeGeneration
 * @Description: 代码生成器
 * @date 2021年08月06日16:16:23
 */
public class CodeGeneration {

    /**
     * 生成实体类和相应的Mapper
     */
    public static void main(String[] args) {

        //作者
        String auth = "ZhangXuBo";
        //表名，多个英文逗号分割
        String tableName = "user";


        String packagePath = "com.example.demo.common";
        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java/";

        //指定生成文件的绝对路径
        Map<String, String> pathInfo = new HashMap<>();
        String entityPath = projectPath.concat("/src/main/java/com/example/demo/common/entity");
        String mapper_path = projectPath.concat("/src/main/java/com/example/demo/common/mapper");
        String xml_path = projectPath.concat("/src/main/resources/com/example/demo/common/mapper");
        pathInfo.put(ConstVal.ENTITY_PATH, entityPath);
        pathInfo.put(ConstVal.MAPPER_PATH, mapper_path);
        pathInfo.put(ConstVal.XML_PATH, xml_path);

        // 代码生成器
        new AutoGenerator(new DataSourceConfig
                .Builder(
                "jdbc:mysql://127.0.0.1/datasource1",
                "root",
                "123456")
                .typeConvert(new MySqlTypeConvert())
                .build()
        )
                .global(new GlobalConfig.Builder()
                        .outputDir(outputDir)
                        .author(auth)
                        .openDir(false)
                        .dateType(DateType.ONLY_DATE)
                        .fileOverride()
                        .build()
                )
                .packageInfo(new PackageConfig.Builder()// 包配置
                        .parent(packagePath)
                        .pathInfo(pathInfo)
                        .build()
                ).template(
                        new TemplateConfig.Builder()
                                .controller("")//关闭controller生成
                                .service("", "")//关闭service生成
                                .build()
                )
                .strategy(new StrategyConfig.Builder()// 策略配置
                        .enableCapitalMode()// 全局大写命名
                        .addInclude(tableName.split(","))
                        .enableSkipView() //跳过视图


                        .entityBuilder()
                        .enableLombok()// lombok 模型
                        .enableChainModel() // 链式操作
                        .enableSerialVersionUID()
                        .idType(IdType.ASSIGN_ID)//全局主键类型
                        .addTableFills(new Column("created_time", FieldFill.INSERT))
                        .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                        .naming(NamingStrategy.underline_to_camel)
                        .enableTableFieldAnnotation()
                        .mapperBuilder()
                        .enableBaseResultMap()
                        .enableBaseColumnList()
                        .build()
                )
                .execute();
    }
}
