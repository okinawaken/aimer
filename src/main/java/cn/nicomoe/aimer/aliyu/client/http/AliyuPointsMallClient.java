package cn.nicomoe.aimer.aliyu.client.http;

import cn.nicomoe.aimer.aliyu.model.dto.AliyuClientResult;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.JSONBody;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.Query;

import java.util.Map;
import cn.nicomoe.aimer.aliyu.model.dto.resp.MemberCouponListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PointsExchangeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PrizeCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PrizeDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PrizeExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.PrizeListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperCheckRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperDetailRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperDressHomeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperExchangeRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperListRespDTO;
import cn.nicomoe.aimer.aliyu.model.dto.resp.WallpaperSeriesRespDTO;

/**
 * 积分商城域：{@code /aliyuMember}。奖品 / 壁纸兑换、会员券、积分兑换订单。
 *
 * @author bixiu
 * @since 4.3.0
 */
public interface AliyuPointsMallClient extends AliyuBaseClient {

    // ────────────── 奖品 ──────────────

    /**
     * 当前门店。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/currentStore")
    AliyuClientResult<java.util.List<Object>> currentStore();

    /**
     * 奖品列表。
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @param cardId   卡 ID
     * @param storeId  门店 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/prize/list")
    AliyuClientResult<PrizeListRespDTO> prizeList(@Query(value = "pageNo") Integer pageNo,
                                        @Query(value = "pageSize") Integer pageSize,
                                        @Query(value = "cardId") String cardId,
                                        @Query(value = "storeId") String storeId);

    /**
     * 奖品详情。
     *
     * @param id      奖品 ID
     * @param cardId  卡 ID
     * @param storeId 门店 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/prize/detail")
    AliyuClientResult<PrizeDetailRespDTO> prizeDetail(@Query(value = "id") String id,
                                          @Query(value = "cardId") String cardId,
                                          @Query(value = "storeId") String storeId);

    /**
     * 奖品兑换校验。
     *
     * @param pointsPrizeId 积分奖品 ID
     * @param cardId        卡 ID
     * @param amount        兑换数量
     * @param storeId       门店 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/prize/check")
    AliyuClientResult<PrizeCheckRespDTO> prizeCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                         @Query(value = "cardId") String cardId,
                                         @Query(value = "amount") Integer amount,
                                         @Query(value = "storeId") String storeId);

    /**
     * 奖品可用门店。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/prize/store")
    AliyuClientResult<Object> prizeStore();

    /**
     * 奖品兑换（消耗小鱼丸，入参 {@code pointsPrizeId, amount, cardId, storeId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuMember/pointsMall/prize/exchange")
    AliyuClientResult<PrizeExchangeRespDTO> prizeExchange(@JSONBody Map<String, Object> body);

    // ────────────── 壁纸 ──────────────

    /**
     * 壁纸列表。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/list")
    AliyuClientResult<WallpaperListRespDTO> wallpaperList();

    /**
     * 壁纸详情。
     *
     * @param pointsPrizeId 积分奖品 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/detail")
    AliyuClientResult<WallpaperDetailRespDTO> wallpaperDetail(@Query(value = "pointsPrizeId") String pointsPrizeId);

    /**
     * 壁纸系列详情。
     *
     * @param seriesId 系列 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/seriesDetail")
    AliyuClientResult<WallpaperSeriesRespDTO> wallpaperSeriesDetail(@Query(value = "seriesId") String seriesId);

    /**
     * 壁纸兑换校验。
     *
     * @param pointsPrizeId 积分奖品 ID
     * @param cardId        卡 ID
     * @param amount        兑换数量
     * @param storeId       门店 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/check")
    AliyuClientResult<WallpaperCheckRespDTO> wallpaperCheck(@Query(value = "pointsPrizeId") String pointsPrizeId,
                                             @Query(value = "cardId") String cardId,
                                             @Query(value = "amount") Integer amount,
                                             @Query(value = "storeId") String storeId);

    /**
     * 壁纸下载。
     *
     * @param materialId 素材 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/download")
    AliyuClientResult<Object> wallpaperDownload(@Query(value = "materialId") String materialId);

    /**
     * 壁纸装扮首页。
     *
     * @param pageNo   页码
     * @param pageSize 每页数量
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/pointsMall/wallpaper/dressHome")
    AliyuClientResult<WallpaperDressHomeRespDTO> wallpaperDressHome(@Query(value = "pageNo") Integer pageNo,
                                                 @Query(value = "pageSize") Integer pageSize);

    /**
     * 壁纸兑换（入参 {@code pointsPrizeId, amount, storeId}）。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuMember/pointsMall/wallpaper/exchange")
    AliyuClientResult<WallpaperExchangeRespDTO> wallpaperExchange(@JSONBody Map<String, Object> body);

    // ────────────── 会员券 / 兑换订单 ──────────────

    /**
     * 会员券详情。
     *
     * @param memberCouponId 会员券 ID
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/memberCoupon/detail")
    AliyuClientResult<Object> memberCouponDetail(@Query(value = "memberCouponId") String memberCouponId);

    /**
     * 会员券列表。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuMember/memberCoupon/list")
    AliyuClientResult<MemberCouponListRespDTO> memberCouponList(@JSONBody Map<String, Object> body);

    /**
     * 积分兑换订单详情。
     *
     * @return 响应数据
     */
    @Get(url = "/aliyuMember/order/pointsExchange/detail")
    AliyuClientResult<Object> pointsExchangeDetail();

    /**
     * 积分兑换订单列表。
     *
     * @param body 请求参数
     * @return 响应数据
     */
    @Post(url = "/aliyuMember/order/pointsExchange/list")
    AliyuClientResult<PointsExchangeListRespDTO> pointsExchangeList(@JSONBody Map<String, Object> body);
}
