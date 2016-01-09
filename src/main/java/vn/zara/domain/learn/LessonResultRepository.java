/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:21 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface LessonResultRepository extends MongoRepository<LessonResult, String> {
    Optional<LessonResult> findOneByLessonIdAndUsername(String lessonId, String username);
    Stream<LessonResult> findByUsername(String username);
}
