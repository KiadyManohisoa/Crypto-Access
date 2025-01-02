// package com.crypto.config;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import com.crypto.service.security.AuthentificationInterceptor;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Autowired
//     private AuthentificationInterceptor authInterceptor;

//     @Override
//     public void addInterceptors(InterceptorRegistry registry) {
//         registry.addInterceptor(authInterceptor)
//                 .addPathPatterns("/**") // Appliquer à toutes les routes
//                 .excludePathPatterns("/connection", "/inscription","/crypto/inscription", "/crypto/connection" ,"/css/**", "/js/**", "/vendor/**"); // Exclure certaines routes
//     }
// }

