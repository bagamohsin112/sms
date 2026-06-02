package com.ytseries.sms.ResponseModel;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ResponseModel {
    private HttpStatus status;
    private int StatusCode;
    private  String message;
    private Object data;

    public ResponseModel(HttpStatus status,String message,Object data)
    {
        this.status=status;
        StatusCode=this.status.value();
        this.message=message;
        this.data=data;
    }
    public static   ResponseModel created(String message,Object data){
        return  new ResponseModel(HttpStatus.CREATED,message,data);
    }
    public static   ResponseModel success(String message,Object data){
        return  new ResponseModel(HttpStatus.OK,message,data);
    }
    public static  ResponseModel NotFound(String message,Object data){
        return  new ResponseModel(HttpStatus.NOT_FOUND,message,data);
    }
    public static ResponseModel  Conflict(String message,Object data){
        return  new ResponseModel(HttpStatus.CONFLICT,message,data);
    }


}


