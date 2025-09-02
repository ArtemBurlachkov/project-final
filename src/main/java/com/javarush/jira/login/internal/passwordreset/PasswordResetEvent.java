package com.javarush.jira.login.internal.passwordreset;

import com.javarush.jira.common.AppEvent;
import com.javarush.jira.login.User;

import java.util.Locale;

public record PasswordResetEvent(User user, String token, Locale locale) implements AppEvent {
}
