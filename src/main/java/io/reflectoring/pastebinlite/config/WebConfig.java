//package io.reflectoring.pastebinlite.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "https://pastebin-frontend-1s1ek4s8d-rohinis-projects-c95122e8.vercel.app"
//                )
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("content-type")
//                .exposedHeaders("*")
//                .allowCredentials(false)
//                .maxAge(3600);
//    }
//}
//
