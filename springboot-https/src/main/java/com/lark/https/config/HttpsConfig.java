package com.lark.https.config;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 如何同时支持？
 * 如果你需要同时支持 HTTP 和 HTTPS 这两个协议，就需要把另外一个协议用程序化的方式来配置。
 * <p>
 * 因为通过程序的方式配置 HTTP 协议更加简单一点，所以，Spring Boot 推荐的做法是把 HTTPS 配置在配置文件，HTTP 通过程序来配置。
 * <p>
 * 采用 Undertow/Tomcat 作为服务器，同时支持 Https、Http 服务配置
 */
@Configuration
public class HttpsConfig {

    /**
     * http服务端口
     */
    @Value("${custom.server.http.port}")
    private Integer httpPort;

    /**
     * https服务端口
     */
    @Value("${server.port}")
    private Integer httpsPort;

    /**
     * 采用 Undertow 作为服务器，同时支持 Https、Http 服务配置
     *
     * @return
     */
    @Bean
    public ServletWebServerFactory undertowFactory() {
        UndertowServletWebServerFactory undertowFactory = new UndertowServletWebServerFactory();
//        undertowFactory.setIoThreads(Math.max(Runtime.getRuntime().availableProcessors(), 2));
//        undertowFactory.setWorkerThreads(300);
        undertowFactory.addBuilderCustomizers((Undertow.Builder builder) -> {
            //同时监听http端口
            builder.addHttpListener(httpPort, "0.0.0.0");
            // 开启HTTP2
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true);
        });

        // 开启HTTP自动跳转至HTTPS
        // 此配置注释可同时支持http及https调用
        /*undertowFactory.addDeploymentInfoCustomizers(deploymentInfo -> {
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection().addUrlPattern("/*"))
                    .setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> httpsPort);
        });*/
        return undertowFactory;
    }

    /**
     * 采用 Tomcat 作为服务器，同时支持 Https、Http 服务配置
     *
     * @return
     */
    /*@Bean
    public ServletWebServerFactory tomcatFactory() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(httpPort);

//        Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
//        protocol.setConnectionTimeout(200);
//        protocol.setMaxThreads(1000);
//        protocol.setMaxConnections(500);

        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }*/
}
