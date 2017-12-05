package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    int deleteByIdAndUser(int id, User user);

    List<Meal> findAllByUserOrderByDateTimeDesc(User user);

    Meal findByIdAndUser(int id, User user);

    List<Meal> findAllByUserAndDateTimeBetweenOrderByDateTimeDesc(User user, LocalDateTime startDate, LocalDateTime endDate);
}
