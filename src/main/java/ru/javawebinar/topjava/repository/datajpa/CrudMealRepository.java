package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Query("SELECT m from Meal m WHERE m.id=:id AND m.user.id=:userId")
    Optional<Meal> findById(@Param("id") Integer id, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") Integer userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userId")
    List<Meal> findAll(@Param("userId") Integer userId, Sort sort);


    @Override
    @Transactional
    <S extends Meal> S save(S s);
}
