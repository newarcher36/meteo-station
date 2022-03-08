package com.weather.meteo_station.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.inject.Inject;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Inject
  private HandlerInterceptor yourInjectedInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(yourInjectedInterceptor);
  }
}