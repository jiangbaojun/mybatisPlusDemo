package com.example.demo.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BaojunJiang
 * @date 2022-08-09 10:37
 */
public class GeneratorPlus {

    public static void main1(String[] args) throws IOException, TemplateException {
        //1.创建freemarker的配置对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //2.设置模板文件所在目录
        configuration.setDirectoryForTemplateLoading(new File("D:/work/myProject/learn/mybatisPlusDemo/src/main/resources"));
        //3.设置字符集
        configuration.setDefaultEncoding("utf-8");
        //4.加载模板
        Template template = configuration.getTemplate("mapper.xml.ftl");
        //5.准备模板文件中所需的数据，一般通过map构造
        Map<String,Object> map = new HashMap<>();
        map.put("table",new HashMap());
        map.put("package",new HashMap());
        map.put("name","panghl");
        map.put("message","你好啊！！！freemarker");
        //6.创建Writer对象,用于输出静态文件
        Writer out = new FileWriter(new File("C:/Users/BaojunJiang/Desktop/test.ftl"));
        //7.输出文件
        template.process(map,out);
        //8.关闭流
        out.close();
    }

    public static void main(String[] args) {
//        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:3306/db1?allowMultiQueries=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
//                        "root", "12345678")
        FastAutoGenerator.create("jdbc:mysql://dev-03.cluster-c9qe4y0vrvda.rds.cn-northwest-1.amazonaws.com.cn:3306/eclinical_admin?allowMultiQueries=true&rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8",
                        "root", "8YTJWOuA7XRK17wRQnw4")
                .globalConfig(builder -> {
                    builder.author("jiangbaojun") // 设置作者
                            .dateType(DateType.ONLY_DATE)
                            .outputDir("C:\\Users\\BaojunJiang\\Desktop\\123"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    Map<OutputFile, String> outputFileMap = new HashMap<>();
                    outputFileMap.put(OutputFile.xml, "C:\\Users\\BaojunJiang\\Desktop\\123");
                    //OutputFile.other自定义模板输出路径
                    outputFileMap.put(OutputFile.other, "C:\\Users\\BaojunJiang\\Desktop\\123\\others");
                    builder.parent("com.test") // 设置父包名
                            .moduleName("system") // 设置父包模块名
                            .pathInfo(outputFileMap);
                })
                .templateConfig(builder -> {
                    //指定xml、service、controller等模板文件。模板load根路径是/，所以此处要写相对resources文件夹的路径
                    builder.xml("mapper.xml");
                    builder.entity("entity.java");
                    //builder.service("service模板文件路径");
                })
                .strategyConfig(builder -> builder
//                        .addInclude("eclinical_admin_limitation") // 设置需要生成的表名
                        .likeTable(new LikeTable("eclinical_admin_limitation%")) // 设置过滤表前缀
                        .entityBuilder().fileOverride().enableLombok().enableTableFieldAnnotation().idType(IdType.AUTO)
                        .mapperBuilder().fileOverride().enableBaseResultMap().enableBaseColumnList())
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .injectionConfig(builder -> {
                    //除了标准的xml、mapper、service等文件生成。此处指定的是自定义的模板，产生的文件
                    builder.customMap(Collections.singletonMap("aa","aa_param"))
                            .customFile(Collections.singletonMap("test1.xml", "/test.xml.ftl"))
                            .build();
                })
                .execute();
    }
}
