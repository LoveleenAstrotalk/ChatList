package com.astrotalk.sdk.api.utils;

public class Constants {

    public static final boolean LIVE_MODE = false;

    public static final String DOMAIN = "http://api.dev1.astrotalk.com:8080/AstroTalk/";
    public static final String DOMAIN_USER_CHAT = "http://api.dev1paidchat.astrotalk.com:8080/AstrotalkChat/";

    //  LIVE mode
//    public static final String DOMAIN = "https://api.prod.astrotalk.in/AstroTalk/";
//    public static final String DOMAIN_USER_CHAT = "https://api.paidchat.astrotalk.com/AstrotalkChat/";

    public static String AUTHORIZATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI5NTAxNDIzMzg2IiwiYXV0aCI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJidXNpbmVzc0lkIjoxLCJhcHBJZCI6MSwicm9sZV90eXBlIjoiUk9MRV9VU0VSIiwiaWQiOjQ3NjkxNCwiaXNMaXZlIjpmYWxzZSwiaWF0IjoxNjQ4NTM1OTQwLCJleHAiOjE2ODAwNzE5NDB9.ib0SdlOlvn37S1HOmzcTVYrG3ClBo7G6hdbMaBnrxQ0";
    public static String  ID = "476914";
    public static long  CHAT_ORDER_ID = 605051;


    public static int APP_ID = 5;
    public static int BUSINESS_ID = 1;
    public static int CONSULTANT_TYPE_ID = 1;
    public static String TIME_ZONE = "Asia/Kolkata";
    public static int CHAT_SERVICE_ID = 4;
    public static int LANGUAGE_ID = 1;
    public static String SDK_VERSION = "0.0.0";
    public static String IMAGE_DOMAIN = "https://astrotalk.s3.amazonaws.com/consultant_pic/";

    public static final int VOLLEY_TIME_OUT = 4 * 15 * 1000;
    public static final int VOLLEY_RETRY = 1;


    // Shared Preferences
    public static final String USER_ID = "id";
    public static final String USER_DETAIL = "userdetail";
    public static final String USER_TIME_ZONE = "user_time_zone";

    public static final String FIVE_MIN_TIPS = "five_min_tips";
    public static final String CONVERSION_FACTOR = "conversionFactor";
    public static final String ISO_CODE = "isoCode";

    // Chat List
    public static String GET_LAST_CHAT_ID_STATUS = DOMAIN_USER_CHAT + "chat/order/get/user/chat-status";
    public static String GET_NEXT_ONLINE_TIME = DOMAIN + "consultant/get/total/wait/time";
    public static String CHECK_CAN_CHAT_CAN_CALL_V2 = DOMAIN + "get/status/for/call-chat/v3";
    public static String WAIT_LIST_CHECK_USER_V2_NEW = DOMAIN + "queue-token/user/waitlist/get/v3";

    // Intake Form
    public static final String CHAT_INTAKE_FORM = DOMAIN_USER_CHAT + "chat/order/fill-intake-form";
    public static final String JOIN_WAIT_LIST_V2 = DOMAIN + "call-chat/queue/create-token/v2";
    public static final String ACCEPT_CHAT = DOMAIN_USER_CHAT + "chat/order/accept/byUser/v2";

    // Chat Screen
    public static final String GET_ASTROLOGER_ACTUAL_PRICE = DOMAIN + "consultant/get/price";
    public static final String CHAT_HISTORY = DOMAIN_USER_CHAT + "chat/message/get-chat-history/v3";
    public static final String LATEST_CHAT_MESSAGE = DOMAIN_USER_CHAT + "/chat/message/v2/get-latest-message/v2";
    public static final String SEND_CHAT_MESSAGE = DOMAIN_USER_CHAT + "chat/message/send-message/v3";
    public static final String COMPLETE_CHAT_ORDER = DOMAIN_USER_CHAT + "chat/order/completed/v2";

    // todo : Looking like extra api
    public static final String CHECK_ASTROLOGER_IS_VALID_FOR_CHAT_CONTINUE = DOMAIN + "show/chat/continue";
    public static final String IS_CHAT_CONTINUE_ENABLE = "is_chat_continue_enable";
}