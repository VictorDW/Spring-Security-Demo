package com.springsecurity.practica.Domain.Materials.Service;

import com.springsecurity.practica.Domain.Materials.DTO.MaterialsRequest;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsResponse;
import com.springsecurity.practica.Domain.Materials.DTO.MaterialsUpdate;

import java.util.List;

public interface IMaterialsService{

    MaterialsResponse create(MaterialsRequest request);
    List<MaterialsResponse> getMaterialsByCode(String code);
    List<MaterialsResponse> getAll();
    MaterialsResponse update(MaterialsUpdate update);
    void delete(Long id);
}
