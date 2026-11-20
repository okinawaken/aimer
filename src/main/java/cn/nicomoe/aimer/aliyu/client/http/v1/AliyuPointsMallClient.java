package cn.nicomoe.aimer.aliyu.client.http.v1;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.MemberCouponRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsExchangeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallCurrentStoreRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallMemberCouponListReqDTO;
import cn.nicomoe.aimer.aliyu.model.dto.PointsMallPointsExchangeDetailReqDTO;
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
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

/**
 * 积分商城域。
 */
public interface AliyuPointsMallClient extends AliyuBaseClient {

    /**
     * 当前门店。
     */
    @Get(url = "/aliyuMember/pointsMall/currentStore")
    AliyuClientResult<java.util.List<PointsMallCurrentStoreRespDTO>> currentStore(@Query PointsMallCurrentStoreReqDTO query);

    /**
     * 奖品列表。
     */
    @Get(url = "/aliyuMember/pointsMall/prize/list")
    AliyuClientResult<PrizeListRespDTO> prizeList(@Query(value = "pageNo") Integer pageNo,
                                                  @Query(value = "pageSize") Integer pageSize,
                                                  @Query(value = "cardId") String cardId,
                                                  @Query(value = "storeId") String storeId);

    /**
     * 奖品详情。
     */
    @Get(url = "/aliyuMember/pointsMall/prize/detail")
    AliyuClientResult<PrizeDetailRespDTO> prizeDetail(@Query(value = "id") String id,
                                                      @Query(value = "cardId") String cardId,
                                                      @Query(value = "storeId") String storeId);

    /**
     * 奖品兑换校验。
     */
    @Get(url = "/aliyuMember/pointsMall/prize/check")
    AliyuClientResult<PrizeCheckRespDTO> prizeCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                                    @Query(value = "cardId") String cardId,
                                                    @Query(value = "amount") Integer amount,
                                                    @Query(value = "storeId") String storeId);

    /**
     * 奖品可用门店。
     */
    @Get(url = "/aliyuMember/pointsMall/prize/store")
    AliyuClientResult<java.util.List<PointsMallPrizeStoreRespDTO>> prizeStore(@Query PointsMallPrizeStoreReqDTO query);

    /**
     * 奖品兑换（消耗小鱼丸）。
     */
    @Post(url = "/aliyuMember/pointsMall/prize/exchange")
    AliyuClientResult<PrizeExchangeRespDTO> prizeExchange(@JSONBody PointsMallPrizeExchangeReqDTO body);

    /**
     * 壁纸列表。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/list")
    AliyuClientResult<WallpaperListRespDTO> wallpaperList(@Query PointsMallWallpaperListReqDTO query);

    /**
     * 壁纸详情。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/detail")
    AliyuClientResult<WallpaperDetailRespDTO> wallpaperDetail(@Query(value = "pointsPrizeId") String pointsPrizeId);

    /**
     * 壁纸系列详情。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/seriesDetail")
    AliyuClientResult<WallpaperSeriesRespDTO> wallpaperSeriesDetail(@Query(value = "seriesId") String seriesId);

    /**
     * 壁纸兑换校验。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/check")
    AliyuClientResult<WallpaperCheckRespDTO> wallpaperCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                                            @Query(value = "cardId") String cardId,
                                                            @Query(value = "amount") Integer amount,
                                                            @Query(value = "storeId") String storeId);

    /**
     * 壁纸下载。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/download")
    AliyuClientResult<WallpaperDownloadRespDTO> wallpaperDownload(@Query(value = "materialId") String materialId);

    /**
     * 壁纸装扮首页。
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/dressHome")
    AliyuClientResult<WallpaperDressHomeRespDTO> wallpaperDressHome(@Query(value = "pageNo") Integer pageNo,
                                                                    @Query(value = "pageSize") Integer pageSize);

    /**
     * 壁纸兑换。
     */
    @Post(url = "/aliyuMember/pointsMall/wallpaper/exchange")
    AliyuClientResult<WallpaperExchangeRespDTO> wallpaperExchange(@JSONBody PointsMallWallpaperExchangeReqDTO body);

    /**
     * 会员券详情。
     */
    @Get(url = "/aliyuMember/memberCoupon/detail")
    AliyuClientResult<MemberCouponRespDTO> memberCouponDetail(@Query(value = "memberCouponId") String memberCouponId);

    /**
     * 会员券列表。
     */
    @Post(url = "/aliyuMember/memberCoupon/list")
    AliyuClientResult<MemberCouponListRespDTO> memberCouponList(@JSONBody PointsMallMemberCouponListReqDTO body);

    /**
     * 积分兑换订单详情。
     */
    @Get(url = "/aliyuMember/order/pointsExchange/detail")
    AliyuClientResult<PointsExchangeDetailRespDTO> pointsExchangeDetail(@Query PointsMallPointsExchangeDetailReqDTO query);

    /**
     * 积分兑换订单列表。
     */
    @Post(url = "/aliyuMember/order/pointsExchange/list")
    AliyuClientResult<PointsExchangeListRespDTO> pointsExchangeList(@JSONBody PointsMallPointsExchangeListReqDTO body);
}
