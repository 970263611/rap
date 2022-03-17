package com.psbc.rpa.handler.impl;

import com.psbc.rpa.handler.RpaHandler;
import com.psbc.rpa.util.JsUtil;

/**
 * @author dahua
 * @time 2022/3/15 9:12
 */
public class CaseHandler implements RpaHandler {
    @Override
    public String handler(String msg) {
        if (msg != null) {

        }
        return JsUtil.buildJs(
                JsUtil.head(
                        0,
                        false,
                        null,
                        null,
                        "C:/Users/dahua/Desktop/yc-rpa/youdao.json"
                ),
                JsUtil.detail(),
                JsUtil.excel(),
                JsUtil.foot()
        );
    }
}
