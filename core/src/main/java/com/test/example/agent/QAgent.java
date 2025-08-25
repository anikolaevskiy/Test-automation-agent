package com.test.example.agent;

public interface QAgent {

    State start(String scenario);

    State nextAction(String screenshot);

    record State(boolean proceed, boolean successful, String screenshot, String message) { }
}
