package com.atguigu.lease.web.app.service;

public interface SmsService {
    void senCode(String phone, String code);
}
