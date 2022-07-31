package com.starting.domain.friends.service;

import com.starting.domain.friends.dto.FriendListResponseDto;
import com.starting.domain.friends.entity.Friend;
import com.starting.domain.friends.entity.FriendStatus;
import com.starting.domain.friends.exception.NotExistFriendException;
import com.starting.domain.friends.exception.NotExistPreRequest;
import com.starting.domain.friends.repository.FriendRepository;
import com.starting.domain.member.entity.UserMember;
import com.starting.domain.member.exception.NotExistMemberException;
import com.starting.domain.member.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserMemberRepository userMemberRepository;

    /**
     * 친구요청 & 재요청
     */
    public void requestFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        //이전에 요청을 보냈던적이 있었던 경우
        if (friendRepository.existsByUserMemberAndFriendId(toMember, fromMember.getId())) {
            Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                    NotExistFriendException::new
            );
            Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                    NotExistFriendException::new
            );

            toFriend.reRequestFriend();
            fromFriend.reResponseFriend();
            return;
        }

        //처음 요청을 보내는 경우
        toMember.requestFriend(fromMember);
    }

    /**
     * 친구 수락
     */
    public void acceptFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );

        toFriend.acceptFriend();
        fromFriend.acceptFriend();
    }

    /**
     * 친구 요청 거절
     */
    public void rejectFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );

        toFriend.rejectFriend();
        fromFriend.rejectFriend();
    }

    /**
     * 친구 삭제
     */
    public void deleteFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                NotExistPreRequest::new
        );

        toFriend.deleteFriend();
        fromFriend.deleteFriend();
    }

    /**
     * 친구 리스트 조회
     */
    @Transactional(readOnly = true)
    public FriendListResponseDto getFriendsList(Long memberId) {
        UserMember userMember = getUserMemberForId(memberId);

        List<Friend> friends = friendRepository.findAllByUserMember(userMember);
        List<String> nicknames = userMemberRepository.findNicknamesByIdList(mapFriendsToIdList(friends));

        return createFriendListResponseDto(friends, nicknames);
    }

    /**
     * 친구로 등록된 친구리스트 조회
     */
    @Transactional(readOnly = true)
    public FriendListResponseDto getAcceptFriendsList(Long memberId) {
        UserMember findMember = getUserMemberForId(memberId);

        List<Friend> friends = friendRepository.findAllByUserMemberAndFriendsStatus(findMember, FriendStatus.ACCEPT);
        List<String> nicknames = userMemberRepository.findNicknamesByIdList(mapFriendsToIdList(friends));

        return createFriendListResponseDto(friends, nicknames);
    }

    private FriendListResponseDto createFriendListResponseDto(List<Friend> friends, List<String> nicknames) {
        return FriendListResponseDto.builder()
                .friends(friends)
                .nicknames(nicknames)
                .build();
    }

    private List<Long> mapFriendsToIdList(List<Friend> friends) {
        List<Long> idList = friends.stream()
                .map(friend -> friend.getFriendId())
                .collect(Collectors.toList());
        return idList;
    }

    private UserMember getUserMemberForNickname(String fromNickname) {
        return userMemberRepository.findByNickName(fromNickname).orElseThrow(NotExistMemberException::new);
    }

    private UserMember getUserMemberForId(Long toMemberId) {
        return userMemberRepository.findById(toMemberId).orElseThrow(NotExistMemberException::new);
    }
}
