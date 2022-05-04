package com.univteeing.domain.freinds.service;

import com.univteeing.domain.dto.FriendDto;
import com.univteeing.domain.freinds.entity.Friend;
import com.univteeing.domain.freinds.repository.FriendRepository;
import com.univteeing.domain.member.entity.UserMember;
import com.univteeing.domain.member.repository.UserMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserMemberRepository memberRepository;

    /**
     * 친구요청 & 재요청
     */
    public void requestFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        //이전에 요청을 보냈던적이 있었던 경우
        if (friendRepository.existsByUserMemberAndFriendId(toMember, fromMember.getId())) {
            Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).get();
            Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).get();

            toFriend.reRequestFriend();
            fromFriend.reResponseFriend();
            return;
        }

        //처음 요청을 보내는 경우
        toMember.requestFriend(fromMember);
    }

    /**
     * 친구수락
     */
    public void acceptFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );

        toFriend.acceptFriend();
        fromFriend.acceptFriend();
    }

    /**
     * 친구요청거절
     */
    public void rejectFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );

        toFriend.rejectFriend();
        fromFriend.rejectFriend();
    }

    /**
     * 친구삭제
     */
    public void deleteFriend(Long toMemberId, String fromNickname) {
        UserMember toMember = getUserMemberForId(toMemberId);
        UserMember fromMember = getUserMemberForNickname(fromNickname);

        Friend toFriend = friendRepository.findByUserMemberAndFriendId(toMember, fromMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );
        Friend fromFriend = friendRepository.findByUserMemberAndFriendId(fromMember, toMember.getId()).orElseThrow(
                () -> new NoSuchElementException("이전 요청이 없습니다.")
        );

        toFriend.deleteFriend();
        fromFriend.deleteFriend();
    }

    /**
     * 친구리스트 조회
     */
    public List<FriendDto> getFriendsList(Long memberId) {
        UserMember userMember = getUserMemberForId(memberId);
        List<Friend> friends = friendRepository.findAllByUserMember(userMember);

        List<String> nicknames = memberRepository.findNicknamesByIdList(mapFriendsToIdList(friends));
        List<FriendDto> friendDtos = mapFriendsToFriendDtos(friends);

        addNicknames(friendDtos, nicknames);
        return friendDtos;
    }

    private List<FriendDto> mapFriendsToFriendDtos(List<Friend> friends) {
        List<FriendDto> friendDtos = friends.stream()
                .map(FriendDto::new)
                .collect(Collectors.toList());
        return friendDtos;
    }

    private List<Long> mapFriendsToIdList(List<Friend> friends) {
        List<Long> idList = friends.stream()
                .map(friend -> friend.getFriendId())
                .collect(Collectors.toList());
        return idList;
    }

    private void addNicknames(List<FriendDto> friendDtos, List<String> nicknames) {
        int idx = 0;
        for (FriendDto friendDto : friendDtos) {
            friendDto.setNickName(nicknames.get(idx++));
        }
    }

    private UserMember getUserMemberForNickname(String fromNickname) {
        return memberRepository.findByNickName(fromNickname).orElseThrow(
                () -> new NoSuchElementException("찾으려는 멤버가 없습니다.")
        );
    }

    private UserMember getUserMemberForId(Long toMemberId) {
        return memberRepository.findById(toMemberId).orElseThrow(
                () -> new NoSuchElementException("찾으려는 멤버가 없습니다.")
        );
    }
}
