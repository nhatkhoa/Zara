/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 05/12/2015 - 6:37 AM at ZaraApi.
 */

package vn.zara.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vn.zara.domain.learn.DoExerciseService;
import vn.zara.domain.lesson.Lesson;
import vn.zara.domain.lesson.LessonRepository;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.infras.dao.DataForDisplayService;
import vn.zara.web.dto.LessonDetail;
import vn.zara.web.dto.LessonForListing;

import java.util.Set;


@RestController
@RequestMapping("/api")
public class LessonRest {
    protected static Logger Logger = LoggerFactory.getLogger(LessonRest.class);

    private final DataForDisplayService dataForDisplayService;
    private final DoExerciseService doExerciseService;
    private final LessonRepository lessonRepository;

    @Autowired
    public LessonRest(DataForDisplayService dataForDisplayService,
                      DoExerciseService doExerciseService,
                      LessonRepository lessonRepository) {
        this.dataForDisplayService = dataForDisplayService;
        this.doExerciseService = doExerciseService;
        this.lessonRepository = lessonRepository;
    }

    @RequestMapping(value = "/lessons", method = RequestMethod.GET)
    public Set<LessonForListing> getLessons(){
        Set<LessonForListing> lessons = dataForDisplayService.getAllLessonForIndex();
        Logger.debug(String.format("Request all lesson for user %s : result %s", SecurityUtil.getCurrentLogin(), lessons.size()));
        return lessons;
    }

    @RequestMapping(value = "/lessons/{id}", method = RequestMethod.GET)
    public LessonDetail getLesson(@PathVariable("id") String lessonId){
        return dataForDisplayService.getLessonDetail(lessonId);

    }

}
