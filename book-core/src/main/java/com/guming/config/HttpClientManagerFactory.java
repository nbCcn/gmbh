package com.guming.config;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: PengCheng
 * @Description: HttpClient封装对象
 * @Date: 2018/4/27
 */
@Component
public class HttpClientManagerFactory implements FactoryBean<CloseableHttpClient>, InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(HttpClientManagerFactory.class);

    private CloseableHttpClient client;

    @Autowired
    private RequestConfig requestConfig;

    @Autowired
    private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    @Autowired
    private HttpRequestRetryHandler httpRequestRetryHandler;

    @Override
    public void destroy() throws Exception {
        /*
         * 调用httpClient.close()会先shut down connection manager，然后再释放该HttpClient所占用的所有资源，
         * 关闭所有在使用或者空闲的connection包括底层socket。由于这里把它所使用的connection manager关闭了，
         * 所以在下次还要进行http请求的时候，要重新new一个connection manager来build一个HttpClient,
         * 也就是在需要关闭和新建Client的情况下，connection manager不能是单例的.
         */
        if(null != this.client){
            this.client.close();
        }
    }

    @Override
    public CloseableHttpClient getObject() throws Exception {
        return this.client;
    }

    @Override
    public Class<?> getObjectType() {
        return (this.client == null ? CloseableHttpClient.class : this.client.getClass());
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /*
         * 建议此处使用HttpClients.custom的方式来创建HttpClientBuilder，而不要使用HttpClientBuilder.create()方法来创建HttpClientBuilder
         * 从官方文档可以得出，HttpClientBuilder是非线程安全的，但是HttpClients确实Immutable的，immutable 对象不仅能够保证对象的状态不被改变，
         * 而且还可以不使用锁机制就能被其他线程共享
         *
         *  .setRetryHandler(httpRequestRetryHandler)    重试处理
		 *	.setKeepAliveStrategy(connectionKeepAliveStrategy)  保持策略
         *	.setRoutePlanner(proxyRoutePlanner)         路由策略
         */
        this.client = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager)
                //重试策略
                .setRetryHandler(httpRequestRetryHandler)
                .setDefaultRequestConfig(requestConfig)
                /*
                 *	.setKeepAliveStrategy(connectionKeepAliveStrategy)  保持策略
                 *	.setRoutePlanner(proxyRoutePlanner)         路由策略
                 */
                .build();
    }


    /**
     * get方法
     * @param url
     * @return
     */
    public String httpGet(String url){
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response =  getObject().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("",e);
        }
        return null;
    }

    /**
     * post请求
     * @param url
     * @param paramsJson     携带的参数Json
     * @param encoding        编码
     * @return
     */
    public String httpPost(String url,String paramsJson,String encoding){
        String result = "";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(paramsJson, encoding));
        try {
            CloseableHttpResponse response =getObject().execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(entity,encoding);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            logger.error("",e);
        }
        return result;
    }
}
