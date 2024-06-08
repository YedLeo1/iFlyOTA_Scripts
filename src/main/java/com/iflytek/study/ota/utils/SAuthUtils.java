package com.iflytek.study.ota.utils;

import com.iflytek.study.ota.ApiConstant;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.security.MessageDigest;


public class SAuthUtils {

    private static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String MD5Generate(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes can not be null");
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5Utils.MD5);
            messageDigest.update(bArr);
            byte[] digest = messageDigest.digest();
            char[] cArr = new char[digest.length * 2];
            int i = 0;
            for (byte b : digest) {
                int i2 = i + 1;
                cArr[i] = digits[(b >>> 4) & 15];
                i = i2 + 1;
                cArr[i2] = digits[b & 15];
            }
            return new String(cArr);
        } catch (NoSuchAlgorithmException unused) {
            throw new IllegalArgumentException("unknown algorithm MD5");
        }
    }

    private static String ParseContents(Map<String, String> map) {
        TreeMap<String, String> treeMap = new TreeMap();
        if (map == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().startsWith(ApiConstant.API_CA_HEADER_TO_SIGN_PREFIX_SYSTEM)) {
                treeMap.put(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry entry2 : treeMap.entrySet()) {
            sb.append((String) entry2.getKey());
            sb.append(":");
            if (entry2.getValue() != null) {
                sb.append((String) entry2.getValue());
            }
            sb.append(ApiConstant.LF);
        }
        return sb.toString();
    }


    public static String GenerateSign(String str, String str2, Map<String, String> map, String str3) {
        if (str3 == null) {
            throw new IllegalArgumentException("AppSecret must not be null");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(ApiConstant.LF);
        if (map.get("Accept") != null) {
            sb.append(map.get("Accept"));
        }
        sb.append(ApiConstant.LF);
        if (map.get("Content-MD5") != null) {
            sb.append(map.get("Content-MD5"));
        }
        sb.append(ApiConstant.LF);
        if (map.get("Content-Type") != null) {
            sb.append(map.get("Content-Type"));
        }
        sb.append(ApiConstant.LF);
        sb.append(map.get("Date"));
        sb.append(ApiConstant.LF);
        sb.append(ParseContents(map));
        sb.append(str2);
        String sb2 = sb.toString();
        return MD5Generate((sb2 + str3).getBytes());
    }


//    private Request buildRequest(String group, String api) {
//        String str;
//        String str2;
//        String valueOf;
//        String sb;
//        String httpProtocol = "http";
//        String method = internalApiRequest.getMethod();
//        int compressionMode = internalApiRequest.getCompressionMode();
//        int encryptionMode = internalApiRequest.getEncryptionMode();
//        int parseMode = internalApiRequest.getParseMode();
//        String version = "1.0";
//        Stage stage = internalApiRequest.getStage();
//        String specialHost = internalApiRequest.getSpecialHost();
//        String str3 = internalApiRequest.isMock() ? "true" : "false";
//        byte[] serializeData = serializeData(internalApiRequest);
//        StringBuilder sb2 = new StringBuilder();
//        sb2.append(DripApiClient.this.baseUrl);
//        String sb3 = sb2.toString();
//        HashMap hashMap = new HashMap();
//        if (DripApiClient.this.diffTime != 0) {
//            str = httpProtocol;
//            str2 = method;
//            valueOf = String.valueOf(SystemClock.elapsedRealtime() + DripApiClient.this.diffTime);
//        } else {
//            str = httpProtocol;
//            str2 = method;
//            valueOf = String.valueOf(System.currentTimeMillis());
//        }
//        internalApiRequest.setTimeStamp(valueOf);
//        hashMap.put("Host", "api.hardware.xfinfr.com");
//        hashMap.put("Date", getHttpDateHeaderValue(valueOf));
//        hashMap.put(ApiConstant.API_X_CA_TIMESTAMP, valueOf);
//        hashMap.put("User-Agent", ApiConstant.USER_AGENT);
//        if (parseMode == 0) {
//            hashMap.put("Content-Type", ApiConstant.CONTENT_TYPE_PB);
//            hashMap.put("Accept", ApiConstant.CONTENT_TYPE_PB);
//        } else {
//            hashMap.put("Content-Type", ApiConstant.CONTENT_TYPE_JSON);
//            hashMap.put("Accept", ApiConstant.CONTENT_TYPE_JSON);
//        }
//        hashMap.put(ApiConstant.API_X_CA_STAGE, "RELEASE");
//        hashMap.put(ApiConstant.API_X_CA_VERSION, version);
//        hashMap.put(ApiConstant.API_X_CA_APPID, DripApiClient.this.appId);
//        hashMap.put(ApiConstant.API_X_CA_ACCESSKEY, DripApiClient.this.accessKey);
//        hashMap.put(ApiConstant.API_X_CA_NONCE, UUID.randomUUID().toString());
//        if (internalApiRequest.isMerge()) {
//            List<SubRequest> requestDataList = ((ApiBatchRequest) internalApiRequest.getData()).getRequestDataList();
//            hashMap.put(ApiConstant.API_X_CA_API, ApiConstant.API_BATCH);
//            StringBuilder sb4 = new StringBuilder();
//            for (int i = 0; i < requestDataList.size(); i++) {
//                SubRequest subRequest = requestDataList.get(i);
//                if (i != 0) {
//                    sb4.append(",");
//                }
//                sb4.append(subRequest.getApi());
//            }
//            hashMap.put(ApiConstant.API_X_CA_BATCH_APIS, sb4.toString());
//        } else {
//            if (group != null) {
//                hashMap.put(ApiConstant.API_X_CA_GROUP, group);
//            }
//            if (api != null) {
//                hashMap.put(ApiConstant.API_X_CA_API, api);
//            }
//        }
//        if (specialHost != null) {
//            hashMap.put("Host", specialHost);
//        }
//        hashMap.put(ApiConstant.API_X_CA_MOCK, str3);
//        byte[] encryptAndGzip = encryptAndGzip(encryptionMode, compressionMode, serializeData, valueOf);
//        if (encryptAndGzip != null) {
//            hashMap.put("Content-MD5", MD5Generate(encryptAndGzip));
//        }
//        String str4 = str2;
//        if (str4.equals("GET") || str4.equals("DELETE") || str4.equals("HEAD")) {
//            String c = encryptAndGzip == null ? null : com.iflytek.drip.apigateway.a.a.a.a.c(encryptAndGzip);
//            sb2.append(ApiConstant.SEPARATOR);
//            sb2.append(c);
//            sb = sb2.toString();
//        } else {
//            sb = sb3;
//        }
//        Map<String, String> headers = internalApiRequest.getHeaders();
//        if (headers != null && !headers.isEmpty()) {
//            hashMap.putAll(headers);
//        }
//        String pathFromUrl = DripApiClient.this.getPathFromUrl(sb);
//        hashMap.put(ApiConstant.API_X_CA_SIGNATURE, GenerateSign(str4, pathFromUrl, hashMap, DirtyApiClient.ACCESS_KEY));
//        hashMap.put(ApiConstant.API_X_CA_SIGNATURE_HEADERS, buildSignatureHeaders(hashMap));
//        Request createRequest = createRequest(sb, str, str4, hashMap, encryptAndGzip);
//        return createRequest;
//    }

}
