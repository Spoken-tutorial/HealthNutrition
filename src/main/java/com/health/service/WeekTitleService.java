
package com.health.service;

import java.util.List;

import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitle;

public interface WeekTitleService {

    WeekTitle findByWeekTitleId(int weekTitleId);

    List<WeekTitle> findAll();

    void save(WeekTitle weekTitle);

    void saveAll(List<WeekTitle> weekTitleList);

    WeekTitle findByWeekVideoResourceAndLan(Week week, VideoResource videoResource, String langName);

}
