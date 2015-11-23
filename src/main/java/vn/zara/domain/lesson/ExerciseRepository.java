/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 23/11/2015 - 6:21 AM at ZaraApi.
 */

package vn.zara.domain.lesson;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.zara.domain.user.User;

import java.util.Optional;

public interface ExerciseRepository extends MongoRepository<Exercise, String>{
}
