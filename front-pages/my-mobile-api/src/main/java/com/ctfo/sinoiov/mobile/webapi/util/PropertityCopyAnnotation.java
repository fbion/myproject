/**
 * 
 */
package com.ctfo.sinoiov.mobile.webapi.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 此注解若注解在原始类上,则使用destFieldName注解目标属性
 * 
 * @author xubao
 *
 */
@Retention(RUNTIME) @Target({FIELD, METHOD})
public @interface PropertityCopyAnnotation {
    //目标属性名
    String destFieldName() default STR_DEFAULT;
    //目标属性类型
    Class<?> destFieldType() default DEFAULT.class;
    //原始属性类型
    Class<?> origFieldType() default DEFAULT.class;
    //原始属性类型描述,当原是类型无法表达元数据格式时,采用此字段描述
    String origFieldTypeDesc() default STR_DEFAULT;
    //原始属性类型格式化
    String origFieldFormat() default STR_DEFAULT;
    //缺省值字符串表示
    String defaultValue() default STR_DEFAULT;
    //nullable 是否可为空
    boolean nullAble() default false;
    //是否需要拷贝
    boolean needCopy() default true;
    //缺省类型
    static final class DEFAULT {}
    
    static final String STR_DEFAULT = "default";

}
