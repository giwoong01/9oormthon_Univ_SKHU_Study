package com.goormthon.everytime.app.dto.user.resDto;

import com.goormthon.everytime.app.domain.user.User;
import lombok.Builder;

@Builder
public record UserInfoResDto(
        Long userId,
        String id,
        String name,
        String nickName,
        String year,
        String universityName
) {
    public static UserInfoResDto from(User user) {
        return UserInfoResDto.builder()
                .userId(user.getUserId())
                .id(user.getId())
                .name(user.getName())
                .nickName(user.getNickName())
                .year(user.getYear())
                .universityName(user.getUniversity().getDisplayName())
                .build();
    }
}
