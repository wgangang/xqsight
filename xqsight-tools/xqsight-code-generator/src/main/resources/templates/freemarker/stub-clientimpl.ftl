<#include "copyright.ftl"/>
package ${basePackage}.${moduleName}.stub.feign.client.impl;

import com.billbear.common.response.PageData;
import ${basePackage}.${moduleName}.stub.bean.${table.className}DTO;
import ${basePackage}.${moduleName}.stub.feign.client.${table.className}Client;
import ${basePackage}.${moduleName}.stub.facade.${table.className}Feign;
import ${basePackage}.${moduleName}.stub.request.${table.className}Request;
import ${basePackage}.${moduleName}.stub.request.${table.className}SearchRequest;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>${table.remarks} client impl</p>
 *
 * @since ${.now}
 * @author generator
*/
@Component
public class ${table.className}ClientImpl implements ${table.className}Client {

    @Resource
    private ${table.className}Feign ${table.javaProperty}Feign;


    @Override
    public Boolean add(${table.className}Request request){
        return ${table.javaProperty}Feign.add(request).isSuccessGet();
    }

    @Override
    public Boolean upd(${table.className}Request request){
        return ${table.javaProperty}Feign.upd(request).isSuccessGet();
    }

    @Override
    public Boolean del(String id){
        return ${table.javaProperty}Feign.del(id).isSuccessGet();
    }

    @Override
    public Boolean delByIds(List<String> ids){
        return ${table.javaProperty}Feign.delByIds(ids).isSuccessGet();
    }

    @Override
    public ${table.className}DTO getById(String id){
        return ${table.javaProperty}Feign.getById(id).isSuccessGet();
    }

    @Override
    public PageData<${table.className}DTO> search(${table.className}SearchRequest request){
        return ${table.javaProperty}Feign.search(request).isSuccessGet();
    }

    @Override
    public List<${table.className}DTO> list(${table.className}SearchRequest request){
        return ${table.javaProperty}Feign.list(request).isSuccessGet();
    }

    @Override
    public List<${table.className}DTO> listByIds(List<String> ids){
        return ${table.javaProperty}Feign.listByIds(ids).isSuccessGet();
    }
}