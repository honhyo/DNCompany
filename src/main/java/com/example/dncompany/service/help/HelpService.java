package com.example.dncompany.service.help;

import com.example.dncompany.dto.help.HelpListDTO;
import com.example.dncompany.dto.help.HelpDetailDTO;
import com.example.dncompany.dto.help.HelpSearchDTO;
import com.example.dncompany.dto.help.HelpWriteDTO;
import com.example.dncompany.dto.page.PageDTO;
import com.example.dncompany.exception.help.HelpNotFoundException;
import com.example.dncompany.mapper.help.HelpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HelpService {

    private final HelpMapper helpMapper;


    // 게시글 등록
    public void registerHelp(HelpWriteDTO helpWriteDTO, Long usersId) {
        helpWriteDTO.setUsersId(usersId);
        String helpTime = helpWriteDTO.getHelpTime();
        String[] timeArr = helpTime.split("-");
        String startTime = timeArr[0];
        String endTime = timeArr[1];

        String helpDate = helpWriteDTO.getHelpCareDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime startDateTime = LocalDateTime.parse(helpDate + " " + startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));
        LocalDateTime endDateTime = LocalDateTime.parse(helpDate + " " + endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH"));

        helpWriteDTO.setHelpStartTime(startDateTime);
        helpWriteDTO.setHelpEndTime(endDateTime);
        log.info("service - helpWriteDTO: {}", helpWriteDTO);

        helpMapper.insertHelp(helpWriteDTO);
    }

    // 게시글 목록 조회
    public List<HelpListDTO> getHelpList() {
        return helpMapper.selectHelpList();
    }


    // 게시글 상세 조회
    public HelpDetailDTO getHelpDetail(Long helpId) {
        return helpMapper.selectHelpDetail(helpId)
                .orElseThrow(() -> new HelpNotFoundException("게시글을 찾을 수 없습니다."));
    }

    // 검색
    public List<HelpListDTO> searchHelpList(HelpSearchDTO searchDTO) {
        log.info("검색 서비스 호출 - 검색조건: {}", searchDTO);
        List<HelpListDTO> result = helpMapper.searchHelpList(searchDTO);
        log.info("검색 결과: {}", result);
        return result;
    }

    // 등록 가능 여부
    public boolean checkHelpOfferExists(Long helpId) {
        return helpMapper.checkHelpOfferExists(helpId) > 0;
    }


    // 페이징 처리된 목록 조회
    public PageDTO<HelpListDTO> getHelpListWithPaging(int page, int size) {
        log.info("페이징 처리 시작 - page: {}, size: {}", page, size);

        // 전체 게시글 수 조회
        int total = helpMapper.getTotalCount();
        log.info("전체 게시글 수: {}", total);

        // 페이징 처리된 목록 조회
        List<HelpListDTO> list = helpMapper.selectHelpListWithPaging(page, size);
        log.info("조회된 게시글 수: {}", list.size());

        // PageDTO 생성하여 반환
        return new PageDTO<>(page, size, total, list);
    }

    public void deleteMyHelpBoard(Long helpId, Long userId) {
        int result = helpMapper.deleteMyHelpBoard(helpId, userId);
        if (result == 0) {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }

    }
}


















