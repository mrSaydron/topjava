package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.DatajpaProfileResolver;

@ActiveProfiles(resolver = DatajpaProfileResolver.class)
public class DatajpaMealServiceTest extends AbstractMealServiceTest{
}
