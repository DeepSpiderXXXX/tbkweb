package com.limon.base.taobao;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.FalsifyingWebConnection;

public class NomalWebConnection extends FalsifyingWebConnection{
    public NomalWebConnection(WebClient webClient) throws IllegalArgumentException{
        super(webClient);
    }
    @Override
    public WebResponse getResponse(WebRequest request) throws IOException {
        return super.getResponse(request);
    }
}
