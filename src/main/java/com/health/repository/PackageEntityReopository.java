
package com.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.model.PackageEntity;

public interface PackageEntityReopository extends JpaRepository<PackageEntity, Integer> {

    void deleteById(int id);

    @Override

    <S extends PackageEntity> S save(S entity);

    PackageEntity findByPackageName(String packageName);

    Optional<PackageEntity> findByPackageId(int id);

}
