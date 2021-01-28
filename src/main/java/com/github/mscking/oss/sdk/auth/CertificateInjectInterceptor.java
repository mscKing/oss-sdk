package com.github.mscking.oss.sdk.auth;

import com.github.mscking.oss.common.util.HmacUtil;
import com.github.mscking.oss.common.util.RandomUtil;
import com.github.mscking.oss.rpc.constant.OssParamConstant;
import com.github.mscking.oss.rpc.util.ParamSignUtil;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

/**
 * @author miaosc
 * @date 2020/10/2 0002
 */
public class CertificateInjectInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String appKey;

    private String appSecret;

    private HmacUtil hmacUtil;

    public CertificateInjectInterceptor(String appKey, String appSecret) {
        //设置在发送请求前阶段进行拦截
        super(Phase.PREPARE_SEND);
        this.appKey = appKey;
        this.appSecret = appSecret;
        hmacUtil = HmacUtil.buildHmacSHA1(appSecret);
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        List<Header> headers = soapMessage.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElementNS("http://rpc.oss.wecarry.com/authorization", "SecurityHeader");
        Element authorizationEle = doc.createElement(OssParamConstant.AUTH);
        Element timestampEle = doc.createElement(OssParamConstant.TIMESTAMP);
        Element randomEle = doc.createElement(OssParamConstant.RANDOM);
        long timestamp = Instant.now().toEpochMilli();
        String random = RandomUtil.generate8String();
        String timestampString = String.valueOf(timestamp);
        timestampEle.setTextContent(timestampString);
        randomEle.setTextContent(random);
        //计算hmac的值
        String cred = this.hmacUtil.mac2Hex(ParamSignUtil.buildParamString(random, timestamp).getBytes(StandardCharsets.UTF_8));
        authorizationEle.setTextContent(String.format("%s%s:%s", OssParamConstant.AUTH_VALUE_PREFIX, appKey, cred));
        auth.appendChild(timestampEle);
        auth.appendChild(authorizationEle);
        auth.appendChild(randomEle);
        headers.add(new Header(new QName("SecurityHeader"), auth));
    }


}
