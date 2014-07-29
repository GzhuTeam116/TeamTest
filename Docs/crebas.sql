/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     2014/7/23 20:58:47                           */
/*==============================================================*/


drop table if exists t_adjacency;

drop table if exists t_discount;

drop table if exists t_hobby;

drop table if exists t_order;

drop table if exists t_regional;

drop table if exists t_resource;

drop table if exists t_shelf;

drop table if exists t_shopcart;

drop table if exists t_species;

drop table if exists t_user;

/*==============================================================*/
/* Table: t_adjacency                                           */
/*==============================================================*/
create table t_adjacency 
(
   tid                  int(32)                        not null,
   startId              int(32)                        null,
   endId                int(32)                        null,
   nextId               int(32)                        null,
   direction            int(1)                         null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   add3                 char(10)                       null,
   constraint PK_T_ADJACENCY primary key clustered (tid)
);

comment on table t_adjacency is 
'邻接表
';

comment on column t_adjacency.tid is 
'邻接表id';

comment on column t_adjacency.startId is 
'起点区域id';

comment on column t_adjacency.endId is 
'终点区域id';

comment on column t_adjacency.nextId is 
'下一个区域id';

comment on column t_adjacency.direction is 
'方向（用1-6表示）';

/*==============================================================*/
/* Table: t_discount                                            */
/*==============================================================*/
create table t_discount 
(
   tid                  int(32)                        not null,
   discount             varchar(255)                   null,
   name                 varchar(255)                   null,
   species              varchar(255)                   null,
   is_all_discount      int(1)                         null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   add3                 char(10)                       null,
   constraint PK_T_DISCOUNT primary key clustered (tid)
);

comment on table t_discount is 
'折扣表';

comment on column t_discount.tid is 
'折扣id';

comment on column t_discount.discount is 
'折扣率(eg：七折，八折。。。)';

comment on column t_discount.name is 
'打折资源的名字(可以为空)';

comment on column t_discount.species is 
'资源种类名字(可以为空)';

comment on column t_discount.is_all_discount is 
'（1表示整个书店都在打折，可以为空）';

/*==============================================================*/
/* Table: t_hobby                                               */
/*==============================================================*/
create table t_hobby 
(
   t_id                 char(10)                       null,
   species_id           char(10)                       null,
   u_id                 char(10)                       null,
   count                char(10)                       null
);

comment on table t_hobby is 
'用户喜好表';

comment on column t_hobby.species_id is 
'分类id';

comment on column t_hobby.u_id is 
'用户id';

comment on column t_hobby.count is 
'浏览次数';

/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order 
(
   order_id             char(10)                       null,
   order_num            char(10)                       null,
   order_date           char(10)                       null,
   order_total          char(10)                       null,
   order_img            char(10)                       null
);

comment on table t_order is 
'订单表';

comment on column t_order.order_id is 
'订单id';

comment on column t_order.order_num is 
'订单号';

comment on column t_order.order_date is 
'订单日期·';

comment on column t_order.order_total is 
'订单总价';

comment on column t_order.order_img is 
'订单图片';

/*==============================================================*/
/* Table: t_regional                                            */
/*==============================================================*/
create table t_regional 
(
   tid                  int(32)                        not null,
   regionalName         varchar(255)                   null,
   east                 int(32)                        null,
   south                int(32)                        null,
   west                 int(32)                        null,
   north                int(32)                        null,
   upstairs             int(32)                        null,
   downstairs           int(32)                        null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   add3                 char(10)                       null,
   constraint PK_T_REGIONAL primary key clustered (tid)
);

comment on table t_regional is 
'区域表';

comment on column t_regional.tid is 
'区域id';

comment on column t_regional.regionalName is 
'区域名称';

comment on column t_regional.east is 
'东区域id(可以为空)';

comment on column t_regional.south is 
'南区域id(可以为空)';

comment on column t_regional.west is 
'西区域id(可以为空)';

comment on column t_regional.north is 
'南区域id(可以为空)';

comment on column t_regional.upstairs is 
'上楼(可以为空)';

comment on column t_regional.downstairs is 
'下楼(可以为空)';

comment on column t_regional.add1 is 
'备用字段1';

comment on column t_regional.add2 is 
'备用字段2';

comment on column t_regional.add3 is 
'备用字段3';

