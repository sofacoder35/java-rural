package com.sofa.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.service.ApiKey;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 */
@Component
@Slf4j
public class BaiduBceUtils {


    @Value("${baidubce.apiKey}")
    private String apiKey;

    @Value("${baidubce.secretKey}")
    private String secretKey;

    @Value("${qiniu.baseUrl}")
    private String baseUrl;

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Secret Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;

            }
            /**
             * 返回结果示例
             */
//            System.err.println("result:" + result);
            JSONObject jsonObject = JSON.parseObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public Object baiduClassify(String result) {
        JSONObject jsonObject=JSON.parseObject(result);
        String imageUrl=jsonObject.getString("data");
        imageUrl=baseUrl+imageUrl;
        log.info("base64图片--{}",imageUrl);
        log.info("accessToken--{}",this.getAuth(apiKey,secretKey));
        String path = "https://aip.baidubce.com/rest/2.0/image-classify/v1/plant";
        ApiExplorerRequest request = new ApiExplorerRequest(HttpMethodName.POST, path);
        // 设置header参数
        request.addHeaderParameter("Content-Type", "application/x-www-form-urlencoded");
        request.addQueryParameter("access_token", this.getAuth(apiKey,secretKey));
            // 设置jsonBody参数
        String jsonBody = "url="+imageUrl;
        request.setJsonBody(jsonBody);
        ApiExplorerClient client = new ApiExplorerClient();
        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
//               System.out.println(response.getResult());
            log.info("返回结果{}",response.getResult());
            JSONObject jsonObject2=JSON.parseObject(response.getResult());
            return jsonObject2;
        } catch (Exception e) {
            log.info("error");
            return null;
        }

    }


}
