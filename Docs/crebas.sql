/*==============================================================*/
/* DBMS name:      Sybase SQL Anywhere 12                       */
/* Created on:     2014/7/14 16:38:44                           */
/*==============================================================*/


drop table if exists t_adjacency;

drop table if exists t_credit;

drop table if exists t_discount;

drop table if exists t_order;

drop table if exists t_regional;

drop table if exists t_resource;

drop table if exists t_shelf;

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



/*==============================================================*/
/* Table: t_credit                                              */
/*==============================================================*/
create table t_credit 
(
   tid                  int(32)                        not null,
   u_id                 int(32)                        null,
   card_name            varchar(255)                   null,
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   add3                 char(10)                       null,
   constraint PK_T_CREDIT primary key clustered (tid)
);



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



/*==============================================================*/
/* Table: t_order                                               */
/*==============================================================*/
create table t_order 
(
   tid                  int(32)                        not null,
   u_id                 int(32)                        null,
   resource_id          int(32)                        null,
   resourcePrice        float(32)                      null,
   number               int(32)                        null,
   orderTime            datetime                       null,
   status               int(1)                         null,
   constraint PK_T_ORDER primary key clustered (tid)
);



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
   add1                 char(10)                       null,
   add2                 char(10)                       null,
   constraint PK_T_RESOURCE primary key clustered (tid)
);





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



/*==============================================================*/
/* Table: t_species                                             */
/*==============================================================*/
create table t_species 
(
   tid                  int(32)                        not null,
   speciesName          varchar(255)                   null,
   parentId             int(32)                        null,
   constraint PK_T_SPECIES primary key clustered (tid)
);


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
   constraint PK_T_USER primary key clustered (tid)
);



