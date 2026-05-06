package com.phaiecobyte.pos.backend.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // ប្រាប់ថា Annotation នេះប្រើបានតែនៅលើ Method ប៉ុណ្ណោះ
@Retention(RetentionPolicy.RUNTIME) // រក្សាទុកវារហូតដល់ពេល Runtime
public @interface LogAudit {
    
    // បង្កើត Parameters សម្រាប់បញ្ជាក់ពីសកម្មភាព
    String action(); // ឧ. "CREATE", "UPDATE", "DELETE"
    String moduleName(); // ឧ. "PRODUCT_MODULE", "CATEGORY_MODULE"
    String entityName(); // ឧ. "t_core_product"
    String defaultReason() default "ប្រតិបត្តិការប្រព័ន្ធស្វ័យប្រវត្តិ"; 
}