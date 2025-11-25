package com.astro.service.InventoryModule;

import com.astro.dto.workflow.InventoryModule.grv.GrvDto;
import java.util.Map;

public interface GrvService {
    String saveGrv(GrvDto req);
    Map<String, Object> getGrvDtls(String processNo);
}