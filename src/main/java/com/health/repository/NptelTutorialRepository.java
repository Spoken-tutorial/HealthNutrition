
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.health.model.NptelTutorial;

public interface NptelTutorialRepository
        extends JpaRepository<NptelTutorial, Integer>, JpaSpecificationExecutor<NptelTutorial> {

    ;

    NptelTutorial findByNptelTutorialId(int nptelTutorialId);

    NptelTutorial findByTitle(String string);

    NptelTutorial findByVideoUrl(String string);

//    @Query("from NptelTutorial t where  t.pack_lan_week_id = ?1")
//    List<NptelTutorial> findAllByPackLanWeek(PackLanWeekMapping packLanWeek);
//
//    @Query("from NptelTutorial t where t.status = true and t.pack_lan_week_id = ?1")
//    List<NptelTutorial> findAllByPackLanWeekByStatusTrue(PackLanWeekMapping packLanWeek);
//
//    List<NptelTutorial> findAllByStatus(boolean status);
//
//    void deleteByNptelTutorialId(int id);
//
//    @Override
//    <S extends NptelTutorial> S save(S entity);
//
//    @Query("from NptelTutorial t where t.status = true")
//    Page<NptelTutorial> findAllByPagination(Pageable page);
//
//    @Query("from NptelTutorial t where t.status = true and t.pack_lan_week_id = ?1")
//    Page<NptelTutorial> findAllByPackLanWeekPagination(PackLanWeekMapping packLanWeek, Pageable page);
//
//    @Query("from NptelTutorial t where t.pack_lan_week_id IN (:packLanWeek) and t.status = true")
//    public Page<NptelTutorial> findAllByPackLanWeekListPagination(
//            @Param("packLanWeek") List<PackLanWeekMapping> packLanWeek, Pageable page);
//
//    @Query("from NptelTutorial t where t.pack_lan_week_id IN (:packLanWeek) and t.status = true")
//    public List<NptelTutorial> findAllByPackLanWeekAndStatus(
//            @Param("packLanWeek") List<PackLanWeekMapping> packLanWeek);

}
