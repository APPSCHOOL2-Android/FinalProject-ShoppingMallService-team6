package com.test.keepgardeningproject_customer.UI.MyPageCustomerPurchase


import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerPurchaseButtonBinding
import java.lang.RuntimeException

class MyPageCustomerPurchaseFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentMyPageCustomerPurchaseBinding: FragmentMyPageCustomerPurchaseBinding


    //구매내역에 들어갈 가짜 데이터
    val list = listOf(
        Model(Model.DELIVERY_TYPE,"배송완료","몬스테라",R.mipmap.ic_launcher),
        Model(Model.DELIVERY_TYPE,"배송완료","장미",R.mipmap.ic_launcher),
        Model(Model.PAYMENT_TYPE,"결제완료","개나리",R.mipmap.ic_launcher),
        Model(Model.PAYMENT_TYPE,"결제완료","미나리",R.mipmap.ic_launcher),
        Model(Model.DELIVERY_TYPE,"배송완료","해바라기",R.mipmap.ic_launcher)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentMyPageCustomerPurchaseBinding = FragmentMyPageCustomerPurchaseBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentMyPageCustomerPurchaseBinding.run {
            recyclerviewPc.run {
                adapter = ResultRecyclerviewAdapter(list)
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(MaterialDividerItemDecoration(context, MaterialDividerItemDecoration.VERTICAL))
            }
            toolbarPc.run {
                setTitle("구매내역")
                setNavigationIcon(R.drawable.ic_back_24px)
                setNavigationOnClickListener {
                    //마이페이지 메인화면으로 이동
                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_PURCHASE_FRAGMENT)
                }
            }
        }


        return fragmentMyPageCustomerPurchaseBinding.root
    }


    inner class ResultRecyclerviewAdapter(private val list:List<Model>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           val view :View?
            //결제완료 및 배송완료 분기처리
            return when(viewType){
                Model.DELIVERY_TYPE->{
                    view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_page_customer_purchase_button,parent,false)
                    DeliveryTypeViewHolder(view)

                }
                Model.PAYMENT_TYPE->{
                    view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_page_customer_purchase,parent,false)
                    PaymentTypeViewHolder(view)

                }
                else ->throw RuntimeException("뷰 타입 에러")
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val obj = list[position]
            when (obj.type) {
                Model.PAYMENT_TYPE -> {
                    (holder as PaymentTypeViewHolder).txtPtname.text = obj.title
                    holder.txtPtstate.text = obj.state
                    holder.imageviewPtimg.setImageResource(obj.data)
                    holder.itemView.setOnClickListener {
                        //사용자가 선택한 index를 번들에 담아 뿌려준다
                        mainActivity.replaceFragment(MainActivity.ORDER_CHECK_FORM_CUSTOMER_FRAGMENT,true,null)
                    }

                }
                Model.DELIVERY_TYPE -> {
                    (holder as DeliveryTypeViewHolder).txtDyname.text = obj.title
                    holder.txtDystate.text = obj.state
                    holder.imageviewDyimg.setImageResource(obj.data)
                    holder.butoonDyreview.setText("리뷰쓰기")
                    holder.butoonDyreview.setOnClickListener {
                        //사용자가 선택한 index를 번들에 담아서 뿌려준다
                        mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_REVIEW_WRITE_FRAGMENT,true,null)
                    }
                }
            }
        }


        // 여기서 받는 position은 데이터의 index다.
        override fun getItemViewType(position: Int): Int {
            return list[position].type
        }

        //뷰홀더에 들어가 아이템 설정
        inner class PaymentTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtPtname: TextView = itemView.findViewById(R.id.textView_pc_productName)
            val txtPtstate :TextView = itemView.findViewById(R.id.textview_pc_state)
            val imageviewPtimg :ImageView = itemView.findViewById(R.id.imageview_pc_img)
        }

        inner class DeliveryTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val txtDyname: TextView = itemView.findViewById(R.id.textView_pc_productName2)
            val txtDystate :TextView = itemView.findViewById(R.id.textview_pc_state2)
            val imageviewDyimg :ImageView = itemView.findViewById(R.id.imageview_pc_img2)
            val butoonDyreview : Button = itemView.findViewById(R.id.button_pc_review2)
        }

    }

}

//구매내역 model
data class Model(val type:Int, val state:String,val title:String,val data:Int){
    companion object{
        const val DELIVERY_TYPE = 0
        const val PAYMENT_TYPE = 1
    }
}