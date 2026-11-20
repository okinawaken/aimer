package cn.nicomoe.aimer.aliyu.service.client.impl;

import cn.nicomoe.aimer.aliyu.client.http.v1.AliyuPointsMallClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallMemberCouponListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPointsExchangeListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeExchangeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeStoreReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPrizeStoreRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallWallpaperExchangeReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallWallpaperListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PrizeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDownloadRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDressHomeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.WallpaperSeriesRespDTO;
import cn.nicomoe.aimer.aliyu.service.client.AliyuPointsMallService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/** 阿里鱼积分商城域服务实现。 */
@Service
@RequiredArgsConstructor
public class AliyuPointsMallServiceImpl implements AliyuPointsMallService {

    private final AliyuPointsMallClient pointsMallClient;

    @Override
    public List<PointsMallCurrentStoreRespDTO> currentStore(PointsMallCurrentStoreReqDTO query) {
        AliyuClientResult<List<PointsMallCurrentStoreRespDTO>> result = pointsMallClient.currentStore(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PrizeListRespDTO prizeList(Integer pageNo, Integer pageSize, String cardId, String storeId) {
        AliyuClientResult<PrizeListRespDTO> result = pointsMallClient.prizeList(pageNo, pageSize, cardId, storeId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PrizeDetailRespDTO prizeDetail(String id, String cardId, String storeId) {
        AliyuClientResult<PrizeDetailRespDTO> result = pointsMallClient.prizeDetail(id, cardId, storeId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PrizeCheckRespDTO prizeCheck(String pointsPrizeId, String cardId, Integer amount, String storeId) {
        AliyuClientResult<PrizeCheckRespDTO> result = pointsMallClient.prizeCheck(pointsPrizeId, cardId, amount, storeId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public List<PointsMallPrizeStoreRespDTO> prizeStore(PointsMallPrizeStoreReqDTO query) {
        AliyuClientResult<List<PointsMallPrizeStoreRespDTO>> result = pointsMallClient.prizeStore(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PrizeExchangeRespDTO prizeExchange(PointsMallPrizeExchangeReqDTO form) {
        AliyuClientResult<PrizeExchangeRespDTO> result = pointsMallClient.prizeExchange(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperListRespDTO wallpaperList(PointsMallWallpaperListReqDTO query) {
        AliyuClientResult<WallpaperListRespDTO> result = pointsMallClient.wallpaperList(query);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperDetailRespDTO wallpaperDetail(String pointsPrizeId) {
        AliyuClientResult<WallpaperDetailRespDTO> result = pointsMallClient.wallpaperDetail(pointsPrizeId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperSeriesRespDTO wallpaperSeriesDetail(String seriesId) {
        AliyuClientResult<WallpaperSeriesRespDTO> result = pointsMallClient.wallpaperSeriesDetail(seriesId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperCheckRespDTO wallpaperCheck(String pointsPrizeId, String cardId, Integer amount, String storeId) {
        AliyuClientResult<WallpaperCheckRespDTO> result = pointsMallClient.wallpaperCheck(pointsPrizeId, cardId, amount, storeId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperDownloadRespDTO wallpaperDownload(String materialId) {
        AliyuClientResult<WallpaperDownloadRespDTO> result = pointsMallClient.wallpaperDownload(materialId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperDressHomeRespDTO wallpaperDressHome(Integer pageNo, Integer pageSize) {
        AliyuClientResult<WallpaperDressHomeRespDTO> result = pointsMallClient.wallpaperDressHome(pageNo, pageSize);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public WallpaperExchangeRespDTO wallpaperExchange(PointsMallWallpaperExchangeReqDTO form) {
        AliyuClientResult<WallpaperExchangeRespDTO> result = pointsMallClient.wallpaperExchange(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberCouponRespDTO memberCouponDetail(String memberCouponId) {
        AliyuClientResult<MemberCouponRespDTO> result = pointsMallClient.memberCouponDetail(memberCouponId);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public MemberCouponListRespDTO memberCouponList(PointsMallMemberCouponListReqDTO form) {
        AliyuClientResult<MemberCouponListRespDTO> result = pointsMallClient.memberCouponList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PointsExchangeDetailRespDTO pointsExchangeDetail() {
        AliyuClientResult<PointsExchangeDetailRespDTO> result = pointsMallClient.pointsExchangeDetail();
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }

    @Override
    public PointsExchangeListRespDTO pointsExchangeList(PointsMallPointsExchangeListReqDTO form) {
        AliyuClientResult<PointsExchangeListRespDTO> result = pointsMallClient.pointsExchangeList(form);
        if (Objects.isNull(result)) {
            return null;
        }
        return result.getData();
    }
}
