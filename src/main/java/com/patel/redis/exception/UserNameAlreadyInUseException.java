package com.patel.redis.exception;

public class UserNameAlreadyInUseException extends BaseException{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UserNameAlreadyInUseException(String msg) {
        super(msg);
    }

}