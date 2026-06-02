package com.ytseries.sms.advice;

import com.ytseries.sms.ResponseModel.ResponseModel;
import com.ytseries.sms.exception.DuplicateExceptionResource;
import com.ytseries.sms.exception.NotFoundExceptionResourse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(DuplicateExceptionResource.class)
    public ResponseModel handelDuplicateException (DuplicateExceptionResource ex)
    {
        return ResponseModel.Conflict(
                ex.getMessage(),null
        );
    }
    @ExceptionHandler(NotFoundExceptionResourse.class)
    public ResponseModel handelDuplicateException (NotFoundExceptionResourse ex)
    {
        return ResponseModel.NotFound(
                ex.getMessage(),null
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseModel handleException (Exception ex)
    {
        return new ResponseModel(HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexcepted Error occured ,Please try again",null);
    }

}
