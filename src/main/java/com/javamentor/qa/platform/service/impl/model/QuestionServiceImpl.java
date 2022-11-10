package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final TagService tagService;
    private final QuestionDao questionDao;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, TagService tagService, QuestionDao questionDao) {
        super(readWriteDao);
        this.tagService = tagService;
        this.questionDao = questionDao;
    }

    @Override
    public void persist(Question question) {
        List<String> listTagName = question.getTags().stream().map(Tag::getName).toList();
        question.setTags(tagService.backListTag(listTagName));
        super.update(question);
    }

    @Override
    public Optional<Long> getCountQuestion() {
        return questionDao.getCountQuestion();
    }

}
