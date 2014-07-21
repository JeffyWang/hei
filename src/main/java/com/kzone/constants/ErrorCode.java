package com.kzone.constants;

/**
 * Created by Jeffy on 14-4-24.
 */
public interface ErrorCode {
    final static String SUCCESS_CODE = "0";
    final static String SUCCESS_MSG = "Success";

    final static String LOGIN_ERR_MSG = "login failed";
    final static String PERMISSION_ERR_MSG = "Permission denied";

    final static String ADD_USER_ERR_CODE = "KU001";
    final static String ADD_USER_ERR_MSG = "Add user error : ";
    final static String GET_USER_ERR_CODE = "KU002";
    final static String GET_USER_ERR_MSG = "Get user's data error : ";
    final static String GET_USER_LIST_ERR_CODE = "KU003";
    final static String GET_USER_LIST_ERR_MSG = "Get users' data error : ";
    final static String DELETE_USER_ERR_CODE = "KU004";
    final static String DELETE_USER_ERR_MSG = "Delete a user error : ";
    final static String UPDATE_USER_ERR_CODE = "KU005";
    final static String UPDATE_USER_ERR_MSG = "Update the user data error : ";
    final static String COUNT_USER_ERR_CODE = "KU006";
    final static String COUNT_USER_ERR_MSG = "Count user error : ";

}
