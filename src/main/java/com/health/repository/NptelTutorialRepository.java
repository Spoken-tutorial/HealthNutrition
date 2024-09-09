
package com.health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.health.model.Language;
import com.health.model.NptelTutorial;
import com.health.model.PackageEntity;

public interface NptelTutorialRepository
        extends JpaRepository<NptelTutorial, Integer>, JpaSpecificationExecutor<NptelTutorial> {

    ;

    NptelTutorial findByNptelTutorialId(int nptelTutorialId);

    NptelTutorial findByTitle(String string);

    NptelTutorial findByVideoUrl(String string);

    NptelTutorial findByTitleAndPackageEntityAndLanAndWeek(String title, PackageEntity packageEntity, Language lan,
            int week);

    NptelTutorial findByTitleAndPackageEntityAndLan(String title, PackageEntity packageEntity, Language lan);

    NptelTutorial findByVideoUrlAndPackageEntityAndLan(String videoUrl, PackageEntity packageEntity, Language lan);

}
