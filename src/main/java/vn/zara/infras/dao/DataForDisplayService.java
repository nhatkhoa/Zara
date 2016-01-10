/**
 * Copyright @ 2015 by Khoa Khoa - khoahoang.sa@gmail.com
 * Created by Khoa Hoang on 04/12/2015 - 7:35 AM at ZaraApi.
 */

package vn.zara.infras.dao;

import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.zara.domain.common.exception.LessonNotExisted;
import vn.zara.domain.common.exception.LessonResultNotExisted;
import vn.zara.domain.learn.DoExercise;
import vn.zara.domain.learn.DoExerciseRepository;
import vn.zara.domain.learn.LessonResultService;
import vn.zara.domain.lesson.Exercise;
import vn.zara.domain.lesson.Lesson;
import vn.zara.domain.lesson.LessonDataInitialize;
import vn.zara.domain.lesson.LessonRepository;
import vn.zara.domain.pokemon.PokemonGroup;
import vn.zara.domain.pokemon.PokemonService;
import vn.zara.domain.util.SecurityUtil;
import vn.zara.web.dto.ExerciseForListing;
import vn.zara.web.dto.LessonDetail;
import vn.zara.web.dto.LessonForListing;
import vn.zara.web.dto.PokemonDetail;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataForDisplayService {
    protected static Logger Logger = LoggerFactory.getLogger(DataForDisplayService.class);

    private final LessonResultService lessonResultService;
    private final PokemonService pokemonService;
    private final LessonRepository lessons;
    private final LessonDataInitialize lessonDataInitialize;
    private final DoExerciseRepository doExerciseRepository;

    @Autowired
    public DataForDisplayService(LessonResultService lessonResultService,
                                 PokemonService pokemonService,
                                 LessonRepository lessons,
                                 LessonDataInitialize lessonDataInitialize,
                                 DoExerciseRepository doExerciseRepository) {
        this.lessonResultService = lessonResultService;
        this.pokemonService = pokemonService;
        this.lessons = lessons;
        this.lessonDataInitialize = lessonDataInitialize;
        this.doExerciseRepository = doExerciseRepository;
    }

    public Set<LessonForListing> getAllLessonForIndex() {
        List<Lesson> lessonsOrigin = lessons.findAll();

        // --- Auto create database example
        if (lessonsOrigin.size() == 0) {
            Logger.debug("Auto create lesson example in the first time!");
            lessonDataInitialize.seedData();
        }

        Set<LessonForListing> lessonForListings = lessonsOrigin
                .stream()
                .map(lesson -> castLessonToLessonForListing(lesson))
                .collect(Collectors.toSet());

        Logger.debug(String.format("Get all lesson for user %s, result: %s", SecurityUtil.getCurrentLogin(), lessonForListings.size()));

        return lessonForListings;
    }

    private LessonForListing castLessonToLessonForListing(Lesson lesson) {
        long scoreOfLesson = lessonResultService.getLessonScore(lesson.getId());
        return new LessonForListing(lesson.getId(),
                lesson.getName(),
                lesson.getDescription(),
                lesson.getLevelRequire() <= scoreOfLesson,
                lesson.getLevelRequire() <= scoreOfLesson ? lesson.getLevelRequire() - scoreOfLesson : 0,
                lessonResultService.getPokemonForLesson(lesson.getId()));
    }


    public LessonDetail getLessonDetail(String lessonId) throws LessonResultNotExisted{
        String username = SecurityUtil.getCurrentLogin();

        Logger.debug(String.format("Request lesson detail for lesson %s on user %s", lessonId, username));
        Lesson lesson = lessons.findOne(lessonId);
        if (lesson == null) {
            String message = String.format("Get detail for %s but it's not existed.", lessonId);
            Logger.debug(message);
            throw new LessonNotExisted(message);
        }

        long sumOfScore = lessonResultService.getLessonScore(lesson.getId());
        PokemonDetail pokemonDetail = lessonResultService.getPokemonForLesson(lesson.getId());

        Logger.debug(String.format("Get pokemon for lesson %s : %s",
                lesson.getName(), pokemonDetail != null ? pokemonDetail.getName() : "None"));

        // --- Get exercises for this lesson.
        List<ExerciseForListing> exerciseForListings = lesson
                .getExercises()
                .stream()
                .map(exercise -> portToExerciseListing(exercise, lessonId, username))
                .collect(Collectors.toList());

        Logger.debug(String.format("Get %s exercises for lesson %s with user %s",
                exerciseForListings.size(), lessonId, username));

        LessonDetail lessonDetail = new LessonDetail(lessonId,lesson.getName(),
                lesson.getDescription(), lesson.getTheory(),pokemonDetail,exerciseForListings, sumOfScore);

        return lessonDetail;
    }

    private ExerciseForListing portToExerciseListing(Exercise exercise, String lessonId, String username) {
        Optional<DoExercise> lastDoExercise = getLastExerciseResult(exercise, lessonId, username);

        return new ExerciseForListing(exercise.getId(),
                exercise.getTitle(),
                exercise.getDescription(),
                lastDoExercise.isPresent() ? lastDoExercise.get().getScore() : 0,
                lastDoExercise.isPresent() ? lastDoExercise.get().getCreatedDate() : null);
    }

    private Optional<DoExercise> getLastExerciseResult(Exercise exercise, String lessonId, String username) {
        return doExerciseRepository
                .findByUsernameAndLesson(username, lessonId).findFirst();
    }



}
