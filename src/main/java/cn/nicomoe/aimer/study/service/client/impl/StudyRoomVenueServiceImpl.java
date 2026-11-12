package cn.nicomoe.aimer.study.service.client.impl;

import cn.nicomoe.aimer.study.client.http.v1.StudyRoomVenueClient;
import cn.nicomoe.aimer.study.client.http.v2.StudyRoomVenueV2Client;
import cn.nicomoe.aimer.study.constant.StudyRoomVenueConstants;
import cn.nicomoe.aimer.study.model.dto.StudyRoomAreaSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomClientResult;
import cn.nicomoe.aimer.study.model.dto.StudyRoomDouYinVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomReserveIndexClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSelectClientRespDTO;
import cn.nicomoe.aimer.study.model.dto.StudyRoomVenueSimpleClientRespDTO;
import cn.nicomoe.aimer.study.model.query.StudyRoomReserveIndexQuery;
import cn.nicomoe.aimer.study.service.client.StudyRoomVenueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 自习室门店域服务实现。
 *
 * @author bixiu
 * @since 4.2.0
 */
@Service
@RequiredArgsConstructor
public class StudyRoomVenueServiceImpl implements StudyRoomVenueService {

    private final StudyRoomVenueClient venueClient;
    private final StudyRoomVenueV2Client venueV2Client;

    @Override
    public StudyRoomVenueSelectClientRespDTO venueSelect(Double currentLng, Double currentLat) {
        StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> result = venueClient.venueSelect(currentLng, currentLat);
        if (Objects.isNull(result)){
            return null;
        }
        StudyRoomVenueSelectClientRespDTO clientRespDTO = result.getData();
        List<StudyRoomVenueSimpleClientRespDTO> clientRespDTOList = clientRespDTO.getVenueList()
                .stream()
                .filter(Objects::nonNull)
                .filter(venue -> !Objects.equals(StudyRoomVenueConstants.DEPOSIT_ONLY_VENUE_NAME, venue.getVenueName()))
                .toList();
        clientRespDTO.setVenueList(clientRespDTOList);
        return clientRespDTO;
    }

    @Override
    public StudyRoomVenueSelectClientRespDTO reserveVenueSelect() {
        StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> result = venueClient.reserveVenueSelect();
        if (Objects.isNull(result)) {
            return null;
        }
        StudyRoomVenueSelectClientRespDTO clientRespDTO = result.getData();
        List<StudyRoomVenueSimpleClientRespDTO> clientRespDTOList = clientRespDTO.getVenueList()
                .stream()
                .filter(Objects::nonNull)
                .filter(venue -> !Objects.equals(StudyRoomVenueConstants.DEPOSIT_ONLY_VENUE_NAME, venue.getVenueName()))
                .toList();
        clientRespDTO.setVenueList(clientRespDTOList);
        return clientRespDTO;
    }

    @Override
    public StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId) {
        StudyRoomClientResult<StudyRoomVenueClientRespDTO> result = venueClient.reserveVenueDetail(venueId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomVenueClientRespDTO reserveVenueDetail(Integer venueId, Long userId) {
        StudyRoomClientResult<StudyRoomVenueClientRespDTO> result = venueV2Client.reserveVenueDetail(venueId, userId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomReserveIndexClientRespDTO reserveIndex(StudyRoomReserveIndexQuery query) {
        StudyRoomClientResult<StudyRoomReserveIndexClientRespDTO> result = venueClient.reserveIndex(query.getCurrentLng(), query.getCurrentLat(), query.getVenueName(), query.getChannelBili(), query.getAreaId());
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomAreaSelectClientRespDTO reserveAreaSelect() {
        StudyRoomClientResult<StudyRoomAreaSelectClientRespDTO> result = venueClient.reserveAreaSelect();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomVenueSelectClientRespDTO receiptVenueSelect(Double currentLng, Double currentLat) {
        StudyRoomClientResult<StudyRoomVenueSelectClientRespDTO> result = venueClient.receiptVenueSelect(currentLng, currentLat);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public StudyRoomDouYinVenueSelectClientRespDTO douYinVenueSelect(Double currentLng, Double currentLat) {
        StudyRoomClientResult<StudyRoomDouYinVenueSelectClientRespDTO> result = venueClient.douYinVenueSelect(currentLng, currentLat);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
