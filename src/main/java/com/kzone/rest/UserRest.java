package com.kzone.rest;

import com.kzone.bean.User;
import com.kzone.bo.ErrorMessage;
import com.kzone.constants.CommonConstants;
import com.kzone.constants.ErrorCode;
import com.kzone.constants.ParamsConstants;
import com.kzone.service.UserService;
import com.kzone.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * Created by Jeffy on 14-4-24.
 */
@Component
@Path("/user")
public class UserRest {
    Logger log = Logger.getLogger(UserRest.class);
    @Autowired
    private UserService userService;

    @GET
    @Path("/info/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") String id) {
        User user = null;

        try {
            if(userService.getUser(id) != null){
                user = userService.getUser(id);
            } else {
                user = userService.getUser(Integer.valueOf(id));
            }
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_ERR_CODE, ErrorCode.GET_USER_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get a " + user.toString());
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        List<User> userList = null;

        try {
            userList = userService.getUsers(null, null);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get user list success.");
        return Response.ok(userList, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/info/{offset}/{length}/{orderDesc}/{equalParams}/{likePrams}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersPage(@Context UriInfo uriInfo) {
        List<User> usersPage = null;

        MultivaluedMap<String, String> params = uriInfo.getPathParameters();
        Map<String, String> equalCondition = new HashMap<String, String>();
        Map<String, String> likeCondition = new HashMap<String, String>();
        int offset = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_OFFSET));
        int length = Integer.parseInt(params.getFirst(ParamsConstants.PAGE_PARAMS_LENGTH));
        String orderDesc = params.getFirst(ParamsConstants.PAGE_PARAMS_ORDER_DESC);

        // 精确查询条件健值对
        if (params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS)))
            equalCondition.put(ParamsConstants.PARAM_ID, params.getFirst(ParamsConstants.PAGE_PARAMS_EQUALPARAMS));
        // 模糊查询条件健值对
        if (params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS) != null
                && !CommonConstants.NULL_STRING.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS))
                && !CommonConstants.NULL.equals(params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS)))
            likeCondition.put(ParamsConstants.PARAM_UUID, params.getFirst(ParamsConstants.PAGE_PARAMS_LIKEPARAMS));

        try {
            usersPage = userService.getUsersPage(offset, length, orderDesc, equalCondition, likeCondition);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.GET_USER_LIST_ERR_CODE, ErrorCode.GET_USER_LIST_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Get users pages success");
        return Response.ok(usersPage, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@RequestBody String body) {
        User user = null;
        UUID uuid = UUID.randomUUID();

        try {
            user = StringUtil.jsonStringToObject(body, User.class);
            user.setUuid(uuid.toString());
            user = userService.addUser(user);
        } catch (Exception e) {
            log.warn(e);
            return Response.ok(new ErrorMessage(ErrorCode.ADD_USER_ERR_CODE, ErrorCode.ADD_USER_ERR_MSG),MediaType.APPLICATION_JSON).build();
        }

        log.debug("Add a " + user.toString());
        return Response.ok(user, MediaType.APPLICATION_JSON).build();
    }

}
