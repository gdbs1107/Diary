package Diary.diary.Domain.Dto;

import Diary.diary.Domain.entity.Weather;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class DiaryDto {
    private Long id;
    private LocalDateTime diaryDate;
    private String content;
    private Weather weather;
    private String title;

    // Default constructor
    public DiaryDto() {}

    // Parameterized constructor
    @Builder
    public DiaryDto(Long id, LocalDateTime diaryDate, String content, Weather weather, String title) {
        this.id = id;
        this.diaryDate = diaryDate;
        this.content = content;
        this.weather = weather;
        this.title = title;
    }

}
