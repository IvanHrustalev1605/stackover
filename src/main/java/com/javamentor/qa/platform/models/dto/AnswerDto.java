package com.javamentor.qa.platform.models.dto;

import com.javamentor.qa.platform.models.entity.question.VoteType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Data
public class AnswerDto {
    @ApiModelProperty(notes = "id ответа на вопрос")
    private Long id;

    @ApiModelProperty(notes = "id пользователя")
    private Long userId;

    @ApiModelProperty(notes = "id вопроса")
    private Long questionId;

    @ApiModelProperty(notes = "текст ответа")
    @NotEmpty
    @NotBlank
    @NotNull
    private String body;

    @ApiModelProperty(notes = "дата создания ответа")
    private LocalDateTime persistDate;

    @ApiModelProperty(notes = "польза ответа")
    private Boolean isHelpful;

    @ApiModelProperty(notes = "дата решения вопроса")
    private LocalDateTime dateAccept;

    @ApiModelProperty(notes = "рейтинг ответа")
    private Long countValuable;

    @ApiModelProperty(notes = "рейтинг юзера")
    private Long countUserReputation;

    @ApiModelProperty(notes = "ссылка на картинку пользователя")
    private String image;

    @ApiModelProperty(notes = "никнейм пользователя")
    private String nickname;

    @ApiModelProperty(notes = "количество голосов")
    private Long countVote;

    @ApiModelProperty(notes = "тип голоса")
    private VoteType voteType;

}
