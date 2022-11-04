package org.example;

import net.dongliu.requests.Requests;


import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws  InterruptedException {
//        ERNIE-ViLG跨模态文生图接口说明
//        String URL = "https://ai.baidu.com/ai-doc/wenxin/Zl33wtflg#ernie-vilg%E8%B7%A8%E6%A8%A1%E6%80%81%E6%96%87%E7%94%9F%E5%9B%BE%E6%9F%A5%E8%AF%A2%E7%BB%93%E6%9E%9C%E6%8E%A5%E5%8F%A3";

//   获取Access Token
        String tokenUrl = "https://wenxin.baidu.com/younger/portal/api/oauth/token";
        HashMap<String,String> Access_Token = new HashMap<>();
        Access_Token.put("grant_type", "client_credentials");
        Access_Token.put("client_id", "TOoeM9yhT6VsF0jxoiY2DGAuR5aGuiFu");
        Access_Token.put("client_secret", "RhV82hWsHXuqHggRhXr4ZTtZcrEQHCEu");

        String token = Requests.post(tokenUrl)
                .body(Access_Token)
                .send()
                .readToText();
        System.out.println(token);
// 创建请求头
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//提交请求接口
        String url1 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/txt2img";

        HashMap<String,String> params = new HashMap<>();
        params.put("access_token", token);
        params.put("text", "宁静的乡村");
        params.put("style", "油画");

        String resp1 = Requests.post(url1)
                .headers(headers)//使用请求头
                .body(params)//以form表单发送
                .send()//发送
                .readToText();//转化为String

        System.out.println(resp1);

        String taskId = null;
        for (String s : resp1.split(",")) {
            if (s.contains("taskId")) {
                String[] split = s.split(":");
                taskId = split[1].replaceAll("\\pP","");
            }
        }
        System.out.println("taskId："+taskId);
//查询结果接口
        String url2 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/getImg";

        Map<String, String> params1 = new HashMap<>();
        params1.put("access_token", token);
        params1.put("taskId", taskId);




        int time = 0;
        while (true) {
            System.out.println(time++);
            Thread.sleep(1000 * 5);
            String s = Requests.post(url2).headers(headers).body(params1).send().readToText();
            System.out.println(s);


            if (s.contains("status\":1")) {
                System.out.println("666");
                break;
            }
        }
        String resp2 = Requests.post(url2).headers(headers).body(params1).send().readToText();
        System.out.println(resp2);

    }
}