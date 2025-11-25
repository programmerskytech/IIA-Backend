package com.astro.repository.InventoryModule;

import com.astro.entity.InventoryModule.OgpAssetDisposalDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OgpAssetDisposalDetailRepository extends JpaRepository<OgpAssetDisposalDetail,Integer> {
 //   List<OgpAssetDisposalDetail> findByDisposalOgpId(Integer disposalOgpId);

    @Query("SELECT d FROM OgpAssetDisposalDetail d WHERE d.disposal.disposalOgpId = :disposalOgpId")
    List<OgpAssetDisposalDetail> findByDisposalOgpId(@Param("disposalOgpId") Integer disposalOgpId);


}
