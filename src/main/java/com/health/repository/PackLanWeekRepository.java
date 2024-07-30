
package com.health.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.health.model.PackLanWeekMapping;
import com.health.model.PackageLanMapping;
import com.health.model.Week;

public interface PackLanWeekRepository extends CrudRepository<PackLanWeekMapping, Integer> {

    // List<PackLanWeekMapping> findAllByPackageLanId(PackageLanMapping packLan);

    // void deleteById(int id);

    // @Override
    // <S extends PackLanWeekMapping> S save(S entity);

    List<PackLanWeekMapping> findAllByWeek(Week week);

    PackLanWeekMapping findByPackageLanAndWeek(PackageLanMapping packLan, Week week);

    // @Query("from PackLanWeekMapping where packageLan_ID IN (:packageLan)")
    // List<PackLanWeekMapping> findByPackageLan(@Param("packageLan")
    // List<PackageLanMapping> packageLan);
}
