package com.sofa.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baidubce.http.ApiExplorerClient;
import com.baidubce.http.HttpMethodName;
import com.baidubce.model.ApiExplorerRequest;
import com.baidubce.model.ApiExplorerResponse;
import com.sofa.vo.RoadVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MapUtils {

    @Value("${tencent.mapKey}")
    private String mapKey;

    public Object findRoad(RoadVo roadVo){
        String path="https://apis.map.qq.com/ws/direction/v1/driving/?from="+roadVo.getFromLatitude()+","+roadVo.getFromLongitude()
                +"&to="+roadVo.getToLatitude()+","+roadVo.getToLongitude()+"&output=json&callback=cb&key="+mapKey;

//        String path="https://apis.map.qq.com/ws/direction/v1/driving/?from=39.915285,116.403857&to=39.915285,116.803857&waypoints=39.111,116.112;39.112,116.113&output=json&callback=cb&key=B7RBZ-XLME6-O5FS4-E3MF3-ALWTF-TYBZG";
        ApiExplorerRequest request=new ApiExplorerRequest(HttpMethodName.GET,path);
        ApiExplorerClient client = new ApiExplorerClient();
        try {
            ApiExplorerResponse response = client.sendRequest(request);
            // 返回结果格式为Json字符串
               System.out.println(response.getResult());
            log.info("返回结果{}",response.getResult());
            JSONObject jsonObject2= JSON.parseObject(response.getResult());
            return jsonObject2;
        } catch (Exception e) {
            log.info("error");
            return null;
        }
    }
}
