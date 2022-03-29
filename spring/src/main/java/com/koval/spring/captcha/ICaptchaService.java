package com.koval.spring.captcha;

public interface ICaptchaService {
    Boolean processResponse(final String response);
}