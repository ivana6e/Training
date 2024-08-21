package com.example.project2.util;

import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class I18nUtil {

    @Value("${spring.messages.basename}")
    private String basename;

    private final LocaleResolverImpl resolver;
    private static LocaleResolverImpl custom;
    private static String path;

    public I18nUtil(LocaleResolverImpl resolver) {
        this.resolver = resolver;
    }

    public static void setBasename(String basename) {
        I18nUtil.path = basename;
    }

    public static void setCustom(LocaleResolverImpl resolver) {
        I18nUtil.custom = resolver;
    }

    @PostConstruct
    public void init() {
        setBasename(basename);
        setCustom(resolver);
    }

    public static String getMessage(String code) {
        Locale locale = custom.getLocale();
        return getMessage(code, null, code, locale);
    }

    public static String getMessage(String code, String lang) {
        Locale locale;
        if(StringUtils.isEmpty(lang)) {
            locale = Locale.UK;
        }
        else {
            try {
                var split = lang.split("_");
                locale = new Locale(split[0], split[1]);
            }
            catch(Exception e) {
                locale = Locale.UK;
            }
        }
        return getMessage(code, null, code, locale);
    }

    public static String getMessage(String code, Objects[] args, String defaultMessage, Locale locale) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        messageSource.setBasename(path);
        String content;

        try {
            content = messageSource.getMessage(code, args, locale);
        }
        catch(Exception e) {
            log.error("international para acquire fail ===> {}", e.getMessage(), e);
            content = defaultMessage;
        }
        return content;
    }

    public static String getStationLetterMessage(String code, String lang) {
        Locale locale = Objects.equals(lang, "zh_TW") ? Locale.TAIWAN : Locale.UK;
        return getMessage(code, null, code, locale);
    }
}
