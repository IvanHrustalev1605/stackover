package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.mapper.QuestionMapper;
import com.javamentor.qa.platform.models.mapper.TagListMapper;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final QuestionDao questionDao;
    private final TagService tagService;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao, QuestionDao questionDao,
                               TagService tagService) {
        super(readWriteDao);
        this.questionDao = questionDao;
        this.tagService = tagService;
    }

    public Optional<QuestionDto> createQuestion(QuestionCreateDto questionCreateDto, User user) {
        List<Tag> tags = tagService.checkTagInDatabaseByName(
                TagListMapper.INSTANCE.toTagList(questionCreateDto.getTags())
        );

        Question question = QuestionConverter.INSTANCE.questionConverterCreateToQuestion(questionCreateDto);
        question.setUser(user);
        question.setTags(tags);
        questionDao.persist(question);

        return Optional.ofNullable(QuestionMapper.INSTANCE.toQuestionDto(question));
    }
}
