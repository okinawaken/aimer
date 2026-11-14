package cn.nicomoe.aimer.aliyu.client.http.v2;

import cn.nicomoe.aimer.aliyu.client.http.AliyuBaseClient;
import cn.nicomoe.aimer.aliyu.constant.AliyuApiConstants;
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
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;
import com.dtflys.forest.annotation.Var;

/** 积分商城域（显式用户上下文）。奖品 / 壁纸兑换、会员券、积分兑换订单。 */
public interface AliyuPointsMallV2Client extends AliyuBaseClient {

    /** 当前门店（响应为门店数组，见 {@link PointsMallCurrentStoreRespDTO}）。 参数 query：会员卡 ID、定位与已选门店（均有值时小程序才传） 参数 userId：系统用户 ID */
    @Get(url = "/aliyuMember/pointsMall/currentStore")
    AliyuClientResult<java.util.List<PointsMallCurrentStoreRespDTO>> currentStore(@Query PointsMallCurrentStoreReqDTO query,
                                                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 奖品列表。 参数 pageNo：页码参数 pageSize：每页数量 */
    @Get(url = "/aliyuMember/pointsMall/prize/list")
    AliyuClientResult<PrizeListRespDTO> prizeList(@Query(value = "pageNo") Integer pageNo,
                                                  @Query(value = "pageSize") Integer pageSize,
                                                  @Query(value = "cardId") String cardId,
                                                  @Query(value = "storeId") String storeId,
                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 奖品详情。 */
    @Get(url = "/aliyuMember/pointsMall/prize/detail")
    AliyuClientResult<PrizeDetailRespDTO> prizeDetail(@Query(value = "id") String id,
                                                      @Query(value = "cardId") String cardId,
                                                      @Query(value = "storeId") String storeId,
                                                      @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 奖品兑换校验。 */
    @Get(url = "/aliyuMember/pointsMall/prize/check")
    AliyuClientResult<PrizeCheckRespDTO> prizeCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                                    @Query(value = "cardId") String cardId,
                                                    @Query(value = "amount") Integer amount,
                                                    @Query(value = "storeId") String storeId,
                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 奖品可用门店。 参数 query：会员卡 / 门店 ID 与定位（{@code latitude/longitude} 为字符串） 参数 userId：系统用户 ID */
    @Get(url = "/aliyuMember/pointsMall/prize/store")
    AliyuClientResult<java.util.List<PointsMallPrizeStoreRespDTO>> prizeStore(@Query PointsMallPrizeStoreReqDTO query,
                                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 奖品兑换（消耗小鱼丸）。 */
    @Post(url = "/aliyuMember/pointsMall/prize/exchange")
    AliyuClientResult<PrizeExchangeRespDTO> prizeExchange(@JSONBody PointsMallPrizeExchangeReqDTO body,
                                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸列表。 参数 query：分页与可选 IP 编码参数 userId：系统用户 ID */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/list")
    AliyuClientResult<WallpaperListRespDTO> wallpaperList(@Query PointsMallWallpaperListReqDTO query,
                                                          @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸详情。 */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/detail")
    AliyuClientResult<WallpaperDetailRespDTO> wallpaperDetail(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                                              @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸系列详情。 */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/seriesDetail")
    AliyuClientResult<WallpaperSeriesRespDTO> wallpaperSeriesDetail(@Query(value = "seriesId") String seriesId,
                                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸兑换校验。 */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/check")
    AliyuClientResult<WallpaperCheckRespDTO> wallpaperCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                                            @Query(value = "cardId") String cardId,
                                                            @Query(value = "amount") Integer amount,
                                                            @Query(value = "storeId") String storeId,
                                                            @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸下载。 */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/download")
    AliyuClientResult<WallpaperDownloadRespDTO> wallpaperDownload(@Query(value = "materialId") String materialId,
                                                @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸装扮首页。 参数 pageNo：页码参数 pageSize：每页数量 */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/dressHome")
    AliyuClientResult<WallpaperDressHomeRespDTO> wallpaperDressHome(@Query(value = "pageNo") Integer pageNo,
                                                                    @Query(value = "pageSize") Integer pageSize,
                                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 壁纸兑换。 */
    @Post(url = "/aliyuMember/pointsMall/wallpaper/exchange")
    AliyuClientResult<WallpaperExchangeRespDTO> wallpaperExchange(@JSONBody PointsMallWallpaperExchangeReqDTO body,
                                                                  @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 会员券详情。 */
    @Get(url = "/aliyuMember/memberCoupon/detail")
    AliyuClientResult<MemberCouponRespDTO> memberCouponDetail(@Query(value = "memberCouponId") String memberCouponId,
                                                 @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 会员券列表。 */
    @Post(url = "/aliyuMember/memberCoupon/list")
    AliyuClientResult<MemberCouponListRespDTO> memberCouponList(@JSONBody PointsMallMemberCouponListReqDTO body,
                                                                @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 积分兑换订单详情。 */
    @Get(url = "/aliyuMember/order/pointsExchange/detail")
    AliyuClientResult<PointsExchangeDetailRespDTO> pointsExchangeDetail(@Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);

    /** 积分兑换订单列表。 */
    @Post(url = "/aliyuMember/order/pointsExchange/list")
    AliyuClientResult<PointsExchangeListRespDTO> pointsExchangeList(@JSONBody PointsMallPointsExchangeListReqDTO body,
                                                                    @Var(value = AliyuApiConstants.Variable.USER_ID) Long userId);
}
