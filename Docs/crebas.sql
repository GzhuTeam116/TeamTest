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
'�ڽӱ�
';

comment on column t_adjacency.tid is 
'�ڽӱ�id';

comment on column t_adjacency.startId is 
'�������id';

comment on column t_adjacency.endId is 
'�յ�����id';

comment on column t_adjacency.nextId is 
'��һ������id';

comment on column t_adjacency.direction is 
'������1-6��ʾ��';

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
'�ۿ۱�';

comment on column t_discount.tid is 
'�ۿ�id';

comment on column t_discount.discount is 
'�ۿ���(eg�����ۣ����ۡ�����)';

comment on column t_discount.name is 
'������Դ������(����Ϊ��)';

comment on column t_discount.species is 
'��Դ��������(����Ϊ��)';

comment on column t_discount.is_all_discount is 
'��1��ʾ������궼�ڴ��ۣ�����Ϊ�գ�';

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
'�û�ϲ�ñ�';

comment on column t_hobby.species_id is 
'����id';

comment on column t_hobby.u_id is 
'�û�id';

comment on column t_hobby.count is 
'�������';

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
'������';

comment on column t_order.order_id is 
'����id';

comment on column t_order.order_num is 
'������';

comment on column t_order.order_date is 
'�������ڡ�';

comment on column t_order.order_total is 
'�����ܼ�';

comment on column t_order.order_img is 
'����ͼƬ';

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
'�����';

comment on column t_regional.tid is 
'����id';

comment on column t_regional.regionalName is 
'��������';

comment on column t_regional.east is 
'������id(����Ϊ��)';

comment on column t_regional.south is 
'������id(����Ϊ��)';

comment on column t_regional.west is 
'������id(����Ϊ��)';

comment on column t_regional.north is 
'������id(����Ϊ��)';

comment on column t_regional.upstairs is 
'��¥(����Ϊ��)';

comment on column t_regional.downstairs is 
'��¥(����Ϊ��)';

comment on column t_regional.add1 is 
'�����ֶ�1';

comment on column t_regional.add2 is 
'�����ֶ�2';

comment on column t_regional.add3 is 
'�����ֶ�3';

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
'��Դ��
';

comment on column t_resource.tid is 
'��Դid';

comment on column t_resource.name is 
'��Դ����';

comment on column t_resource.price is 
'��Դ�۸�';

comment on column t_resource.press is 
'�����磨��Դ�����أ�';

comment on column t_resource.number is 
'��Դ����';

comment on column t_resource.isbn is 
'ibsn';

comment on column t_resource.author is 
'���ߣ���Դ�ĳ����ߣ�';

comment on column t_resource.species_id is 
'��Դ����id';

comment on column t_resource.salesvolume is 
'������';

comment on column t_resource.localtion is 
'��ܵ�id';

comment on column t_resource.is_onsall is 
'�Ƿ��ϼ�';

comment on column t_resource.url is 
'��ԴͼƬ';

comment on column t_resource.introduction is 
'��Դ���';

comment on column t_resource.searchNum is 
'������';

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
'��ܱ�';

comment on column t_shelf.tid is 
'���id';

comment on column t_shelf.shelf_name is 
'�������';

comment on column t_shelf.regional_id is 
'����id';

comment on column t_shelf.shelf_descripe is 
'�������';

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
'���ﳵ��';

comment on column t_shopcart.tid is 
'���ﳵid';

comment on column t_shopcart.u_id is 
'��Աid';

comment on column t_shopcart.resource_id is 
'��Դid';

comment on column t_shopcart.number is 
'��Դ����';

comment on column t_shopcart.orderid is 
'����id';

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
'��Ʒ�����
';

comment on column t_species.tid is 
'�����id';

comment on column t_species.speciesName is 
'��������';

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
'��Ա��
';

comment on column t_user.tid is 
'��Աid';

comment on column t_user.account is 
'�˻�';

comment on column t_user.password is 
'����';

comment on column t_user.is_adimn is 
'�Ƿ�Ϊ����Ա��1�ǣ�0��';

comment on column t_user.add1 is 
'�����ֶ�1';

comment on column t_user.add2 is 
'�����ֶ�2';

comment on column t_user.add3 is 
'�����ֶ�3';

comment on column t_user.phone is 
'�ֻ��� ����Ϊnull��';

comment on column t_user.card_num is 
'����������Ϊnull��';

comment on column t_user.weibo_account is 
'΢���˺ţ���Ϊnull��';

