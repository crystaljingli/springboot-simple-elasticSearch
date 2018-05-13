package com.lijing.springbootes.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.omg.CORBA.portable.UnknownException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author crystal
 * @CreatedDate 2018年05月13日 星期日 10时50分.
 */
@Configuration
public class EsConfig {

    @Bean
    public TransportClient transportClient() throws UnknownHostException {
        InetSocketTransportAddress localhost = new InetSocketTransportAddress(
                InetAddress.getByName("localhost"),
                9300);
        Settings settings = Settings.builder().put("cluster.name", "lijing").build();
        PreBuiltTransportClient transportClient = new PreBuiltTransportClient(settings);
        transportClient.addTransportAddress(localhost);
        return transportClient;

    }
}
