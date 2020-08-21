package android.googd.x5webview;

import android.text.TextUtils;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import java.io.ByteArrayInputStream;

public class WebViewClient extends com.tencent.smtt.sdk.WebViewClient {

    private String[] gg={
            "assets/xiwang.js?v0108",
            "pic.windtch.com",
            "daikuanfanli.com"
    };

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        String url = webResourceRequest.getUrl().toString();
        WebResourceResponse webResourceResponse=null;
        for (int i=0;gg.length>i;i++) {
            if (url.contains(gg[i])) {
               webResourceResponse= new WebResourceResponse("text/html", "utf-8", new ByteArrayInputStream("广告".getBytes()));
            } else {
                webResourceResponse=super.shouldInterceptRequest(webView, webResourceRequest);
            }
        }
        return webResourceResponse;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if( url.startsWith("http:") || url.startsWith("https:") ) {
            WebView.HitTestResult hitTestResult = view.getHitTestResult();
            //hitTestResult==null解决重定向问题
            if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                view.loadUrl(url);
                return true;
            }
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

}
