package com.test.keepgardeningproject_seller.UI.SearchAddress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.test.keepgardeningproject_seller.MainActivity
import com.test.keepgardeningproject_seller.databinding.FragmentSearchAddressBinding


class SearchAddressFragment : Fragment() {


    lateinit var searchAddressBinding: FragmentSearchAddressBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
       searchAddressBinding  = FragmentSearchAddressBinding.inflate(layoutInflater)
        mainActivity  = activity as MainActivity

        searchAddressBinding.run {
            webviewAddress.run {
                settings.javaScriptEnabled = true
                addJavascriptInterface(BridgeInterface(mainActivity), "Android")
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        // Android -> Javascript 함수 호출
                        view?.loadUrl("javascript:sample2_execDaumPostcode();")
                    }
                }

                // 검색 사이트 로드
                loadUrl("https://searchaddress-98c4c.web.app/")
            }
        }
        return searchAddressBinding.root
    }
    inner class BridgeInterface(private val mainActivity: MainActivity){
        @JavascriptInterface
        fun processDATA(data:String?){
            if(data!=null){
                mainActivity.postAddress = data!!
            }
            mainActivity.removeFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT)
        }

    }

}