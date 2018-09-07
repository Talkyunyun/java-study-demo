package com.mmlogs.target;

import java.lang.annotation.*;

/**
 * Class Value
 *
 * @author Gene.yang
 * @date 2018/07/02
 */

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

    String value();
}
