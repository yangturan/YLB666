package com.commons.contants;

public class RedisKey {
    //排行榜前三信息
    public static final String INVENT_RANK_KEY="inventRank";
    public static final String INVENT_RANK_KEY2="rank";

    //验证码信息,每个不同的用户还要在前面加上不同的用户唯一手机号，以防不同用户key相同导致新用户重置老用户验证码
    public static final String CODE_NUMBER="codeNumber";

    //当前用户的TOKEN信息,需要根据不同用户前缀不同的手机号
    public static final String USER_TOKEN="token";
}
