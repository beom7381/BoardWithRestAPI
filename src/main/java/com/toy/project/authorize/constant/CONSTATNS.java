package com.toy.project.authorize.constant;

public class CONSTATNS {
    public static final Long REFRESH_TOKEN_EXPIRY_DAY = 1000 * 60 * 60 * 24 * 14L;
    public static final Long ACCESS_TOKEN_EXPIRY_DAY = 1000 * 30L;//1000 * 60 * 5L;
    public static final String REFRESH_TOKEN_PREFIX = "RefreshToken:";
    public static final String BLOCK_ACCESS_TOKEN_PREFIX = "BlockAccessToken:";
}
