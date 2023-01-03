package util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//JSON工具类，用于将请求内容转换为JSON对象
public class JsonUtils {
    /**
     * 获取Request中的JSON字符串
     */
    public static String getRequestPostStr(HttpServletRequest request) throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        assert buffer != null;
        return new String(buffer, charEncoding);
    }

    public static JSONObject getRequestPostJson(HttpServletRequest request) throws IOException {
        String jsonStr = getRequestPostStr(request);
        System.out.println("接收到的json数据：" + jsonStr);
        return JSONObject.parseObject(jsonStr);
    }

    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte[] buffer = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readLen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readLen == -1) {
                break;
            }
            i += readLen;
        }
        return buffer;
    }


    /*
      JSON对象转为网址传参格式（按key的首字母从小到大排序）
      @param jsonObject
     * @return
     */
//    public static String json2pathValue(JSONObject jsonObject)  {
//        Map map = (Map) jsonObject.toJavaObject(Map.class);
//        Set<String> set = map.keySet();
//        List<String> keyList = new ArrayList<>(set);
//        List<String> collect = keyList.stream().sorted().collect(Collectors.toList());
//        StringBuilder stringBuilder = new StringBuilder();
//        for (String s : collect) {
//            String value =map.get(s).toString();
//            stringBuilder.append(s).append("=").append(value).append("&");
//        }
//        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
//        return stringBuilder.toString();
//    }
}
