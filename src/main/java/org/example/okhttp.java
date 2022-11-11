package org.example;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class okhttp {

    public static void main(String[] args) throws IOException, InterruptedException {
        String tokenUrl = "https://wenxin.baidu.com/younger/portal/api/oauth/token";

        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "TOoeM9yhT6VsF0jxoiY2DGAuR5aGuiFu")
                .add("client_secret", "RhV82hWsHXuqHggRhXr4ZTtZcrEQHCEu")
                .build();
        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        String tokenString = response.body().string();

        String url1 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/txt2img";
        FormBody formBody1 = new FormBody.Builder()
                .add("access_token", tokenString)
                .add("text", "宁静的乡村")
                .add("style", "油画")
                .build();
        Request request1 = new Request.Builder()
                .url(url1)
                .post(formBody1)
                .build();
        Response response1 = client.newCall(request1).execute();
        String str= response1.body().string();        String taskId = null;

        for (String s : str.split(",")) {
            if (s.contains("taskId")) {
                String[] split = s.split(":");
                taskId = split[1].replaceAll("\\pP","");
            }
        }
        System.out.println(str);

        String url2 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/getImg";

        FormBody formBody2 = new FormBody.Builder()
                .add("access_token", tokenString)
                .add("taskId", taskId)
                .build();
        Request request2 = new Request.Builder()
                .url(url2)
                .post(formBody2)
                .build();


        int time = 0;
        String s;
        while (true) {
            System.out.println(time++);
            Thread.sleep(1000 * 5);
            Response response2 = client.newCall(request2).execute();

            s = response2.body().string();
            System.out.println(s);

            if (s.contains("status\":1")) {
                System.out.println("666");
                break;
            }
        }

        System.out.println(s);
    }


}


