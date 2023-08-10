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

