package com.insproject.provider.module.common;

import com.insproject.provider.module.user.entity.User;

public class SessionUser {
	public static ThreadLocal<User> threadLocal = new ThreadLocal<User>();
}
