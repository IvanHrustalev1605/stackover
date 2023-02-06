package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "вопрос")
@Setter
public class QuestionDto {

    @Parameter(description = "вопроса")
    private Long id;

    @Schema(description = "заголовок вопроса")
    private String title;

    @Schema(description = "id автора")
    private Long authorId;

    @Schema(description = "имя автора")
    private String authorName;

    @Schema(description = "ссылка на изображение автора")
    private String authorImage;

    @Schema(description = "описание вопроса")
    private String description;

    @Schema(description = "количество просмотров")
    private Long viewCount;

    @Schema(description = "репутация автора")
    private Long authorReputation;

    @Schema(description = "количество ответов на вопрос")
    private Long countAnswer;

    @Schema(description = "рейтинг вопроса")
    private Long countValuable;

    @Schema(description = "дата создания вопроса")
    private LocalDateTime persistDateTime;

    @Schema(description = "дата последнего обновления")
    private LocalDateTime lastUpdateDateTime;

    @Schema(description = "кол-во голосов за вопрос")
    private Long countVote;

    @Schema(description = "голос авторизованного пользователя за вопрос")
    private VoteType voteType;

//    @Schema(description = "список тегов")
//    private List<TagDto> listTagDto;
}
