
package com.health.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.health.model.Language;
import com.health.model.PackageEntity;
import com.health.model.PackageLanMapping;

public interface PackageLanRepository extends CrudRepository<PackageLanMapping, Integer> {

    @Query("from PackageLanMapping where package1=?1 order by order")
    List<PackageLanMapping> findAllByPackage(PackageEntity package1);

    void deleteById(int id);

    @Override
    <S extends PackageLanMapping> S save(S entity);

    List<PackageLanMapping> findAllByLan(Language lan);

    @Query("from PackageLanMapping where package1=?1 and lan=?2")
    PackageLanMapping findByPackageAndlan(PackageEntity package1, Language lan);

}
