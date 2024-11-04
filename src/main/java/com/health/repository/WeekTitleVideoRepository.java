package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitleVideo;

public interface WeekTitleVideoRepository extends JpaRepository<WeekTitleVideo, Integer> {

    @Query("SELECT wt FROM WeekTitleVideo wt WHERE wt.week = :week AND wt.videoResource = :videoResource AND wt.videoResource.lan.langName = :langName")
    WeekTitleVideo findByWeekVideoResourceAndLan(@Param("week") Week week,
            @Param("videoResource") VideoResource videoResource, @Param("langName") String langName);

    WeekTitleVideo findByWeekTitleVideoId(int weekTitleVideoId);

}
