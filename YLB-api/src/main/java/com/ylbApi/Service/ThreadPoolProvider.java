package com.ylbApi.Service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

public interface ThreadPoolProvider {
    ThreadPoolExecutor getPool();
}
