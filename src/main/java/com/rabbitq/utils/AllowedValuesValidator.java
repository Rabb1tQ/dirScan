package com.rabbitq.utils;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class AllowedValuesValidator  implements IParameterValidator {
    public void validate(String name, String value) throws ParameterException {
        if (!"head".equals(value) && !"get".equals(value)) {
            throw new ParameterException("参数 " + name + " 必须为head或get之一，当前值为：（" + value + "）");
        }
    }
}