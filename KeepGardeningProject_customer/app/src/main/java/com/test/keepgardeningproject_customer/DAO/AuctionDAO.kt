package com.test.keepgardeningproject_customer.DAO

class AuctionDAO {
}

data class AuctionInfo(
    var auctionIdx:Long,
    var auctionAuctionProductIndex:Long?,
    var auctionCustomerIdx:Long?,

    var auctionBidNickname : String?,
    var auctionBidPrice : String?,
    )
//이미지, 상태, 타이틀, index


data class AuctionProductInfo(
    var auctionProductIdx : Long?,
    var auctionProductName : String?,
    var auctionProductDetail : String?,
    var auctionProductStoreIdx : Long?,

    var auctionProductOpenPrice : String?,
    var auctionProductOpenDate : String?,
    var auctionProductCloseDate : String?,

    var auctionProductImageList : ArrayList<String>?
)


//구매자 경매 데이터클래스
data class auctionCustomerDetail(var name:String,var img:String,var state:String,var price:String,var productidx:Long)

