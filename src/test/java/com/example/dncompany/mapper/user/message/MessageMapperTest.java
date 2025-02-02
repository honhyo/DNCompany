package com.example.dncompany.mapper.user.message;

import com.example.dncompany.dto.user.message.MessagePageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MessageMapperTest {
    @Autowired
    MessageMapper messageMapper;

    MessagePageDTO messagePageDTO;

    @BeforeEach
    void setUp() {
        // messagePageDTO 객체를 초기화하거나 실제 데이터를 넣어줍니다.
        messagePageDTO = new MessagePageDTO();
        messagePageDTO.setUsersId(42L);
        messagePageDTO.setUserFrom(42L);  // 예시로 42를 설정
        messagePageDTO.setUserTo(42L);  // 예시로 42를 설정
    }

    @Test
    void selectfromMessage() {
        // given

        // when
        List<MessagePageDTO> messagePageDTOList = messageMapper.selectFromMessage(messagePageDTO.getUsersId());
        // then
        assertThat(messagePageDTOList).isNotEmpty()
                .extracting("userFrom")
                .contains (42L);
    }

    @Test
    void selectToMessage() {
        // given

        // when
        List<MessagePageDTO> messagePageDTOList = messageMapper.selectToMessage(messagePageDTO.getUsersId());
        // then
        assertThat(messagePageDTOList).isNotEmpty()
                .extracting("userTo")
                .contains (42L);
    }


    @Test
    public void testCountByTotalFrom() {
        // given
        Long userFrom = 42L; // 존재하는 userFrom ID 값

        // when
        int result = messageMapper.countByTotalFrom(userFrom);

        // then
        System.out.println("Count by Page From: " + result);
        Assertions.assertTrue(result >= 0, "결과는 0 이상이어야 합니다.");
    }

    @Test
    public void testCountByTotalTo() {
        // given
        Long userTo = 42L; // 존재하는 userTo ID 값

        // when
        int result = messageMapper.countByTotalTo(userTo);

        // then
        System.out.println("Count by Page To: " + result);
        Assertions.assertTrue(result >= 0, "결과는 0 이상이어야 합니다.");
    }

    @Test
    public void testCountByPageWithBothParameters() {
        // given
        Long userFrom = 43L; // 존재하는 userFrom ID 값
        Long userTo = 42L;   // 존재하는 userTo ID 값

        // when
        int resultFrom = messageMapper.countByTotalFrom(userFrom);
        int resultTo = messageMapper.countByTotalTo(userTo);

        // then
        System.out.println("Count by Page From: " + resultFrom);
        System.out.println("Count by Page To: " + resultTo);
        Assertions.assertTrue(resultFrom >= 0, "결과는 0 이상이어야 합니다.");
        Assertions.assertTrue(resultTo >= 0, "결과는 0 이상이어야 합니다.");
    }

    @Test
    void deletemessageList() {
        Long messageId = 33L;

    }

}