package com.test.keepgardeningproject_customer.UI.AuctionCustomer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.keepgardeningproject_customer.DAO.AuctionProductInfo
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.Repository.ProductRepository
import com.test.keepgardeningproject_customer.databinding.FragmentAuctionCustomerBinding
import com.test.keepgardeningproject_customer.databinding.RowAuctionCustomerBinding
import java.text.DecimalFormat

class AuctionCustomerFragment : Fragment() {

    lateinit var fragmentAuctionCustomerBinding : FragmentAuctionCustomerBinding
    lateinit var mainActivity : MainActivity
    
    lateinit var viewModel : AuctionCustomerViewModel
    
    // 불러온 전체 리스트
    var APInfoList = mutableListOf<AuctionProductInfo>()
    var APImageList = mutableListOf<String>()
    
    // 지금 리싸이클러뷰에 보여줄 리스트
    var rpl = mutableListOf<AuctionProductInfo>()
    var ril = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAuctionCustomerBinding = FragmentAuctionCustomerBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        
        // 뷰모델 초기화
        viewModel = ViewModelProvider(mainActivity)[AuctionCustomerViewModel::class.java]
        viewModel.run{
            auctionProductInfoList.observe(mainActivity){
                APInfoList = it
                rpl = APInfoList
                fragmentAuctionCustomerBinding.recyclerAuctionCustomer.adapter?.notifyDataSetChanged()
            }
            auctionProductImageNameList.observe(mainActivity){
                APImageList = it
                ril = APImageList
                fragmentAuctionCustomerBinding.recyclerAuctionCustomer.adapter?.notifyDataSetChanged()
            }
        }
        viewModel.getAuctionProductInfoAll()

        fragmentAuctionCustomerBinding.run{
            // 툴바
            toolbarAuctionCustomer.run{
                title = "경매중인 상품"
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.AUCTION_CUSTOMER_FRAGMENT)
                }
            }

            // 검색창
            searchViewAc.run{
                queryHint = "검색어를 입력해주세요"
                setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        //검색어 입력 순간마다의 이벤트...
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        //키보드에서 검색 버튼을 누르는 순간의 이벤트
                        val resultList = mutableListOf<AuctionProductInfo>()
                        val resultImageFileNameList = mutableListOf<String>()
                        for(ap in APInfoList){
                            if(ap.auctionProductName?.contains(query) == true){
                                resultList.add(ap)
                                resultImageFileNameList.add(ap.auctionProductImageList?.get(0)!!)
                            }
                        }
                        rpl = resultList
                        ril = resultImageFileNameList
                        fragmentAuctionCustomerBinding.recyclerAuctionCustomer.adapter?.notifyDataSetChanged()

                        return true
                    }
                })
            }

            // 경매상품목록
            recyclerAuctionCustomer.run{
                adapter = RAAC()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        return fragmentAuctionCustomerBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAuctionProductInfoAll()
        fragmentAuctionCustomerBinding.recyclerAuctionCustomer.adapter?.notifyDataSetChanged()
    }

    inner class RAAC : RecyclerView.Adapter<RAAC.VHAC>() {
        inner class VHAC(rowAuctionCustomerBinding: RowAuctionCustomerBinding) : RecyclerView.ViewHolder(rowAuctionCustomerBinding.root){
            var imageviewAc : ImageView
            var textviewAcTitle : TextView
            var textViewAcPrice : TextView
            var textViewAcStore : TextView

            init{
                imageviewAc = rowAuctionCustomerBinding.imageviewAc
                textviewAcTitle = rowAuctionCustomerBinding.textviewAcTitle
                textViewAcPrice = rowAuctionCustomerBinding.textViewAcPrice
                textViewAcStore = rowAuctionCustomerBinding.textViewAcStore

                // 클릭시 개별상품 detail로 이동
                rowAuctionCustomerBinding.root.setOnClickListener {
                    val selectedAuctionProductIdx = rpl[adapterPosition].auctionProductIdx!!
                    MainActivity.chosedAuctionProductIdx = selectedAuctionProductIdx
                    mainActivity.replaceFragment(MainActivity.AUCTION_CUSTOMER_DETAIL_FRAGMENT,true,null)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHAC {
            val rowAuctionCustomerBinding = RowAuctionCustomerBinding.inflate(layoutInflater)
            val vhac = VHAC(rowAuctionCustomerBinding)

            return vhac
        }

        override fun getItemCount(): Int {
            return rpl.size
        }

        override fun onBindViewHolder(holder: VHAC, position: Int) {
            // 이미지
            holder.imageviewAc.setImageResource(R.drawable.ic_launcher_background)

            var fileName = ril[position]
            ProductRepository.getProductImage(fileName){
                val fileUri = it.result
                Glide.with(mainActivity).load(fileUri).into(holder.imageviewAc)
            }
            
            // 상품명
            holder.textviewAcTitle.text = rpl[position].auctionProductName
            
            // 가게명
            holder.textViewAcStore.text = rpl[position].auctionProductOpenDate + " 입찰시작"



            // 경매시작가
            var decimal = DecimalFormat("#,###")
            var temp = rpl[position].auctionProductOpenPrice!!.toInt()
            holder.textViewAcPrice.text = "경매 시작가 : " + decimal.format(temp) + " 원"
        }
    }
}