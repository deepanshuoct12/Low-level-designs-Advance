package org.dynamik.model;

import org.dynamik.constants.LogLevel;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncLogger extends Logger {
    private BlockingQueue<Message> buffer;
    private ExecutorService executor;
    private AtomicBoolean isShutdown;

    public AsyncLogger(LoggerConfig loggerConfig) {
        super(loggerConfig);
        buffer = new ArrayBlockingQueue<>(loggerConfig.getBufferSize());
        isShutdown = new AtomicBoolean(false);
        executor = Executors.newSingleThreadExecutor();
        startWorker();
    }

    @Override
    protected void log(String msg, LogLevel logLevel) {
        if (isShutdown.get()) {
            System.err.println("Logger is shutdown, cannot accept new messages");
            return;
        }
        
        Message message = new Message(msg, logLevel);
        try {
            buffer.put(message);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void startWorker() {
        executor.submit(() -> {
            while (!isShutdown.get() || !buffer.isEmpty()) {
                try {
                    Message message = buffer.poll(100, TimeUnit.MILLISECONDS);
                    if (message != null) {
                        for (Sink sink : sinks) {
                            sink.log(message);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }
    
    public void shutdown() {
        isShutdown.set(true);
        executor.shutdown();
    }

}
