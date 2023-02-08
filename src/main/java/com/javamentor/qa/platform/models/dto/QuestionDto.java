package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.Tag;
import com.javamentor.qa.platform.models.entity.question.VoteType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Вопрос dto")
public class QuestionDto {
    @Parameter(description = "ID вопроса")
    private Long id;
    @Schema(description = "Заголовок")
    private String title;
    @Schema(description = "ID автора")
    private Long authorId;
    @Schema(description = "Имя Автора")
    private String authorName;
    @Schema(description = "Аватар автора")
    private String authorImage;
    @Schema(description = "Содержание вопроса")
    private String description;
    @Schema(description = "Количество просмотров")
    private Long viewCount;
    @Schema(description = "Репутация автора")
    private Long authorReputation;
    @Schema(description = "Счетчик ответов")
    private Long countAnswer;
    @Schema(description = "Счетчик рейтинга")
    private Long countValuable;
    @Schema(description = "Дата создания вопроса")
    private LocalDateTime persistDateTime;
    @Schema(description = "Дата последнего обновления вопроса")
    private LocalDateTime lastUpdateDateTime;
    @Schema(description = "Кол-во голосов за вопрос")
    private Long countVote;
    @Schema(description = "Голос авторизованного пользователя за вопрос")
    private VoteType voteType;
//    @Schema(description = "Список тегов")
//    private List<Tag> listTagDto;
}

