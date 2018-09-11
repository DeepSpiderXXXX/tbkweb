package com.limon.base.taobao;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;

public class InterceptWebConnection extends FalsifyingWebConnection{
    public InterceptWebConnection(WebClient webClient) throws IllegalArgumentException{
        super(webClient);
    }
    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        WebResponse response=super.getResponse(request);
        if(response.getWebRequest().getUrl().toString().indexOf("g.alicdn.com")!=-1){
            return createWebResponse(response.getWebRequest(), "", "application/javascript", 200, "Ok");
        }
        return super.getResponse(request);
    }
}
