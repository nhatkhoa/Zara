/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 05/12/2015 - 7:47 AM at ZaraApi.
 */

package vn.zara.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.zara.domain.learn.DoExerciseRepository;
import vn.zara.infras.dao.ProcessDoExerciseService;
import vn.zara.web.dto.LessonDetail;

@RestController
@RequestMapping("/api/do-exercise")
public class DoExerciseRest {
    protected static Logger Logger = LoggerFactory.getLogger(DoExerciseRest.class);

    private final ProcessDoExerciseService processDoExerciseService;

    @Autowired
    public DoExerciseRest(ProcessDoExerciseService processDoExerciseService) {
        this.processDoExerciseService = processDoExerciseService;
    }

    @RequestMapping(value = "/{lesson_id}/{exercise_id}", method = RequestMethod.POST)
    public LessonDetail doExercise(@PathVariable("lesson_id") String lessonId,
                                   @PathVariable("exercise_id") String exerciseId,
                                   @RequestBody int score){
        return processDoExerciseService.updateExerciseScore(lessonId, exerciseId, score);
    }

    @RequestMapping(value = "/{exercise_id}", method = RequestMethod.GET)
    public void getQuestionForExercise(@PathVariable("exercise_id") String exerciseId){

    }
}
