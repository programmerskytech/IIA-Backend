package com.astro.repository.InventoryModule;

import com.astro.entity.PaymentVoucherMaterials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PaymentVoucherMaterialsRepository extends JpaRepository<PaymentVoucherMaterials, Long> {
    PaymentVoucherMaterials findByMaterialCodeAndPaymentVoucherId(String materialCode, Long paymentVoucherId);

    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM PaymentVoucherMaterials m " +
            "JOIN m.paymentVoucher pv " +
            "WHERE pv.grnNumber = :grnNumber AND m.materialCode = :materialCode")
    BigDecimal getTotalReceivedQuantity(@Param("grnNumber") String grnNumber,
                                        @Param("materialCode") String materialCode);

}
