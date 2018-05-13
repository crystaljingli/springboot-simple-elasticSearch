package com.lijing.springbootsimple.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 19时20分.
 */
@Configuration
@PropertySource(value= "classpath:resource.properties")
@ConfigurationProperties(prefix = "com.baidu.open-source")
public class Resource {

    private String name;
    private String webSite;
    private String language;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
