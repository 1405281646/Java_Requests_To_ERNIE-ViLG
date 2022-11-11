package org.example;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ERNIE_ViLG_by_OKHTTP {
    private static final String ak = "TOoeM9yhT6VsF0jxoiY2DGAuR5aGuiFu";
    private static final String sk = "RhV82hWsHXuqHggRhXr4ZTtZcrEQHCEu";
    public static OkHttpClient client = new OkHttpClient();
    private static String token;
    private static String taskId;

    public static void main(String[] args) throws IOException, InterruptedException {

        String t = "宁静的乡村", s = "二次元";
        drawByERNIE_ViLG(t, s);
    }

    private static void drawByERNIE_ViLG(String t, String s) throws IOException, InterruptedException {
        initToken(ak, sk);
        submitRequest(t, s);
        queryResult();
    }

    private static void queryResult() throws IOException, InterruptedException {
        String url2 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/getImg";

        FormBody formBody2 = new FormBody.Builder().add("access_token", token).add("taskId", taskId).build();
        Request request2 = new Request.Builder().url(url2).post(formBody2).build();
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

    private static void submitRequest(String text, String style) throws IOException {
        String url1 = "https://wenxin.baidu.com/younger/portal/api/rest/1.0/ernievilg/v1/txt2img";
        FormBody formBody1 = new FormBody.Builder().add("access_token", token).add("text", text).add("style", style).build();
        Request request1 = new Request.Builder().url(url1).post(formBody1).build();
        Response response1 = client.newCall(request1).execute();
        String str = response1.body().string();

        for (String s : str.split(",")) {
            if (s.contains("taskId")) {
                String[] split = s.split(":");
                taskId = split[1].replaceAll("\\pP", "");
            }
        }
    }

    private static void initToken(String ak, String sk) throws IOException {
        String tokenUrl = "https://wenxin.baidu.com/younger/portal/api/oauth/token";

        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder().add("grant_type", "client_credentials").add("client_id", ak).add("client_secret", sk).build();
        Request request = new Request.Builder().url(tokenUrl).post(formBody).build();
        Response response = client.newCall(request).execute();
        token = response.body().string();
    }


}
