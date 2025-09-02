package com.javarush.jira.login.internal.verification;

import com.javarush.jira.common.AppEvent;
import com.javarush.jira.login.UserTo;

import java.util.Locale;

public record RegistrationConfirmEvent(UserTo userto, String token, Locale locale) implements AppEvent {
}
