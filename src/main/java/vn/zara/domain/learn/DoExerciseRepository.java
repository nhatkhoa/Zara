/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:21 AM at ZaraApi.
 */

package vn.zara.domain.learn;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.zara.domain.lesson.Exercise;

import java.util.Optional;
import java.util.stream.Stream;

public interface DoExerciseRepository extends MongoRepository<DoExercise, String>{
    Stream<DoExercise> findByUsername(String username);

}
