package cn.nicomoe.aimer.aliyu.model.vo;

import cn.nicomoe.aimer.aliyu.model.bo.AliyuUserSettingConfigBO;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/** 阿里鱼用户配置明细的响应视图，原样返回配置数据，Cookie 不做加工。 */
@Data
@AutoMapper(target = AliyuUserSettingConfigBO.class)
public class AliyuUserConfigDetailVO {

    /** 持久会话 Cookie（JSESSIONID + MZCONSUMERJSESSIONID 等） */
    private String cookie;
}
