<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

<#if enableCache>
    <!-- 开启二级缓存 -->
    <cache type="${cacheClassName}"/>

</#if>
<#if baseResultMap>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
<#list table.fields as field>
<#if field.keyFlag><#--生成主键排在第一位-->
        <id column="${field.name}" property="${field.propertyName}" jdbcType="${field.metaInfo.jdbcType}"/>
</#if>
</#list>
<#list table.commonFields as field><#--生成公共字段 -->
        <result column="${field.name}" property="${field.propertyName}" jdbcType="${field.metaInfo.jdbcType}"/>
</#list>
<#list table.fields as field>
<#if !field.keyFlag><#--生成普通字段 -->
        <result column="${field.name}" property="${field.propertyName}" jdbcType="${field.metaInfo.jdbcType}"/>
</#if>
</#list>
    </resultMap>

</#if>
<#if baseColumnList>
    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
<#list table.commonFields as field>
        ${field.columnName},
</#list>
        ${table.fieldNames}
    </sql>

</#if>

<insert id="saveFull">
    <#list table.fields as field>
        <#if field.keyFlag>
    <selectKey keyProperty="${field.propertyName}" order="AFTER" resultType="${field.propertyType}">
        SELECT LAST_INSERT_ID()
    </selectKey>
        </#if>
    </#list>
    insert into ${table.name} (<#list table.fields as field><#if !field.keyFlag>${field.columnName}<#if field_has_next>,</#if></#if></#list>)
    values(
    <#list table.fields as field>
        <#if !field.keyFlag>
        <#noparse>#</#noparse>{${field.propertyName},jdbcType=${field.metaInfo.jdbcType}}<#if field_has_next>,</#if>
        </#if>
    </#list>
    )
</insert>

<insert id="save">
    <#list table.fields as field>
        <#if field.keyFlag>
    <selectKey keyProperty="${field.propertyName}" order="AFTER" resultType="${field.propertyType}">
        SELECT LAST_INSERT_ID()
    </selectKey>
        </#if>
    </#list>
    insert into ${table.name}
    <trim prefix="(" suffix=")" suffixOverrides=",">
    <#list table.fields as field>
    <#if !field.keyFlag>
        <if test="${field.propertyName} != null">${field.columnName},</if>
    </#if>
    </#list>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
    <#list table.fields as field>
    <#if !field.keyFlag>
        <if test="${field.propertyName} != null"><#noparse>#</#noparse>{${field.propertyName},jdbcType=${field.metaInfo.jdbcType}},</if>
    </#if>
    </#list>
    </trim>
</insert>

<update id="updateByPrimaryKey" parameterType="${package.Entity}.${entity}">
    update ${table.name}
    <set>
<#list table.fields as field>
    <#if !field.keyFlag>
        <if test="${field.propertyName} != null">
            ${field.columnName} = <#noparse>#</#noparse>{${field.propertyName},jdbcType=${field.metaInfo.jdbcType}},
        </if>
    </#if>
</#list>
    </set>
    where <#list table.fields as field><#if field.keyFlag>${field.columnName} = <#noparse>#</#noparse>{${field.propertyName},jdbcType=${field.metaInfo.jdbcType}}</#if></#list>
</update>
</mapper>
