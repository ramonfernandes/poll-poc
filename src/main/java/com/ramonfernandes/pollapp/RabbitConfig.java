package com.ramonfernandes.pollapp;

public class RabbitConfig {

    public static final String POLL_EXCHANGE = "poll-exchange";
    public static final String POLL_CLOSE_QUEUE = "poll.closed-queue";
    public static final String NOTIFY_RESULT_QUEUE = "poll.notify-result-queue";
    public static final String POLL_CLOSE_RK = "close-queue-rk";
    public static final String NOTIFY_RESULT_RK = "notify-result-rk";

}
