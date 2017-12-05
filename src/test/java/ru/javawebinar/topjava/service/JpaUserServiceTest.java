package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.JpaProfileResolver;

@ActiveProfiles(resolver = JpaProfileResolver.class)
public class JpaUserServiceTest extends AbstractUserServiceTest{
}
