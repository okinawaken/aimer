package cn.nicomoe.aimer.aliyu.service.client;

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

import java.util.List;

/** 阿里鱼积分商城域服务，返回已解包的业务数据。 */
public interface AliyuPointsMallService {

    /** 当前门店。 */
    List<PointsMallCurrentStoreRespDTO> currentStore(PointsMallCurrentStoreReqDTO query);

    /** 奖品列表。 */
    PrizeListRespDTO prizeList(Integer pageNo, Integer pageSize, String cardId, String storeId);

    /** 奖品详情。 */
    PrizeDetailRespDTO prizeDetail(String id, String cardId, String storeId);

    /** 奖品兑换校验。 */
    PrizeCheckRespDTO prizeCheck(String pointsPrizeId, String cardId, Integer amount, String storeId);

    /** 奖品可用门店。 */
    List<PointsMallPrizeStoreRespDTO> prizeStore(PointsMallPrizeStoreReqDTO query);

    /** 奖品兑换（消耗小鱼丸）。 */
    PrizeExchangeRespDTO prizeExchange(PointsMallPrizeExchangeReqDTO form);

    /** 壁纸列表。 */
    WallpaperListRespDTO wallpaperList(PointsMallWallpaperListReqDTO query);

    /** 壁纸详情。 */
    WallpaperDetailRespDTO wallpaperDetail(String pointsPrizeId);

    /** 壁纸系列详情。 */
    WallpaperSeriesRespDTO wallpaperSeriesDetail(String seriesId);

    /** 壁纸兑换校验。 */
    WallpaperCheckRespDTO wallpaperCheck(String pointsPrizeId, String cardId, Integer amount, String storeId);

    /** 壁纸下载。 */
    WallpaperDownloadRespDTO wallpaperDownload(String materialId);

    /** 壁纸装扮首页。 */
    WallpaperDressHomeRespDTO wallpaperDressHome(Integer pageNo, Integer pageSize);

    /** 壁纸兑换。 */
    WallpaperExchangeRespDTO wallpaperExchange(PointsMallWallpaperExchangeReqDTO form);

    /** 会员券详情。 */
    MemberCouponRespDTO memberCouponDetail(String memberCouponId);

    /** 会员券列表。 */
    MemberCouponListRespDTO memberCouponList(PointsMallMemberCouponListReqDTO form);

    /** 积分兑换订单详情。 */
    PointsExchangeDetailRespDTO pointsExchangeDetail();

    /** 积分兑换订单列表。 */
    PointsExchangeListRespDTO pointsExchangeList(PointsMallPointsExchangeListReqDTO form);
}
