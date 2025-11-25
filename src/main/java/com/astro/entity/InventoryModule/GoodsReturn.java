package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "goods_return")
public class GoodsReturn {

    @Id
    @Column(name = "goods_return_id")
    private String goodsReturnId;

    @Column(name = "goods_return_note_no")
    private String goodsReturnNoteNo;

    @Column(name = "rejected_quantity")
    private Integer rejectedQuantity;

    @Column(name = "return_quantity")
    private Integer returnQuantity;

    @Column(name = "type_of_return")
    private String typeOfReturn;

    @Column(name = "reason_of_return")
    private String reasonOfReturn;

    private String updatedBy;
    private String createdBy;

    private LocalDateTime createdDate = LocalDateTime.now();

    private LocalDateTime updatedDate = LocalDateTime.now();

}
