package com.common.constants;

public class CommonConstants {

   public interface MessageType {
         String ERROR = "error";
         String SUCCESS = "success";
    }

    public interface GENERIC_PROC
    {
         static final String GET_BY_ID = "{call GET_BY_ID(?,?)}";
         static final String GET_ALL ="{call GET_ALL(?)}";
         static final String UPDATE_STATUS = "{call UPDATE_STATUS(?,?,?)}";
         static final String DELETE = "{call DELETE_BY_ID(?,?)}";
    }
    public interface STATUS
    {
       static final int ACTIVE = 1;
       static final int IN_ACTIVE = 0;
    }
}
