package com.example.Humosoft.Mapper;

import com.example.Humosoft.DTO.Request.TaskRequest;
import com.example.Humosoft.DTO.Response.TaskResponse;
import com.example.Humosoft.Model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    Task toTask(TaskRequest request);
    TaskResponse toTaskResponse(Task task);
    void updateTask(@MappingTarget Task task, TaskRequest request);
}
