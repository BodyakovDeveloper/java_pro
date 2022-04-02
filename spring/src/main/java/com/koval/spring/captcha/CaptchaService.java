package com.koval.spring.captcha;

import com.koval.spring.constant.ConstantClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Component
public class CaptchaService implements ICaptchaService {

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private Environment environment;

    @Override
    public Boolean processResponse(String response) {
        String secretKey = environment.getProperty(ConstantClass.RECAPTCHA_KEY_SECRET);

        URI verifyUri = URI.create(String.format(ConstantClass.CAPTCHA_URL, environment.getProperty(ConstantClass.RECAPTCHA_KEY_SECRET), response));
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);

        if (googleResponse != null) {
            return googleResponse.isSuccess();
        } else {
            return false;
        }
    }
}
