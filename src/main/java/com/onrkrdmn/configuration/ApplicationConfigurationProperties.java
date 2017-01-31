package com.onrkrdmn.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/***
 * Created by onur on 24.01.17.
 * ApplicationConfiguration properties
 */
@Component
@ConfigurationProperties(prefix = "appconfig")
@Getter
@Setter
public class ApplicationConfigurationProperties {

    private String[] defaultUserRoles;

    private String clientId;

    private String clientSecret;
}
