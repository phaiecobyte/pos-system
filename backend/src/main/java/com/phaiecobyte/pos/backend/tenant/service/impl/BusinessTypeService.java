package com.phaiecobyte.pos.backend.tenant.service.impl;

import com.phaiecobyte.pos.backend.tenant.model.BusinessType;
import com.phaiecobyte.pos.backend.tenant.repository.BusinessTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessTypeService {
    private final BusinessTypeRepository businessTypeRepository;

    public List<BusinessType> list(){
        return businessTypeRepository.findAll();
    }

    public BusinessType create(BusinessType businessType){
        return businessTypeRepository.save(businessType);
    }

    public BusinessType update(BusinessType req){
        BusinessType businessType = new BusinessType();
        businessType.setDescription(req.getDescription());
        return businessTypeRepository.save(businessType);
    }
}
