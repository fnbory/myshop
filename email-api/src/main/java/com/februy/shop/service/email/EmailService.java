package com.februy.shop.service.email;

import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 1:47
 */
public interface EmailService {

    void send(String to, String subject, String content, List<String> filePaths);

    void sendHTML(String email, String subject, Map<String, Object> params, List<String> filePaths);
}
