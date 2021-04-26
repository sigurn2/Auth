prompt Importing table T_ROLE...
set feedback off
set define off
insert into T_ROLE (id, descript, name, type)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '社会保障企业非CA用户', 'ROLE_ENTERPRISE_USER', '1');
insert into T_ROLE (id, descript, name, type)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '社会保障个人网厅实名用户', 'ROLE_PERSON_USER', '1');
insert into T_ROLE (id, descript, name, type)
values ('4028018c47ed92cd0147ed92d7810000', '社会保障企业CA用户', 'ROLE_ENTERPRISE_CAUSER', '1');
insert into T_ROLE (id, descript, name, type)
values ('2230B0EC6BA306F0E053DD81A8C091D6', '社会保障个人网厅用户', 'ROLE_PERSON_NORMAL', '1');
--超级管理员角色
insert into T_ROLE (ID, DESCRIPT, NAME, type)
values ('4028018c47ed92cd0147ed92d784000', '超级管理员', 'ROLE_ADMIN', '1');
prompt Done.
