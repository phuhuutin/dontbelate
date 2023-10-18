package org.dontbelate.drivingrouteservice;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.dontbelate.drivingrouteservice.entity.DBLDrivingRouteChangeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
public class DrivingrouteServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivingrouteServiceApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

//    @Bean
//    ObservedAspect observedAspect(ObservationRegistry observationRegistry) {
//        return new ObservedAspect(observationRegistry);
//    }


//}
//@Component
//class MyHandler implements ObservationHandler<Observation.Context> {
//
//    private static final Logger log = LoggerFactory.getLogger(MyHandler.class);
//
//    @Override
//    public void onStart(Observation.Context context) {
//        log.info("Before running the observation for context [{}], drivingrouteType [{}]", context.getName(), getUserTypeFromContext(context));
//    }
//
//    @Override
//    public void onStop(Observation.Context context) {
//        log.info("After running the observation for context [{}], drivingrouteType [{}]", context.getName(), getUserTypeFromContext(context));
//    }
//
//    @Override
//    public boolean supportsContext(Observation.Context context) {
//        return true;
//    }
//
//    private String getUserTypeFromContext(Observation.Context context) {
//        return StreamSupport.stream(context.getLowCardinalityKeyValues().spliterator(), false)
//                .filter(keyValue -> "drivingrouteType".equals(keyValue.getKey()))
//                .map(KeyValue::getValue)
//                .findFirst()
//                .orElse("UNKNOWN");
//    }
}