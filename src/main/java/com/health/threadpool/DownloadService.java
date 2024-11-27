package com.health.threadpool;

import java.util.concurrent.Semaphore;

import org.springframework.stereotype.Service;

@Service
public class DownloadService {

    private static final int MAX_CONCURRENT_DOWNLOADS = 3;
    private final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_DOWNLOADS, true);

    public boolean acquireDownloadSlot() {
        return semaphore.tryAcquire();
    }

    public void releaseDownloadSlot() {
        semaphore.release();
    }
}