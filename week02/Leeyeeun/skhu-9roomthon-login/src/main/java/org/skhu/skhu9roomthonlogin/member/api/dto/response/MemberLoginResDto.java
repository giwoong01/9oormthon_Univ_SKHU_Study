package org.skhu.skhu9roomthonlogin.member.api.dto.response;
import lombok.Builder;
import org.skhu.skhu9roomthonlogin.member.domain.Member;

@Builder
public record MemberLoginResDto(
        String email,
        String nickname,
        String token
){
    public static MemberLoginResDto of(Member member, String token)
    {
        return MemberLoginResDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .token(token)
                .build();
    }
}