/*==============================================================*/
/* Table: t_resource                                            */
/*==============================================================*/
create table t_resource 
(
   tid                  int(32)                        not null,
   name                 varchar(255)                   null,
   price                float(32)                      null,
   press                varchar(255)                   null,
   number               int(32)                        null,
   isbn                 varchar(255)                   null,
   author               varchar(255)                   null,
   species_id           int(32)                        null,
   salesvolume          int(32)                        null,
   localtion            int(32)                        null,
   is_onsall            int(32)                        null,
   url                  varchar(255)                   null,
   introduction         varchar(255)                   null,
   searchNum            varchar(255)                   null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   constraint PK_T_RESOURCE primary key clustered (tid)
);

comment on table t_resource is 
'资源表
';

comment on column t_resource.tid is 
'资源id';

comment on column t_resource.name is 
'资源名字';

comment on column t_resource.price is 
'资源价格';

comment on column t_resource.press is 
'出版社（资源产出地）';

comment on column t_resource.number is 
'资源数量';

comment on column t_resource.isbn is 
'ibsn';

comment on column t_resource.author is 
'作者（资源的出售者）';

comment on column t_resource.species_id is 
'资源种类id';

comment on column t_resource.salesvolume is 
'销售量';

comment on column t_resource.localtion is 
'书架的id';

comment on column t_resource.is_onsall is 
'是否上架';

comment on column t_resource.url is 
'资源图片';

comment on column t_resource.introduction is 
'资源简介';

comment on column t_resource.searchNum is 
'搜索量';

/*==============================================================*/
/* Table: t_shelf                                               */
/*==============================================================*/
create table t_shelf 
(
   tid                  int(32)                        not null,
   shelf_name           varchar(255)                   null,
   regional_id          int(32)                        null,
   shelf_descripe       varchar(255)                   null,
   constraint PK_T_SHELF primary key clustered (tid)
);

comment on table t_shelf is 
'书架表';

comment on column t_shelf.tid is 
'书架id';

comment on column t_shelf.shelf_name is 
'书架名字';

comment on column t_shelf.regional_id is 
'区域id';

comment on column t_shelf.shelf_descripe is 
'书架描述';

/*==============================================================*/
/* Table: t_shopcart                                            */
/*==============================================================*/
create table t_shopcart 
(
   tid                  int(32)                        not null,
   u_id                 int(32)                        null,
   resource_id          int(32)                        null,
   number               int(32)                        null,
   orderid              int(32)                        null,
   constraint PK_T_SHOPCART primary key clustered (tid)
);

comment on table t_shopcart is 
'购物车表';

comment on column t_shopcart.tid is 
'购物车id';

comment on column t_shopcart.u_id is 
'人员id';

comment on column t_shopcart.resource_id is 
'资源id';

comment on column t_shopcart.number is 
'资源数量';

comment on column t_shopcart.orderid is 
'订单id';

/*==============================================================*/
/* Table: t_species                                             */
/*==============================================================*/
create table t_species 
(
   tid                  int(32)                        not null,
   speciesName          varchar(255)                   null,
   constraint PK_T_SPECIES primary key clustered (tid)
);

comment on table t_species is 
'商品种类表
';

comment on column t_species.tid is 
'种类表id';

comment on column t_species.speciesName is 
'种类名字';

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user 
(
   tid                  int(32)                        not null,
   account              varchar(255)                   null,
   password             varchar(255)                   null,
   is_adimn             int(32)                        null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   add3                 char(10)                       null,
   phone                char(10)                       null,
   card_num             char(10)                       null,
   weibo_account        char(10)                       null,
   constraint PK_T_USER primary key clustered (tid)
);

comment on table t_user is 
'人员表
';

comment on column t_user.tid is 
'人员id';

comment on column t_user.account is 
'账户';

comment on column t_user.password is 
'密码';

comment on column t_user.is_adimn is 
'是否为管理员（1是，0否）';

comment on column t_user.add1 is 
'备用字段1';

comment on column t_user.add2 is 
'备用字段2';

comment on column t_user.add3 is 
'备用字段3';

comment on column t_user.phone is 
'手机号 （可为null）';

comment on column t_user.card_num is 
'银联卡（可为null）';

comment on column t_user.weibo_account is 
'微博账号（可为null）';

