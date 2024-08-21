package com.example.project2.util;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Configuration
public class LocaleResolverImpl implements LocaleResolver {

    @Autowired
    private HttpServletRequest request;

    public Locale getLocale(){
        return resolveLocale(request);
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter("Accept-Language");
        Locale locale;

        if(!StringUtils.isEmpty(lang)) {
            String[] split = lang.split("-");
            locale = new Locale(split[0], split[1]);
        }
        else {
            // host language
            locale = Locale.getDefault();
        }
        return locale;
    }

    @Override
    public void setLocale(@NonNull HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
