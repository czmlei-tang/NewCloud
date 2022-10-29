package com.tang.newcloud.service.base.hander;

import com.tang.newcloud.common.base.result.R;
import com.tang.newcloud.common.base.result.ResultCodeEnum;
import com.tang.newcloud.common.base.util.ExceptionUtils;
import com.tang.newcloud.service.base.exception.NewCloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        log.info(ExceptionUtils.getMessage(e));
        return R.error();
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e){
        log.info(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e){
        log.info(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    @ExceptionHandler(NewCloudException.class)
    @ResponseBody
    public R error(NewCloudException e){
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
