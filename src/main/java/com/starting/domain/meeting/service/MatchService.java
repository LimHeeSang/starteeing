package com.starting.domain.meeting.service;

import com.starting.domain.meeting.dto.MatchListResponseDto;
import com.starting.domain.meeting.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MatchService {

    private final MatchRepository matchRepository;

    /**
     * 매칭 정보 조회(페이징)
     */
    public Page<MatchListResponseDto> getMatches(Pageable pageable) {
        return matchRepository.findAll(pageable).map(MatchListResponseDto::new);
    }
}
