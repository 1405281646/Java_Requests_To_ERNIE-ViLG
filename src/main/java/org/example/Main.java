package org.example;

import net.dongliu.requests.Requests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

//   获取Access Token
        String tokenUrl = "https://wenxin.baidu.com/younger/portal/api/oauth/token";
        Map Access_Token = new HashMap<>();
        Access_Token.put("grant_type", "client_credentials");
        Access_Token.put("client_id", "TOoeM9yhT6VsF0jxoiY2DGAuR5aGuiFu");
        Access_Token.put("client_secret", "RhV82hWsHXuqHggRhXr4ZTtZcrEQHCEu");

        String token = Requests.post(tokenUrl).params(Access_Token).send().readToText();
        System.out.println(token);

        Map headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String url1 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/txt2img";

        Map<String, String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("text", "宁静的乡村");
        params.put("style", "油画");

//        String resp1 = Requests.post(url1).headers(headers).body(params).send().readToText();

//        System.out.println(resp1);


        String url2 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/getImg";

        Map<String, String> params1 = new HashMap<>();
        params1.put("taskId", "7537844");  params1.put("access_token", token);


//Thread.sleep(1000*120 );


        boolean flag=true;
        while (flag){
            Thread.sleep(1000);
            int statusCode = Requests.post(url2).headers(headers).body(params1).send().getStatusCode();
            System.out.println(statusCode);
            if (statusCode==200){
                flag=false;
            }
        }
        String resp2 = Requests.post(url2).headers(headers).body(params1).send().readToText();
        System.out.println(resp2);

    }
}