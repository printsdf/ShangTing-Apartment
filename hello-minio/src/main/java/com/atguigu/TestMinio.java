package com.atguigu;

import edu.princeton.cs.algs4.ST;
import io.minio.*;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TestMinio {

    public static void main(String[] args) {

        String endpoint = "http://192.168.249.101:9000";
        String accessKey = "minioadmin";
        String secretKey = "minioadmin";
        String bucketName = "hello-minio";

        MinioClient minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();

        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());

                String policy = """
                        {
                          "Statement" : [ {
                            "Action" : "s3:GetObject",
                            "Effect" : "Allow",
                            "Principal" : "*",
                            "Resource" : "arn:aws:s3:::%s/*"
                          } ],
                          "Version" : "2012-10-17"
                        }
                        """.formatted(bucketName);

                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policy).build());
            }

            minioClient.uploadObject(UploadObjectArgs.builder().filename("D:\\wy292\\Documents\\luo.jpg").bucket(bucketName).object("luo.jpg").build());
            System.out.println("上传成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
