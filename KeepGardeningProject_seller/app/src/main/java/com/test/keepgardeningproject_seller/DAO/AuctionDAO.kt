package com.test.keepgardeningproject_seller.DAO

import android.provider.ContactsContract.CommonDataKinds.Nickname

class AuctionDAO {
}

data class AuctionClass(
    var auctionIdx: Long,
    var auctionProductIdx: Long,
    var auctionBidNickname: String,
    var auctionBidPrice: String,
    var auctionCustomerIdx: Long
)