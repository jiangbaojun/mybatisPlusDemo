package ${package.Mapper};

<#if mapperAnnotation>
import org.apache.ibatis.annotations.Mapper;
</#if>

/**
 * ${table.comment!}
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} {

}
</#if>
