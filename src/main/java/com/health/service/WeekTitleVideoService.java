
package com.health.service;

import java.util.List;

import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitleVideo;

public interface WeekTitleVideoService {

    List<WeekTitleVideo> findAll();

    void save(WeekTitleVideo weekTitleVideo);

    void saveAll(List<WeekTitleVideo> weekTitleList);

    WeekTitleVideo findByWeekVideoResourceAndLan(Week week, VideoResource videoResource, String langName);

    WeekTitleVideo findByWeekTitleVideoId(int weekTitleVideoId);

}
