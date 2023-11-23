create table correction
(
    id      int auto_increment
        primary key,
    weekday enum ('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat') not null comment '星期',
    JXLMC   varchar(32)                                            not null comment '教学楼名称',
    jsmph   varchar(32)                                            not null comment '教室门牌号',
    JASDM   varchar(10)                                            not null comment '教室代码',
    SKZWS   int        default -1                                  not null comment '座位数',
    zylxdm  varchar(2) default '04'                                not null comment '类型代码',
    jc_ks   smallint                                               not null comment '节次_开始',
    jc_js   smallint                                               not null comment '节次_结束',
    jyytms  text                                                   not null comment '借用用途说明',
    kcm     text                                                   not null comment '课程名',
    SFYXZX  bit        default b'1'                                not null comment '是否允许自习'
)
    comment '校正表';

create index correction_jasdm_index
    on correction (JASDM);

create index correction_jxl_index
    on correction (JXLMC);

create index correction_weekday_index
    on correction (weekday);

create table feedback
(
    id    int auto_increment
        primary key,
    time  timestamp default CURRENT_TIMESTAMP not null comment '日期',
    jc    smallint                            not null comment '节次',
    JASDM char(10)                            not null comment '教室代码'
)
    comment '用户反馈';

create table grids
(
    id      int auto_increment
        primary key,
    text    varchar(8)       not null comment '标题文字',
    img_url varchar(128)     not null comment '标题图片',
    url     varchar(128)     null comment '跳转页面',
    method  varchar(16)      null comment '执行方法',
    button  varchar(128)     null comment '按钮开放功能',
    active  bit default b'1' not null
)
    comment '发现页面';

create table jas
(
    jasdm           char(10)         not null comment '教室代码'
        primary key,
    jasmc           varchar(32)      not null comment '教室名称',
    jxldm           varchar(8)       not null comment '教学楼代码',
    jxldm_display   varchar(16)      not null comment '教学楼名称',
    xxxqdm          varchar(4)       not null comment '学校校区代码',
    xxxqdm_display  varchar(16)      not null comment '学校校区名称',
    jaslxdm         varchar(8)       null comment '教室类型代码',
    jaslxdm_display varchar(16)      null comment '教室类型名称',
    zt              varchar(8)       null comment '状态',
    lc              smallint         null comment '楼层',
    jsyt            text             null comment '教室用途',
    skzws           int              not null comment '上课座位数',
    kszws           int              not null comment '考试座位数',
    xnxqdm          varchar(32)      null comment '学年学期代码',
    xnxqdm2         varchar(32)      null comment '学年学期代码2',
    dwdm            varchar(32)      null comment '管理单位代码',
    dwdm_display    varchar(32)      null comment '管理单位名称',
    zwsxdm          varchar(8)       null comment '座位属性代码',
    xgdd            text             null comment '相关地点',
    syrq            varchar(32)      null comment '使用日期',
    sysj            varchar(32)      null comment '使用时间',
    sxlb            varchar(32)      null comment '实习类别',
    bz              text             null comment '备注',
    sfypk           bit              null comment '是否已排课',
    sfyxpk          bit              null comment '是否允许排课',
    pkyxj           varchar(32)      null comment '排课优先级',
    sfkswh          bit              null comment '是否考试维护',
    sfyxks          bit              null comment '是否允许考试',
    ksyxj           varchar(32)      null comment '考试优先级',
    sfyxcx          bit              null comment '是否允许查询',
    sfyxjy          bit              null comment '是否允许借用',
    sfyxzx          bit default b'0' not null comment '是否允许自习'
)
    comment '教室列表';

create index jas_jxldm_index
    on jas (jxldm);

create table kcb
(
    id      int auto_increment
        primary key,
    jxlmc   varchar(32)                                            not null comment '教学楼名称',
    jsmph   varchar(32)                                            not null comment '教室门牌号',
    jasdm   char(10)                                               not null comment '教室代码',
    skzws   int                                                    not null comment '座位数',
    zylxdm  char(2) default '00'                                   not null comment '类型代码',
    jc_ks   smallint                                               not null comment '节次_开始',
    jc_js   smallint                                               not null comment '节次_结束',
    jyytms  text                                                   not null comment '借用用途说明',
    kcm     text                                                   not null comment '课程名',
    weekday enum ('sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat') not null comment '星期',
    sfyxzx  bit     default b'0'                                   not null comment '是否允许自习'
)
    comment '原始课程表';

create index kcb_jasdm_index
    on kcb (jasdm);

create index kcb_jxl_index
    on kcb (jxlmc);

create index kcb_weekday_index
    on kcb (weekday);

create table notice
(
    id   int auto_increment
        primary key,
    time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '发布时间',
    text varchar(1024)                       not null comment '公告内容'
)
    comment '公告记录';

create table positions
(
    id        int         not null
        primary key,
    name      varchar(32) not null comment '名称',
    latitude  float(7, 5) not null comment '纬度',
    longitude float(8, 5) not null comment '经度',
    kind      smallint    not null comment '1=教学楼 2=校车站'
)
    comment '位置坐标';

create table shuttle
(
    id            int auto_increment
        primary key,
    route         smallint                   not null comment '路线方向',
    start_time    varchar(5) default '00:00' not null comment '发车时间',
    start_station varchar(8)                 not null comment '起点站',
    end_station   varchar(8)                 not null comment '终点站',
    shuttle_count int                        not null comment '发车数量',
    working       char(7)                    not null comment '工作日/双休日',
    constraint UNIQUE_KEY
        unique (route, start_time)
)
    comment '校车时刻表';

create table timetable
(
    id      int auto_increment
        primary key,
    JXLMC   varchar(32)                                            not null comment '教学楼名称',
    jsmph   varchar(32)                                            not null comment '教室门牌号',
    JASDM   char(10)                                               not null comment '教室代码',
    SKZWS   int                                                    not null comment '座位数',
    zylxdm  char(2) default '00'                                   not null comment '类型代码',
    jc_ks   smallint                                               not null comment '节次_开始',
    jc_js   smallint                                               not null comment '节次_结束',
    jyytms  text                                                   not null comment '借用用途说明',
    kcm     text                                                   not null comment '课程名',
    weekday enum ('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat') not null comment '星期',
    SFYXZX  bit     default b'0'                                   not null comment '是否允许自习'
)
    comment '课程表';

create index timetable_jasdm_index
    on timetable (JASDM);

create index timetable_jxl_index
    on timetable (JXLMC);

create index timetable_weekday_index
    on timetable (weekday);

create table user_favorites
(
    id      bigint auto_increment
        primary key,
    openid  varchar(32)                                            not null comment '用户id',
    title   varchar(64)                                            not null comment '标题',
    weekday enum ('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat') not null comment '星期',
    ksjc    smallint                                               not null comment '开始节次',
    jsjc    smallint                                               not null comment '结束节次',
    place   varchar(32)                                            not null comment '地点',
    color   char(7)                                                not null comment '颜色(#RGB)',
    remark  text                                                   not null comment '备注'
)
    comment '用户定制时间表';

create table users
(
    openid           varchar(32) not null comment '用户id'
        primary key,
    first_login_time datetime    not null comment '首次登录时间',
    last_login_time  datetime    not null comment '最新登录时间'
)
    comment '用户信息';

