package com.starteeing.golbal.response;

import com.starteeing.domain.member.entity.UserMember;
import com.starteeing.golbal.response.result.CommonResult;
import com.starteeing.golbal.response.result.ListResult;
import com.starteeing.golbal.response.result.SingleResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResponseServiceTest {

    private ResponseService responseService = new ResponseService();

    private UserMember userMember;
    private UserMember userMember1;
    private UserMember userMember2;
    private UserMember userMember3;
    private List<UserMember> userMembers;

    @BeforeEach
    void setUp() {
        userMember = UserMember.builder().build();

        userMember1 = UserMember.builder().build();
        userMember2 = UserMember.builder().build();
        userMember3 = UserMember.builder().build();
        userMembers = Arrays.asList(userMember1, userMember2, userMember3);
    }

    @Test
    void 단일결과_성공처리() {
        SingleResult<UserMember> result = responseService.createSingleResult(userMember);

        assertThat(result.getData()).isEqualTo(userMember);
        assertThat(result.getCode()).isEqualTo(CommonExEnum.SUCCESS.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.SUCCESS.getMessage());
    }

    @Test
    void 복수결과_성공처리() {
        ListResult<UserMember> result = responseService.createListResult(userMembers);

        assertThat(result.getData()).contains(userMember1, userMember2, userMember3);
        assertThat(result.getCode()).isEqualTo(CommonExEnum.SUCCESS.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.SUCCESS.getMessage());
    }

    @Test
    void 성공결과만_처리() {
        CommonResult result = responseService.createSuccessResult();

        assertThat(result.getCode()).isEqualTo(CommonExEnum.SUCCESS.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.SUCCESS.getMessage());
    }

    @Test
    void 실패결과만_처리() {
        CommonResult result = responseService.createFailResult();

        assertThat(result.getCode()).isEqualTo(CommonExEnum.FAIL.getCode());
        assertThat(result.getMessage()).isEqualTo(CommonExEnum.FAIL.getMessage());
    }
}