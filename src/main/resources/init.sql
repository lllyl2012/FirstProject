--权限表--
CREATE TABLE permission(
	pid int(11) AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null default '',
	url varchar(255) default ''
)engine=InnoDB default charset=utf8;

INSERT INTO permission values(null,'add','');
INSERT INTO permission values(null,'delete','');
INSERT INTO permission values(null,'edit','');
INSERT INTO permission values(null,'query','');
--用户表--
create table user(
	uid int(11) auto_increment primary key,
	username varchar(255) not null default '',
	password varchar(255) not null default ''
)engine = InnoDB default charset = utf8;

insert into user values(null,'admin','123');
insert into user values(null,'demo','123');

--角色表--
create table role(
	rid int(11) auto_increment primary key,
	rname varchar(255) not null default ''
)engine = InnoDB default charset = utf8;


insert into role values(null,'admin');
insert into role values(null,'customer');
--权限角色关系表--
create table permission_role(
	rid int(11) not null,
	pid int(11) not null,
	key idx_rid(rid),
	key idx_pid(pid)
)engine = InnoDB default charset = utf8;
insert into permission_role values(1,1);
insert into permission_role values(1,2);
insert into permission_role values(1,3);
insert into permission_role values(1,4);
insert into permission_role values(2,1);
insert into permission_role values(2,4);
--用户角色关系表--
create table user_role(
	uid int(11) not null,
	rid int(11) not null,
	KEY idx_uid(uid),
	KEY idx_rid(rid)
)ENGINE = InnoDB default charset = utf8;
insert into user_role values(1,1);
insert into user_role values(2,2);

