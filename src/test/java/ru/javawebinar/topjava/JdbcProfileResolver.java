package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

public class JdbcProfileResolver implements ActiveProfilesResolver{
    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.getActiveDbProfile(), Profiles.JDBC};
    }
}
