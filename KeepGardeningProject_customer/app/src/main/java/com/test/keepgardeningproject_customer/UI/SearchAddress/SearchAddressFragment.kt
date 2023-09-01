package com.test.keepgardeningproject_customer.UI.SearchAddress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.test.keepgardeningproject_customer.MainActivity
import com.test.keepgardeningproject_customer.databinding.FragmentSearchAddressBinding

class SearchAddressFragment : Fragment() {
    lateinit var fragmentSearchAddressBinding: FragmentSearchAddressBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSearchAddressBinding = FragmentSearchAddressBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentSearchAddressBinding.run {
            webViewSearchAddress.run {
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

        return fragmentSearchAddressBinding.root
    }

    inner class BridgeInterface(private val mainActivity: MainActivity) {
        @JavascriptInterface
        fun processDATA(data: String?) {
            // 다음 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달됨
            if (data != null) {
                mainActivity.address = data!!
            }
            mainActivity.removeFragment(MainActivity.SEARCH_ADDRESS_FRAGMENT)
        }
    }
}