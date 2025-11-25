package com.astro.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astro.dto.workflow.LocatorMasterResDto;
import com.astro.entity.LocatorMasterEntity;
import com.astro.repository.LocatorMasterRepository;
import com.astro.service.LocatorService;

@Service
public class LocatorServiceImpl implements LocatorService{

    @Autowired
    private LocatorMasterRepository lmr;

    @Override
    public List<LocatorMasterResDto> getLocatorMaster() {
        List<LocatorMasterEntity> lmeList = lmr.findAll();

        return lmeList.stream().map(lme -> {
            LocatorMasterResDto lmrDto = new LocatorMasterResDto();
            lmrDto.setValue(lme.getLocatorId());
            lmrDto.setLabel(lme.getLocatorDesc());
            lmrDto.setLocationId(lme.getLocationId());
            return lmrDto;
        }).collect(Collectors.toList());
    }
    
}
