package com.javarush.jira.login.internal.web;

import com.javarush.jira.common.error.DataConflictException;
import com.javarush.jira.login.UserTo;
import com.javarush.jira.login.internal.UserHandler;
import com.javarush.jira.login.internal.verification.ConfirmData;
import com.javarush.jira.login.internal.verification.RegistrationConfirmEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping(RegisterController.REGISTER_URL)
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    static final String REGISTER_URL = "/ui/register";

    private final ApplicationEventPublisher eventPublisher;
    private final UserHandler handler;
    private final UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target != null && emailValidator.supports(target.getClass())) {
            binder.addValidators(emailValidator);
        }
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("userTo", new UserTo());
        return "unauth/register";
    }

    @PostMapping
    public String register(@Valid UserTo userTo, BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("userTo", userTo);
            return "unauth/register";
        }
        log.info("register {}", userTo);
        if (userTo.getDisplayName() == null || userTo.getDisplayName().isBlank()) {
            userTo.setDisplayName(userTo.getFirstName() + " " + userTo.getLastName());
        }
        userTo.setDisplayName(HtmlUtils.htmlEscape(userTo.getDisplayName()));
        userTo.setFirstName(HtmlUtils.htmlEscape(userTo.getFirstName()));
        userTo.setLastName(HtmlUtils.htmlEscape(userTo.getLastName()));
        ConfirmData confirmData = new ConfirmData(userTo);
        request.getSession().setAttribute("token", confirmData);
        eventPublisher.publishEvent(new RegistrationConfirmEvent(userTo, confirmData.getToken(), RequestContextUtils.getLocale(request)));
        return "redirect:/view/login";
    }

    @GetMapping("/confirm")
    public String confirmRegistration(@RequestParam String token, SessionStatus status, HttpSession session,
                                      @SessionAttribute("token") ConfirmData confirmData) {
        log.info("confirm registration {}", confirmData);
        if (token.equals(confirmData.getToken())) {
            handler.createFromTo(confirmData.getUserTo());
            session.invalidate();
            status.setComplete();
            return "login";
        }
        throw new DataConflictException("Token mismatch error");
    }
}