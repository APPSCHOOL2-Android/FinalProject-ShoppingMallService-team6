package com.test.keepgardeningproject_customer.UI.MyPageCustomerQnA

import android.icu.lang.UCharacter.VerticalOrientation
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.test.keepgardeningproject_customer.DAO.MypageQnAData
import com.test.keepgardeningproject_customer.DAO.MypageQnADetailData

import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.R
import com.test.keepgardeningproject_customer.UI.HomeCustomerMain.HomeCustomerMainFragment
import com.test.keepgardeningproject_customer.databinding.ActivityMainBinding
import com.test.keepgardeningproject_customer.databinding.FragmentMyPageCustomerQnABinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerQnaBinding
import com.test.keepgardeningproject_customer.databinding.RowMyPageCustomerReviewBinding
import org.w3c.dom.Text

class MyPageCustomerQnAFragment : Fragment() {

    companion object {
        fun newInstance() = MyPageCustomerQnAFragment()
    }

    private lateinit var viewModel: MyPageCustomerQnAViewModel

    lateinit var binding: FragmentMyPageCustomerQnABinding

    lateinit var mainActivity: MainActivity

    lateinit var newBundle:Bundle

    lateinit var homeCustomerMainFragment: HomeCustomerMainFragment

    var MypageQnADetail = MypageQnADetailData(1,"아이언맨 나이?","아이언맨은 52세입니다.",3.5f)

    var MypageQnAData = MypageQnAData(true,"마블","어벤져스",MypageQnADetail)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyPageCustomerQnABinding.inflate(inflater)

        mainActivity = activity as MainActivity

        /*homeCustomerMainFragment = HomeCustomerMainFragment()
        homeCustomerMainFragment.mainActivity = activity as MainActivity*/

        val view = binding.root

        binding.run{

            materialToolbarQc.run{

                title = "문의 내역"

                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)

                setNavigationOnClickListener {

                    mainActivity.removeFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_FRAGMENT)

                }

            }

            MyPageCustomerQuestionRecyclerView.run{

                adapter = QuestionRecyclerViewAdapter(MypageQnAData)
                layoutManager = LinearLayoutManager(context)

            }


        }


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPageCustomerQnAViewModel::class.java)
        // TODO: Use the ViewModel
    }

    inner class QuestionRecyclerViewAdapter(val Qna: MypageQnAData) :
        RecyclerView.Adapter<QuestionRecyclerViewAdapter.QuestionViewHolder>() {
        inner class QuestionViewHolder(rowCustomerQuestionBinding: RowMyPageCustomerQnaBinding) :
            RecyclerView.ViewHolder(rowCustomerQuestionBinding.root) {

            val ProductName: TextView
            val StoreName: TextView
            val Question: TextView
            val state:TextView
            val moveBtnQc : ImageButton

            init {
                ProductName = rowCustomerQuestionBinding.textviewQcProductName
                StoreName = rowCustomerQuestionBinding.textviewQcStoreName
                Question = rowCustomerQuestionBinding.textviewQcComment
                state = rowCustomerQuestionBinding.textviewQcReplyState
                moveBtnQc = rowCustomerQuestionBinding.imageButtonQcDetail
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val rowCustomerQuestionBinding = RowMyPageCustomerQnaBinding.inflate(layoutInflater)
            val ViewHolder = QuestionViewHolder(rowCustomerQuestionBinding)

            rowCustomerQuestionBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.ProductName.text = Qna.productName
            holder.StoreName.text = Qna.storeName
            holder.Question.text = Qna.content.titleQnA

            /*if(Qna.replyState){

                holder.state.text = "답변완료"

            }
            else{

                holder.state.text = "미답변"

            }*/

            holder.itemView.setOnClickListener {

                val imageResourceId = R.drawable.img_orchid

                newBundle = Bundle().apply{

                    putFloat("contentRating",Qna.content.starNumbers)
                    putInt("contentImage",imageResourceId)
                    putString("contentTitle",Qna.content.titleQnA)
                    putString("contentQnA",Qna.content.contentQnA)

                }

                mainActivity.replaceFragment(MainActivity.MY_PAGE_CUSTOMER_QNA_DETAIL_FRAGMENT,true,newBundle)

            }


        }
    }

}