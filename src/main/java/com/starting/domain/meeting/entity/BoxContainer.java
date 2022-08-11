package com.starting.domain.meeting.entity;

import com.starting.domain.meeting.repository.BoxRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BoxContainer {

    public static final int BOX_KEY_THREE = 3;
    public static final int BOX_KEY_FOUR = 4;
    public static final int BOX_KEY_FIVE = 5;
    private final Map<Integer, Box> boxMap;
    private final BoxRepository boxRepository;

    public BoxContainer(BoxRepository boxRepository) {
        this.boxMap = new HashMap<>();
        this.boxRepository = boxRepository;

        //Box엔티티 내에 tickets를 같이 가져올수있도록 리팩토링 필요
        boxMap.put(BOX_KEY_THREE, boxRepository.getById(3L));
        boxMap.put(BOX_KEY_FOUR, boxRepository.getById(4L));
        boxMap.put(BOX_KEY_FIVE, boxRepository.getById(5L));
    }

    public Box getBoxNum(int number) {
        if (number < BOX_KEY_THREE || number > BOX_KEY_FIVE) {
            throw new IllegalArgumentException("숫자는 3~5사이여야 합니다.");
        }

        return boxMap.get(number);
    }
}