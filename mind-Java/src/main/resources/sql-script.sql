drop table if exists t_user;
create table t_user
(
    nick_name       varchar(255) comment '昵称',
    email           varchar(255) comment '邮箱',
    phone           varchar(255) comment '手机号',
    status          tinyint      not null comment '状态',
    username        varchar(255) not null comment '用户名',
    password        varchar(255) not null comment '密码',
    login_count     int comment '登录次数',
    last_login_time datetime comment '最后登录时间',

    id              bigint       not null primary key auto_increment comment '自增id',
    create_time     datetime comment '创建时间',
    update_time     datetime comment '更新时间'

);


create table t_bubble
(
    content        varchar(255) comment '内容',
    type           tinyint comment '类型 1 临时存储 2 长时存储 3 永久存储',
    master_id      bigint comment '创建人id',
    priority_value int     default 0 comment '权重值 越大优先级越高',
    is_delete      tinyint default 0 comment '是否删除 0未删除 1删除',

    id             bigint primary key auto_increment comment '自增id',
    create_time    datetime not null comment '创建时间',
    update_time    datetime not null comment '更新时间'
);