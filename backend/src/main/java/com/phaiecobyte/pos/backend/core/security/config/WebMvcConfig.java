package com.phaiecobyte.pos.backend.core.security.config;

import com.phaiecobyte.pos.backend.core.tenancy.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                // កំណត់ថា API មួយណាខ្លះដែលត្រូវឆ្លងកាត់ការត្រួតពិនិត្យ Tenant
                .addPathPatterns("/api/**")
                // លើកលែង API មួយចំនួនដូចជា Login ដែលមិនទាន់ដឹងថាជាហាងណា
                .excludePathPatterns("/api/v1/auth/**");
    }
}