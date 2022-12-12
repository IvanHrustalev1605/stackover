package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.converters.QuestionConverter;
import com.javamentor.qa.platform.dao.abstracts.model.QuestionDao;
import com.javamentor.qa.platform.dao.abstracts.model.TagDao;
import com.javamentor.qa.platform.dao.abstracts.repository.ReadWriteDao;
import com.javamentor.qa.platform.models.dto.QuestionCreateDto;
import com.javamentor.qa.platform.models.dto.QuestionDto;
import com.javamentor.qa.platform.models.entity.question.Question;
import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.mapper.QuestionMapper;
import com.javamentor.qa.platform.models.mapper.TagListMapper;
import com.javamentor.qa.platform.service.abstracts.model.QuestionService;
import com.javamentor.qa.platform.service.abstracts.model.TagService;
import com.javamentor.qa.platform.service.abstracts.model.UserService;
import com.javamentor.qa.platform.service.impl.repository.ReadWriteServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl extends ReadWriteServiceImpl<Question, Long> implements QuestionService {

    private final TagDao tagDao;
    private final QuestionDao questionDao;
    private final TagService tagService;
    private final UserService userService;

    public QuestionServiceImpl(ReadWriteDao<Question, Long> readWriteDao,
                               QuestionDao questionDao, TagDao tagDao,
                               TagService tagService, UserService userService) {
        super(readWriteDao);
        this.questionDao = questionDao;
        this.tagDao = tagDao;
        this.tagService = tagService;
        this.userService = userService;
    }

    public QuestionDto createQuestion(QuestionCreateDto questionCreateDto, String authUserEmail) {
        List<Tag> tags = tagService.checkTagInDatabaseByName(
                TagListMapper.INSTANCE.toTagList(questionCreateDto.getTags())
        );

        User user = userService.getByEmail(authUserEmail).get();

        Question question = QuestionConverter.INSTANCE.questionConverterCreateToQuestion(questionCreateDto);
        question.setUser(user);
        question.setTags(tags);
        questionDao.persist(question);

        return QuestionMapper.INSTANCE.toQuestionDto(question);
    }
}
