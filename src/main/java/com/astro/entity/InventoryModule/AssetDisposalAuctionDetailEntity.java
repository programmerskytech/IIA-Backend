package com.astro.entity.InventoryModule;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "asset_disposal_auction_detail")
@Data
public class AssetDisposalAuctionDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_detail_id")
    private Integer auctionDetailId;

    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false)
    private AssetDisposalAuctionEntity auction;

    @Column(name = "disposal_id", nullable = false)
    private Integer disposalId;
}
