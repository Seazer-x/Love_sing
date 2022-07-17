package com.boop.love_sing.common.config;


import com.qingstor.sdk.config.EnvContext;
import com.qingstor.sdk.exception.QSException;
import com.qingstor.sdk.service.Bucket;
import com.qingstor.sdk.service.QingStor;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class QinglongUtil {

    private static final String accessKey="NIEXQCSSJUYLRICPZHOY";
    private static final String secretKey="9MAz5fFqGYdhQz63Hg6BnGF2dPeiKBfAXsZdbHz0";

    private static final EnvContext env = new EnvContext(accessKey, secretKey);
    private static final QingStor stor = new QingStor(env);
    private static final String zoneName = "sh1a";
    private static final Bucket bucket = stor.getBucket("love-sing", zoneName);

    public static String putObject(String key,InputStream body) throws QSException {
        Bucket.PutObjectInput input = new Bucket.PutObjectInput();
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] data = body.readAllBytes();
            String contentMD5 = Base64.getEncoder().encodeToString(md5.digest(data));
            input.setContentMD5(contentMD5);
            body = new ByteArrayInputStream(data);
            input.setBodyInputStream(body);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new QSException("上传失败！");
        }
        Bucket.PutObjectOutput output = bucket.putObject(key, input);
        System.out.println(output.getStatueCode());
        if (output.getStatueCode() == 201) {
            return output.getETag()+"===="+output.getRequestId();
        } else {
            return output.getCode() + ":" + output.getMessage()+"==="+output.getRequestId();
        }
    }

}
