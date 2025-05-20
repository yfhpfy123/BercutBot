package com.dungeon.bercutbot.controler.util;

import com.dungeon.bercutbot.annotation.BotCommand;
import com.dungeon.bercutbot.annotation.BotController;
import com.dungeon.bercutbot.annotation.BotRequestMapping;
import com.dungeon.bercutbot.annotation.BotRequestParam;
import com.dungeon.bercutbot.view.BotView;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class BotDispatcher {
    private final ApplicationContext context;
    private final List<Route> routes = new ArrayList<>();

    public BotDispatcher(ApplicationContext context) {
        this.context = context;
        initRoutes();
    }

    class Route {
        Pattern pattern;
        Method method;
        Object bean;
        List<String> paramNames; // из шаблона, например ["stage"]
    }

    private void initRoutes() {
        for (Object bean : context.getBeansWithAnnotation(BotController.class).values()) {
            for (Method method : bean.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(BotRequestMapping.class)) {
                    String pathTemplate = method.getAnnotation(BotRequestMapping.class).value();
                    Route route = parseRoute(pathTemplate, method, bean);
                    routes.add(route);
                } else if (method.isAnnotationPresent(BotCommand.class)) {
                    String pathTemplate = method.getAnnotation(BotCommand.class).value();
                    Route route = parseRoute(pathTemplate, method, bean);
                    routes.add(route);
                }
            }
        }
    }

    public BotView dispatch(Update update) {
        String key = extractKey(update);
        for (Route route : routes) {
            Matcher matcher = route.pattern.matcher(key);
            if (matcher.matches()) {
                Object[] args = buildMethodArgs(route, matcher, update);
                try {
                    return (BotView) route.method.invoke(route.bean, args);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private String extractKey(Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()) {
            return update.getMessage().getText().split(" ")[0]; // /start
        }
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getData(); // например: /bot/register/human
        }
        return null;
    }

    private Object[] buildMethodArgs(Route route, Matcher matcher, Update update) {
        Parameter[] parameters = route.method.getParameters();
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].getType() == Update.class) {
                args[i] = update;
            } else if (parameters[i].isAnnotationPresent(BotRequestParam.class)) {
                String paramName = parameters[i].getAnnotation(BotRequestParam.class).value();
                int groupIndex = route.paramNames.indexOf(paramName) + 1;
                String rawValue = matcher.group(groupIndex);
                String cleanedValue = rawValue == null ? null : rawValue.replaceAll("[{}]", "");
                args[i] = cleanedValue;
            } else {
                args[i] = null; // или throw
            }
        }
        return args;
    }

    private Route parseRoute(String pathTemplate, Method method, Object bean) {
        List<String> paramNames = new ArrayList<>();

        StringBuilder regexBuilder = new StringBuilder();
        int lastPos = 0;

        Pattern paramPattern = Pattern.compile("\\{([^}]+)}");
        Matcher matcher = paramPattern.matcher(pathTemplate);

        while (matcher.find()) {
            String textBefore = pathTemplate.substring(lastPos, matcher.start());
            regexBuilder.append(Pattern.quote(textBefore));
            regexBuilder.append("(.+?)");
            paramNames.add(matcher.group(1));
            lastPos = matcher.end();
        }

        String tail = pathTemplate.substring(lastPos);
        regexBuilder.append(Pattern.quote(tail));
        String regex = "^" + regexBuilder.toString() + "$";

        Pattern pattern = Pattern.compile(regex);

        Route route = new Route();
        route.pattern = pattern;
        route.method = method;
        route.bean = bean;
        route.paramNames = paramNames;
        return route;
    }
}
