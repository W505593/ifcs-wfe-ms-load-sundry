package com.wexinc.gf.ifcs.ms.spring.ifcswfemsloadsundry.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class LoadSundryRoute extends RouteBuilder {

    @EndpointInject("direct:loadsundry")
    @Override
    public void configure() throws Exception {

        from("direct:loadsundry")
                .marshal().json(JsonLibrary.Jackson)
                .to("log:DEBUG?showBody=true&showHeaders=true")
                .toD("rest:post:?host=${header.serviceURL}&outType=" +
                        "com.wexinc.bulkindicatorSchema.BulkIndicatorResponse");

    }
}
