package com.health.threadpool;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Value;

public class TimeoutOutputStream extends FilterOutputStream {

    @Value("${downloadTimeOut}")
    private long downloadTimeOut;
    private final long timeout;

    public TimeoutOutputStream(OutputStream out, long timeoutMillis) {
        super(out);
        this.timeout = timeoutMillis;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        long start = System.currentTimeMillis();
        super.write(b, off, len);
        long duration = System.currentTimeMillis() - start;
        if (duration > timeout) {
            throw new IOException("Write operation timed out after " + timeout + "ms");
        }
    }

}
