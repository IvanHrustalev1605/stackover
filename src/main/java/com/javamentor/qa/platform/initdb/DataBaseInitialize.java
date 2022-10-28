package com.javamentor.qa.platform.initdb;

import com.javamentor.qa.platform.models.entity.Badge;
import com.javamentor.qa.platform.models.entity.BookMarks;
import com.javamentor.qa.platform.models.entity.Comment;
import com.javamentor.qa.platform.models.entity.CommentType;
import com.javamentor.qa.platform.models.entity.chat.*;
import com.javamentor.qa.platform.models.entity.question.*;
import com.javamentor.qa.platform.models.entity.question.answer.Answer;
import com.javamentor.qa.platform.models.entity.question.answer.CommentAnswer;
import com.javamentor.qa.platform.models.entity.question.answer.VoteAnswer;
import com.javamentor.qa.platform.models.entity.user.Role;
import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.models.entity.user.UserBadges;
import com.javamentor.qa.platform.models.entity.user.UserFavoriteQuestion;
import com.javamentor.qa.platform.models.entity.user.reputation.Reputation;
import com.javamentor.qa.platform.models.entity.user.reputation.ReputationType;
import com.javamentor.qa.platform.service.abstracts.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DataBaseInitialize {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final ReputationService reputationService;
    private final TagService tagService;
    private final UserService userService;
    private final RoleService roleService;
    private final CommentAnswerService commentAnswerService;
    private final IgnoredTagService ignoredTagService;
    private final TrackedTagService trackedTagService;
    private final VoteAnswerService voteAnswerService;
    private final VoteQuestionService voteQuestionService;
    private final BadgesService badgesService;
    private final BookmarksService bookmarksService;
    private final ChatService chatService;
    private final CommentService commentService;
    private final CommentQuestionService commentQuestionService;
    private final GroupChatService groupChatService;
    private final MessageService messageService;
    private final QuestionViewedService questionViewedService;
    private final RelatedTagService relatedTagService;
    private final SingleChatService singleChatService;
    private final UserBadgesService userBadgesService;
    private final UserFavoriteQuestionService userFavoriteQuestionService;
    private final static int USER_COUNT = 5;
    private final static int QUESTION_COUNT = 5;
    private final static int TAG_COUNT = 5;
    private final static int ANSWER_COUNT = 5;
    private final static int REPUTATION_COUNT = 5;
    private final static int BADGE_COUNT = 5;
    private final static int CHAT_COUNT = 2;
    private final static int COMMENT_COUNT = 5;
    private final static int COMMENT_QUESTION_COUNT = 3;
    private final static int COMMENT_ANSWER_COUNT = 3;
    private final static int MESSAGE_COUNT = 5;
    private final static int VOTE_ANSWERS_COUNT = 2;
    private final static int VOTE_QUESTIONS_COUNT = 2;
    private final static List<Question> questions = new ArrayList<>();
    private final static List<Tag> tags = new ArrayList<>();

    public DataBaseInitialize(UserService userService, RoleService roleService, AnswerService answerService,
                              QuestionService questionService, ReputationService reputationService, TagService tagService,
                              CommentAnswerService commentAnswerService, IgnoredTagService ignoredTagService, TrackedTagService trackedTagService,
                              VoteAnswerService voteAnswerService, VoteQuestionService voteQuestionService, BadgesService badgesService,
                              BookmarksService bookmarksService, ChatService chatService, CommentService commentService,
                              CommentQuestionService commentQuestionService, GroupChatService groupChatService, MessageService messageService,
                              QuestionViewedService questionViewedService, RelatedTagService relatedTagService, SingleChatService singleChatService,
                              UserBadgesService userBadgesService, UserFavoriteQuestionService userFavoriteQuestionService) {
        this.userService = userService;
        this.roleService = roleService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.reputationService = reputationService;
        this.tagService = tagService;
        this.commentAnswerService = commentAnswerService;
        this.ignoredTagService = ignoredTagService;
        this.trackedTagService = trackedTagService;
        this.voteAnswerService = voteAnswerService;
        this.voteQuestionService = voteQuestionService;
        this.badgesService = badgesService;
        this.bookmarksService = bookmarksService;
        this.chatService = chatService;
        this.commentService = commentService;
        this.commentQuestionService = commentQuestionService;
        this.groupChatService = groupChatService;
        this.messageService = messageService;
        this.questionViewedService = questionViewedService;
        this.relatedTagService = relatedTagService;
        this.singleChatService = singleChatService;
        this.userBadgesService = userBadgesService;
        this.userFavoriteQuestionService = userFavoriteQuestionService;
    }

    void dataBaseInit() {
        roleInit();
        userInit();
        tagInit();
        questionInit();
        answerInit();
        reputationInit();
        badgesInit();
        bookmarksInit();
        chatInit();
        commentInit();
        groupChatInit();
        messageInit();
        questionViewedInit();
        relatedTagInit();
        singleChatInit();
        tagIgnoreInit();
        tagTrackedInit();
        userBadgesInit();
        userFavoriteQuestionInit();
        votesOnAnswersInit();
        votesOnQuestionsInit();
        commentQuestionInit();
        commentAnswerInit();
    }

    void answerInit() {
        for (int answerNum = 1; answerNum <= ANSWER_COUNT; answerNum++) {
            Answer answer = new Answer();
            answer.setDateAcceptTime(LocalDateTime.now());
            answer.setHtmlBody(String.format("HtmlBody %s", answerNum));
            answer.setIsDeleted(false);
            answer.setIsDeletedByModerator(false);
            answer.setIsHelpful(false);
            answer.setPersistDateTime(LocalDateTime.now());
            answer.setUpdateDateTime(LocalDateTime.now());
            if (answerNum == 2) {
                answer.setQuestion(questionService.getById(2L).get());
                answer.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                answer.setQuestion(questionService.getById(3L).get());
                answer.setUser(userService.getByEmail("email3@mail.com").get());
            }
            answerService.persist(answer);
        }
    }

    void badgesInit() {
        for (int badgeNum = 1; badgeNum <= BADGE_COUNT; badgeNum++) {
            Badge badge = new Badge();
            badge.setBadgeName(String.format("Name %s", badgeNum));
            badge.setDescription(String.format("Description %s", badgeNum));
            badge.setReputationForMerit(5);
            badgesService.persist(badge);
        }
    }

    void bookmarksInit() {
        BookMarks bookMarks = new BookMarks();
        bookMarks.setQuestion(questionService.getById(3L).get());
        bookMarks.setUser(userService.getByEmail("email3@mail.com").get());
        bookmarksService.persist(bookMarks);
    }

    void chatInit() {
        for (int chatNum = 1; chatNum <= CHAT_COUNT; chatNum++) {
            Chat chat = new Chat();
            chat.setPersistDate(LocalDateTime.now());
            if (chatNum == 1) {
                chat.setChatType(ChatType.SINGLE);
                chat.setTitle(String.format("Title %s", chatNum));
            } else {
                chat.setChatType(ChatType.GROUP);
                chat.setTitle(String.format("Title %s", chatNum));
            }
            chatService.persist(chat);
        }
    }

    void commentInit() {
        for (int commentNum = 1; commentNum <= COMMENT_COUNT; commentNum++) {
            Comment comment = new Comment();
            comment.setLastUpdateDateTime(LocalDateTime.now());
            comment.setPersistDateTime(LocalDateTime.now());
            if (commentNum == 1) {
                comment.setCommentType(CommentType.ANSWER);
                comment.setText(String.format("Comment %s", commentNum));
                comment.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                comment.setCommentType(CommentType.QUESTION);
                comment.setText(String.format("Comment %s", commentNum));
                comment.setUser(userService.getByEmail("email3@mail.com").get());
            }
            commentService.persist(comment);
        }
    }

    void commentAnswerInit() {
        for (int commentAnswerNum = 1; commentAnswerNum <= COMMENT_ANSWER_COUNT; commentAnswerNum++) {
            CommentAnswer commentAnswer = new CommentAnswer();
            if (commentAnswerNum == 1) {
                commentAnswer.setAnswer(answerService.getById(1L).get());
                commentAnswer.setUser(userService.getByEmail("email1@mail.com").get());
                commentAnswer.setText(commentService.getById(1L).get().getText());
            } else {
                commentAnswer.setAnswer(answerService.getById(3L).get());
                commentAnswer.setUser(userService.getByEmail("email3@mail.com").get());
                commentAnswer.setText(commentService.getById(3L).get().getText());
            }
            commentAnswerService.persist(commentAnswer);
        }
    }

    void commentQuestionInit() {
        for (int commentQuestionNum = 1; commentQuestionNum <= COMMENT_QUESTION_COUNT; commentQuestionNum++) {
            CommentQuestion commentQuestion = new CommentQuestion();
            commentQuestion.setComment(new Comment(CommentType.QUESTION));
            if (commentQuestionNum == 1) {
                commentQuestion.setText(commentService.getById(2L).get().getText());
                commentQuestion.setQuestion(questionService.getById(2L).get());
                commentQuestion.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                commentQuestion.setText(commentService.getById(3L).get().getText());
                commentQuestion.setQuestion(questionService.getById(3L).get());
                commentQuestion.setUser(userService.getByEmail("email3@mail.com").get());
            }
            commentQuestionService.persist(commentQuestion);
        }
    }

    void groupChatInit() {
        GroupChat groupChat = new GroupChat();
        groupChat.setUsers(Set.of(userService.getByEmail("email2@mail.com").get(),userService.getByEmail("email3@mail.com").get()));
        groupChatService.persist(groupChat);
    }

    void messageInit() {
        for (int messageNum = 1; messageNum <= MESSAGE_COUNT; messageNum++) {
            Message message = new Message();
            message.setLastRedactionDate(LocalDateTime.now());
            message.setPersistDate(LocalDateTime.now());
            if (messageNum == 1) {
                message.setMessage(String.format("Message %s", messageNum));
                message.setChat(chatService.getById(1L).get());
                message.setUserSender(userService.getByEmail("email2@mail.com").get());
            } else {
                message.setMessage(String.format("Message %s", messageNum));
                message.setChat(chatService.getById(2L).get());
                message.setUserSender(userService.getByEmail("email3@mail.com").get());
            }
            messageService.persist(message);
        }
    }

    void questionInit() {
        for (int questionNum = 1; questionNum <= QUESTION_COUNT; questionNum++) {
            Question question = new Question();
            question.setDescription(String.format("Description %s", questionNum));
            question.setIsDeleted(false);
            question.setLastUpdateDateTime(LocalDateTime.now());
            question.setPersistDateTime(LocalDateTime.now());
            question.setTitle(String.format("Question title %s", questionNum));
            if (questionNum == 2) {
                question.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                question.setUser(userService.getByEmail("email3@mail.com").get());
            }
            question.setTags(tags);
            questionService.persist(question);
            questions.add(question);
        }
    }

    void questionViewedInit() {
        for (int questionViewedNum = 1; questionViewedNum <= QUESTION_COUNT; questionViewedNum++) {
            QuestionViewed questionViewed = new QuestionViewed();
            questionViewed.setLocalDateTime(LocalDateTime.now());
            if (questionViewedNum == 2) {
                questionViewed.setQuestion(questionService.getById(2L).get());
                questionViewed.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                questionViewed.setQuestion(questionService.getById(3L).get());
                questionViewed.setUser(userService.getByEmail("email3@mail.com").get());
            }
            questionViewedService.persist(questionViewed);
        }
    }

    void relatedTagInit() {
        RelatedTag relatedTag = new RelatedTag();
        relatedTag.setChildTag(tagService.getById(1L).get());
        relatedTag.setMainTag(tagService.getById(2L).get());
        relatedTagService.persist(relatedTag);
    }

    void reputationInit() {
        for (int reputationNum = 1; reputationNum <= REPUTATION_COUNT; reputationNum++) {
            Reputation reputation = new Reputation();
            reputation.setPersistDate(LocalDateTime.now());
            if (reputationNum == 2) {
                reputation.setCount(5);
                reputation.setType(ReputationType.Answer);
                reputation.setAnswer(answerService.getById(2L).get());
                reputation.setAuthor(userService.getByEmail("email2@mail.com").get());
                reputation.setQuestion(null);
                reputation.setSender(userService.getByEmail("email2@mail.com").get());
            } else {
                reputation.setCount(10);
                reputation.setType(ReputationType.Question);
                reputation.setAnswer(null);
                reputation.setAuthor(userService.getByEmail("email3@mail.com").get());
                reputation.setQuestion(questionService.getById(3L).get());
                reputation.setSender(userService.getByEmail("email3@mail.com").get());
            }
            reputationService.persist(reputation);
        }
    }

    void roleInit() {
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        roleService.persistAll(roleUser,roleAdmin);
    }

    void singleChatInit() {
        SingleChat singleChat = new SingleChat();
        singleChat.setUserOne(userService.getByEmail("email4@mail.com").get());
        singleChat.setUseTwo(userService.getByEmail("email5@mail.com").get());
        singleChatService.persist(singleChat);
    }

    void tagInit() {
        for (int tagNum = 1; tagNum <= TAG_COUNT; tagNum++) {
            Tag tag = new Tag();
            tag.setDescription(String.format("Tag description %s", tagNum));
            tag.setName(String.format("Tag name %s", tagNum));
            tag.setPersistDateTime(LocalDateTime.now());
            tag.setQuestions(questions);
            tagService.persist(tag);
            tags.add(tag);
        }
    }

    void tagIgnoreInit() {
        IgnoredTag ignoredTag = new IgnoredTag();
        ignoredTag.setPersistDateTime(LocalDateTime.now());
        ignoredTag.setIgnoredTag(tagService.getById(1L).get());
        ignoredTag.setUser(userService.getByEmail("email2@mail.com").get());
        ignoredTagService.persist(ignoredTag);
    }

    void tagTrackedInit() {
        TrackedTag trackedTag = new TrackedTag();
        trackedTag.setPersistDateTime(LocalDateTime.now());
        trackedTag.setTrackedTag(tagService.getById(2L).get());
        trackedTag.setUser(userService.getByEmail("email3@mail.com").get());
        trackedTagService.persist(trackedTag);
    }

    void userBadgesInit() {
        UserBadges userBadges = new UserBadges();
        userBadges.setReady(true);
        userBadges.setBadge(badgesService.getById(1L).get());
        userBadges.setUser(userService.getByEmail("email4@mail.com").get());
        userBadgesService.persist(userBadges);
    }

    void userInit() {
        final Role ROLE_USER;
        final Role ROLE_ADMIN;
        Optional<Role> roleUser = roleService.getByName("ROLE_USER");
        Optional<Role> roleAdmin = roleService.getByName("ROLE_ADMIN");
        ROLE_USER = roleUser.orElse(new Role("ROLE_USER"));
        ROLE_ADMIN = roleAdmin.orElse(new Role("ROLE_ADMIN"));

        for (int userNum = 1; userNum <= USER_COUNT; userNum++) {
            User user = new User();
            user.setAbout(String.format("About User %s", userNum));
            user.setCity(String.format("City of User %s", userNum));
            user.setEmail(String.format("email%s@mail.com", userNum));
            user.setFullName(String.format("Full Name User %s", userNum));
            user.setImageLink(String.format("Image Link User %s", userNum));
            user.setIsDeleted(false);
            user.setIsEnabled(true);
            user.setLastUpdateDateTime(LocalDateTime.now());
            user.setLinkGitHub(String.format("Git Hub User %s", userNum));
            user.setLinkSite(String.format("Link Site User %s", userNum));
            user.setLinkVk(String.format("Link VK User %s", userNum));
            user.setNickname(String.format("Nickname User %s", userNum));
            user.setPassword("password");
            user.setPersistDateTime(LocalDateTime.now());
            if (userNum == 1) {
                user.setRole(ROLE_ADMIN);
            } else {
                user.setRole(ROLE_USER);
            }
            userService.persist(user);
        }
    }

    void userFavoriteQuestionInit() {
        UserFavoriteQuestion userFavoriteQuestion = new UserFavoriteQuestion();
        userFavoriteQuestion.setPersistDateTime(LocalDateTime.now());
        userFavoriteQuestion.setQuestion(questionService.getById(3L).get());
        userFavoriteQuestion.setUser(userService.getByEmail("email5@mail.com").get());
        userFavoriteQuestionService.persist(userFavoriteQuestion);
    }

    void votesOnAnswersInit() {
        for (int voteAnswerNum = 1; voteAnswerNum <= VOTE_ANSWERS_COUNT; voteAnswerNum++) {
            VoteAnswer voteAnswer = new VoteAnswer();
            voteAnswer.setPersistDateTime(LocalDateTime.now());
            if (voteAnswerNum == 1) {
                voteAnswer.setVoteType(VoteType.UP);
                voteAnswer.setAnswer(answerService.getById(1L).get());
                voteAnswer.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                voteAnswer.setVoteType(VoteType.DOWN);
                voteAnswer.setAnswer(answerService.getById(2L).get());
                voteAnswer.setUser(userService.getByEmail("email3@mail.com").get());
            }
            voteAnswerService.persist(voteAnswer);
        }
    }

    void votesOnQuestionsInit() {
        for (int voteQuestionNum = 1; voteQuestionNum <= VOTE_QUESTIONS_COUNT; voteQuestionNum++) {
            VoteQuestion voteQuestion = new VoteQuestion();
            voteQuestion.setLocalDateTime(LocalDateTime.now());
            if (voteQuestionNum == 1) {
                voteQuestion.setVote(VoteType.UP);
                voteQuestion.setQuestion(questionService.getById(1L).get());
                voteQuestion.setUser(userService.getByEmail("email2@mail.com").get());
            } else {
                voteQuestion.setVote(VoteType.DOWN);
                voteQuestion.setQuestion(questionService.getById(2L).get());
                voteQuestion.setUser(userService.getByEmail("email3@mail.com").get());
            }
            voteQuestionService.persist(voteQuestion);
        }
    }
}
