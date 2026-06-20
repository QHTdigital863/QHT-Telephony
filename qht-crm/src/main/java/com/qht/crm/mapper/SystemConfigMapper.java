package com.qht.crm.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.qht.crm.entity.SystemConfig;
import com.qht.crm.entity.dto.SystemConfigDTO;



@Mapper(componentModel = "spring")
public interface SystemConfigMapper {


    SystemConfigDTO mapSystemConfigToDTO(SystemConfig systemConfig);
    SystemConfig mapDTOToSystemConfig(SystemConfigDTO systemConfigDTO);
    
    @Mapping(target = "id",ignore = true)
	void updateSystemConfigToSystemConfig(SystemConfigDTO source, @MappingTarget SystemConfig target);
	
}