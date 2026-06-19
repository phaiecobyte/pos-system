//package com.phaiecobyte.pos.backend.core.tenancy;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//public class TenantInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        // ចាប់យក Tenant ID ពី HTTP Header ដែលបញ្ជូនពី Angular Frontend
//        String tenantId = request.getHeader("X-TenantID");
//
//        if (tenantId != null && !tenantId.isEmpty()) {
//            TenantContext.setCurrentTenant(tenantId);
//        } else {
//            // កំណត់ Default Tenant (ប្រសិនបើមិនមានបញ្ជូនមក) ឬអាចបោះ Error (Throw Exception)
//            TenantContext.setCurrentTenant("DEFAULT_TENANT");
//        }
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        // សម្អាតទិន្នន័យនៅពេលបញ្ចប់ Request
//        TenantContext.clear();
//    }
//}