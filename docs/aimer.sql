-- ----------------------------
-- 系统授权表
-- ----------------------------
drop table if exists sys_client;
create table sys_client
(
    id             bigint(20) not null auto_increment comment 'id',
    client_id      varchar(64)  not null default '' comment '客户端id',
    client_key     varchar(32)  default '' comment '客户端key',
    client_secret  varchar(255) default '' comment '客户端秘钥',
    grant_type     varchar(255) default '' comment '授权类型',
    device_type    varchar(32)  default '' comment '设备类型',
    active_timeout int(11)      default 1800 comment 'token活跃超时时间',
    timeout        int(11)      default 604800 comment 'token固定超时',
    status         tinyint(4)   default 0 comment '状态（0正常 1停用）',
    deleted        char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    create_dept    bigint(20)   default '' comment '创建部门',
    create_by      bigint(20)   default '' comment '创建者',
    create_time    datetime     default '' comment '创建时间',
    update_by      bigint(20)   default '' comment '更新者',
    update_time    datetime     default '' comment '更新时间',
    primary key (id)
) engine = innodb comment ='系统授权表';


-- ----------------------------
-- 参数配置表
-- ----------------------------
drop table if exists sys_config;
create table sys_config
(
    config_id    bigint(20) not null comment '参数主键',
    tenant_id    varchar(20)  default '000000' comment '租户编号',
    config_name  varchar(100) default '' comment '参数名称',
    config_key   varchar(100) default '' comment '参数键名',
    config_value varchar(500) default '' comment '参数键值',
    config_type  char(1)      default 'N' comment '系统内置（Y是 N否）',
    create_dept  bigint(20)   default '' comment '创建部门',
    create_by    bigint(20)   default '' comment '创建者',
    create_time  datetime comment '创建时间',
    update_by    bigint(20)   default '' comment '更新者',
    update_time  datetime comment '更新时间',
    remark       varchar(500) default '' comment '备注',
    primary key (config_id)
) engine = innodb comment = '参数配置表';


-- ----------------------------
-- 字典数据表
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
    dict_code   bigint(20) not null comment '字典编码',
    tenant_id   varchar(20)  default '000000' comment '租户编号',
    dict_sort   int(4)       default 0 comment '字典排序',
    dict_label  varchar(100) default '' comment '字典标签',
    dict_value  varchar(100) default '' comment '字典键值',
    dict_type   varchar(100) default '' comment '字典类型',
    css_class   varchar(100) default '' comment '样式属性（其他样式扩展）',
    list_class  varchar(100) default '' comment '表格回显样式',
    is_default  char(1)      default 'N' comment '是否默认（Y是 N否）',
    create_dept bigint(20)   default '' comment '创建部门',
    create_by   bigint(20)   default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   bigint(20)   default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(500) default '' comment '备注',
    primary key (dict_code)
) engine = innodb comment = '字典数据表';