package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.dto.WallpaperDownloadRespDTO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * 壁纸下载响应（{@code GET /aliyuMember/pointsMall/wallpaper/download?materialId=}）。
 */
@Data
@AutoMapper(target = WallpaperDownloadRespDTO.class)
public class WallpaperDownloadVO {

    /**
     * 素材 ID
     */
    private Long materialId;

    /**
     * 素材名称
     */
    private String materialName;

    /**
     * 带签名的原始下载地址
     */
    private String originSignedUrl;

    /**
     * 下载地址过期时间
     */
    private String expireTime;
}
