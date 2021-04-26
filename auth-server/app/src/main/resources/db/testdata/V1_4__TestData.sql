--Company
insert into COMPANY (ID, NAME, COMPANYNUMBER, TAXCODE, ORGCODE, AREACODE, APP_ID)
values (87856601226, '网厅测试单位', '10001015', null, '10001015', null, null);

insert into COMPANY (ID, NAME, COMPANYNUMBER, TAXCODE, ORGCODE, AREACODE, APP_ID)
values (87856601227, '网厅演示单位', '10000609', null, '10000609', null, null);

insert into COMPANY (ID, NAME, COMPANYNUMBER, TAXCODE, ORGCODE, AREACODE, APP_ID)
values (87856601228, '安全认证测试单位', '10074942', null, '10074942', null, null);

--OAUTH_CLIENT_DETAILS...

insert into OAUTH_CLIENT_DETAILS (CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, WEB_SERVER_REDIRECT_URI, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY, ADDITIONAL_INFORMATION, AUTOAPPROVE)
values ('ehrss-client-tianj', null, 'tianj-client-secret', 'read,write', 'password,refresh_token', null, 'ROLE_CLIENT', 7200, null, null, null);

--PERSON...
 
set define off
insert into PERSON (ID, PERSONNUMBER, NAME, ID_TYPE, ID_NUMBER, ID_SOCIALENSURENUMBER, ID_COUNTRY, PROFILE_STATUS)
values (21004779623, '3501830933', '登录测试', '01', '320706195505220516', '320706195505220516', 'CHN', '1');

insert into PERSON (ID, PERSONNUMBER, NAME, ID_TYPE, ID_NUMBER, ID_SOCIALENSURENUMBER, ID_COUNTRY, PROFILE_STATUS)
values (21004779622, '3501830932', '范维智', '01', '320706195405220516', '320706195405220516', 'CHN', '1');

--T_APP...

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('beatrice-analysis', '行为分析', '/beatrice/analysis/ui/#/login', '行为分析');

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('ehrss-si-enterprise', '这里是系统描述', '/ehrss/si/enterprise/ui/#/login', '社会保障');

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('ehrss-si-person', '这里是系统描述', 'kq.neusoft.com', '网报个人');

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('ehrss-sl-hr', '这里是系统描述', 'kq.neusoft.com', '人事人才');

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('ehrss-sl-labour', '这里是系统描述', '/ehrss/sl/labour/ui/#/login', '劳动就业');

insert into T_APP (ID, DESCRIPT, URL, NAME)
values ('ehrss-si-agent', '这里是系统描述', 'kq.neusoft.com', '建筑项目');

--T_RESOURCE...

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B0A15B5EE053DD81A8C01B21', '0', '1', 'siperson', 4, '工伤职工垫付费用申报', '/buzz/treatment/injury/injuryAdvanced', '工伤职工垫付费用申报', '2FBADAF77E0D1209E053076519B9C9C8', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1083FE3E053DD81A8C01952', '0', '1', 'sienterprise', 3, '职工缴费管理', null, '职工缴费管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402357', '0', '0', 'beatriceanalysis', 4, '计划任务菜单', null, '计划任务管理', null, 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402362', '0', '1', 'beatriceanalysis', 1, '首页', '/home', '首页', null, 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('265DD2ADFDB469EFE053DD81A8C04F19', '0', '1', 'siagent', 1, '建筑项目新参保登记', '/project/register', '建筑项目新参保登记', null, 'ehrss-si-agent');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97dddd0001', '0', '1', 'admin', 2, '权限管理', null, '权限管理', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97dde90002', '0', '1', 'admin', 1, '用户管理', '/girder/security/user', '用户管理', '4028018c47ed97d00147ed97dddd0001', null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97ddf50003', '0', '1', 'admin', 2, '菜单维护', '/girder/security/menu', '菜单维护', '4028018c47ed97d00147ed97dddd0001', null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de020004', '0', '1', 'admin', 3, '角色维护', '/girder/security/role', '角色维护', '4028018c47ed97d00147ed97dddd0001', null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de130005', '0', '1', 'admin', 4, '用户授权', '/girder/security/role_to_user', '用户授权', '4028018c47ed97d00147ed97dddd0001', null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de190006', '0', '1', 'admin', 5, '菜单授权', '/girder/security/menu_to_role', '菜单授权', '4028018c47ed97d00147ed97dddd0001', null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de1f0007', '0', '1', 'admin', 3, '通知管理', '/girder/notice', '通知管理', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de240008', '0', '1', 'admin', 4, '系统监控', null, '系统监控', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de2b0009', '0', '1', 'admin', 3, '系统信息', '/girder/monitor', '系统信息', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de31000a', '0', '1', 'admin', 3, '参数配置', '/girder/monitor', '参数配置', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c47ed97d00147ed97de37000b', '0', '1', 'admin', 3, '日志管理', '/girder/monitor', '日志管理', null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c488830e801488830fd53001f', '1', '1', '社保核心系统接口', 0, '社保核心系统接口', 'ws/simis/users', null, null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('4028018c488830e801488830fd660020', '1', '1', '海南省数字证书认证中心接口', 0, '海南省数字证书认证中心接口', 'ws/ca/users', null, null, null);

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('265DD2ADFDB569EFE053DD81A8C04F19', '0', '1', 'siagent', 2, '建筑项目变更登记', '/project/modify', '建筑项目变更登记', null, 'ehrss-si-agent');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('37D1BEC1CE1B04D8E053BA1201015A53', '0', '1', 'siagent', 3, '单位到账统计情况查询', '/project/search', '单位到账统计情况查询', null, 'ehrss-si-agent');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402953', '0', '1', 'sienterprise', 1, '缴费基数名册打印', '/search/charging/print', '缴费基数名册打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03086', '0', '1', 'siperson', 4, '我的基数历程', '/dataGraphical_line', '我的基数历程', '233A5A85B09B5B5EE053DD81A8C03061', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03033', '0', '1', 'siperson', 2, '社保业务', '/rights', '社保业务', null, 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03034', '0', '1', 'siperson', 1, '社保业务经办', null, '社保业务经办', '233A5A85B09B5B5EE053DD81A8C03033', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03035', '0', '0', 'siperson', 2, '自助缴费凭证查询', '/rights/payment/selfhelpvoucher', '自助缴费凭证查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('38217DE455F05202E053013211BA8AA8', '0', '1', 'sienterprise', 6, '工伤浮动费率告知书打印', '/search/injure/print/float', '工伤浮动费率告知书打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('38217DE455F15202E053013211BA8AA8', '0', '1', 'sienterprise', 7, '单位核定单打印', '/search/collect/collectNotice', '单位征集核定单打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03036', '0', '1', 'siperson', 3, '缴费信息查询', '/rights/payment/payinfo', '缴费信息查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03037', '0', '0', 'siperson', 1, '个人身份网上缴费', '/buzz/payment/individualpay', '个人身份网上缴费', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03038', '0', '1', 'siperson', 4, '养老查询', null, '养老查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03039', '0', '1', 'siperson', 1, '养老账户信息查询', '/rights/account/pension/info', '养老账户信息查询', '233A5A85B09B5B5EE053DD81A8C03038', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03040', '0', '1', 'siperson', 2, '养老待遇享受信息查询', '/rights/treatment/pension/enjoyinfo', '养老待遇享受信息查询', '233A5A85B09B5B5EE053DD81A8C03038', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03041', '0', '1', 'siperson', 5, '医疗查询', null, '医疗查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03042', '0', '1', 'siperson', 1, '医疗账户信息查询', '/rights/account/medical/info', '医疗账户信息查询', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03043', '0', '1', 'siperson', 2, '医疗账户收支明细查询', '/rights/account/medical/balances', '医疗账户收支明细查询', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03044', '0', '1', 'siperson', 3, '医疗费支出查询', '/dataGraphical_pie', '医疗费支出查询', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03045', '0', '1', 'siperson', 4, '医疗待遇审批信息查询', '/rights/treatment/medical/approval', '医疗待遇审批信息查询', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03046', '0', '1', 'siperson', 5, '异地居住就医信息查询', '/rights/treatment/medical/otherplace', '异地居住就医信息查询', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03047', '0', '1', 'siperson', 6, '工伤待遇信息查询', '/rights/treatment/injury/info', '工伤待遇信息查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03048', '0', '1', 'siperson', 7, '生育待遇信息查询', '/rights/treatment/maternity/info', '生育待遇信息查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03049', '0', '1', 'siperson', 8, '失业待遇信息查询', '/rights/treatment/unemployee/info', '失业待遇信息查询', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03050', '0', '1', 'siperson', 3, '劳动业务', '/labor', '劳动业务', null, 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03051', '0', '1', 'siperson', 1, '劳动业务经办', null, '劳动业务经办', '233A5A85B09B5B5EE053DD81A8C03050', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03052', '0', '1', 'siperson', 1, '失业人员', '/labor/unemployee/archives', '失业人员档案查询', '233A5A85B09B5B5EE053DD81A8C03051', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03053', '0', '1', 'siperson', 2, '就失业', '/labor/employeework/info', '就失业信息查询', '233A5A85B09B5B5EE053DD81A8C03051', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03054', '0', '1', 'siperson', 4, '卡中心', '/card', '卡中心', null, 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03055', '0', '1', 'siperson', 1, '卡中心分割线', null, '卡中心', '233A5A85B09B5B5EE053DD81A8C03054', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03056', '0', '1', 'siperson', 1, '社会保障卡办卡进度查询', '/card/progress', '社会保障卡办卡进度查询', '233A5A85B09B5B5EE053DD81A8C03055', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03057', '0', '1', 'siperson', 2, '社会保障卡临时挂失', '/card/loss', '社会保障卡临时挂失', '233A5A85B09B5B5EE053DD81A8C03055', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03058', '0', '1', 'siperson', 3, '社会保障卡状态查询', '/card/status', '社会保障卡状态查询', '233A5A85B09B5B5EE053DD81A8C03055', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03059', '0', '1', 'siperson', 4, '社社会保障卡基本信息查询', '/card/baseinfo', '社会保障卡基本信息查询', '233A5A85B09B5B5EE053DD81A8C03055', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03060', '0', '1', 'siperson', 5, '个人中心', '/personCenter', '个人中心', null, 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03061', '0', '1', 'siperson', 1, '个人中心分割线', null, '个人中心', '233A5A85B09B5B5EE053DD81A8C03060', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03062', '0', '1', 'siperson', 1, '基本信息修改', '/buzz/person/baseinfo', '基本信息修改', '233A5A85B09B5B5EE053DD81A8C03061', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03063', '0', '1', 'siperson', 2, '密码修改', '/personCenter/password', '密码修改', '233A5A85B09B5B5EE053DD81A8C03061', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03064', '0', '1', 'siperson', 3, '手机号修改', '/personCenter/mobile', '手机号修改', '233A5A85B09B5B5EE053DD81A8C03061', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09A5B5EE053DD81A8C01B21', '0', '1', 'siperson', 1, '个人首页', '/home', '首页', null, 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03080', '0', '1', 'siperson', 1, '个人网上缴费', '/buzz/payment/individual/individualPay', '个人网上缴费', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03081', '0', '1', 'siperson', 9, '缴费计算器', '/rights/calculator/payment', '缴费计算器', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03082', '0', '1', 'siperson', 10, '退休计算器', '/rights/calculator/pension', '退休计算器', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03083', '0', '1', 'siperson', 11, '档案材料上传', '/dossierUpdate', '档案材料上传', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03084', '0', '1', 'siperson', 2, '缴费单打印', '/buzz/payment/printPayment', '缴费单打印', '233A5A85B09B5B5EE053DD81A8C03034', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B09B5B5EE053DD81A8C03085', '0', '1', 'siperson', 6, '年度医疗费支出统计', '/dataGraphical_bar', '年度医疗费支出统计', '233A5A85B09B5B5EE053DD81A8C03041', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402853', '0', '1', 'beatriceanalysis', 2, '企业网厅', null, '企业网厅', null, 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402852', '0', '1', 'beatriceanalysis', 1, '企业网厅业务量统计（按区县）', '/enterprise/statistics/business/aera', '业务量统计（按区县）', '24503A24A11E34E5E053DD81A8402853', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402851', '0', '1', 'beatriceanalysis', 2, '企业网厅业务量统计（按业务类型）', '/enterprise/statistics/business/type', '业务量统计（按业务类型）', '24503A24A11E34E5E053DD81A8402853', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402850', '0', '1', 'beatriceanalysis', 3, '企业网厅业务量统计（按时间）', '/enterprise/statistics/business/time', '业务量统计（按时间）', '24503A24A11E34E5E053DD81A8402853', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402854', '0', '1', 'beatriceanalysis', 4, '企业网厅审批情况统计（按区县）', '/enterprise/statistics/approve/aera', '审批情况统计（按区县）', '24503A24A11E34E5E053DD81A8402853', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402855', '0', '1', 'beatriceanalysis', 5, '企业网厅审批情况统计（按业务类型）', '/enterprise/statistics/approve/type', '审批情况统计（按业务类型）', '24503A24A11E34E5E053DD81A8402853', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402856', '0', '1', 'beatriceanalysis', 3, '个人网厅', null, '个人网厅', null, 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402859', '0', '1', 'beatriceanalysis', 1, '个人网厅业务量统计（按业务类型）', '/person/statistics/business', '业务量统计（按业务类型）', '24503A24A11E34E5E053DD81A8402856', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402858', '0', '1', 'beatriceanalysis', 2, '个人网厅业务量统计（按时间）', '/person/statistics/time', '业务量统计（按时间）', '24503A24A11E34E5E053DD81A8402856', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402861', '0', '0', 'beatriceanalysis', 1, '计划任务管理', '/manager/task/scheduled', '计划任务管理', '24503A24A11E34E5E053DD81A8402357', 'beatrice-analysis');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('38217DE455EF5202E053013211BA8AA8', '0', '1', 'sienterprise', 4, '医疗异地安置人员登记申报', '/medicare/relocation', '医疗异地安置人员登记申报', '2FF091583C1A1657E053076519B94DB7', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('33014EEF4B3E686BE053076519B9048D', '0', '1', 'sienterprise', 3, '生成支付表', '/pension/levy', '生成支付表', '24503A24A1153FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('233A5A85B0C05B5EE053DD81A8C01B21', '0', '1', 'siperson', 3, '修改密码', '/personCenter/password', '修改密码', '233A5A85B0BF5B5EE053DD81A8C01B21', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('23C645A5730A7867E053DD81A8C0DF56', '0', '1', 'siperson', 4, '修改手机号码', '/personCenter/mobile', '修改手机号码', '233A5A85B0BF5B5EE053DD81A8C01B21', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2F565E1C3A9C4439E053076519B92553', '0', '1', 'sienterprise', 3, '生育医疗垫付费汇总表打印', '/search/medicare/print', '生育医疗垫付费汇总表打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FA5767B3C846320E053076519B96EAB', '0', '1', 'sienterprise', 2, '单位用工范围申报', '/unit/employmentscope', '单位用工范围申报', '24503A24A1063FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FA57E590CA46342E053076519B908B8', '0', '1', 'sienterprise', 2, '养老支付表打印', '/search/pension/levyform', '养老支付表打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FF07558BDB815F2E053076519B976F9', '0', '1', 'sienterprise', 12, '报表打印', null, '报表打印', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FF091583C1C1657E053076519B94DB7', '0', '1', 'sienterprise', 2, '转外地医院住院登记', '/medicare/foreignmedical', '转外地医院住院登记', '2FF091583C1A1657E053076519B94DB7', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FF091583C1B1657E053076519B94DB7', '0', '1', 'sienterprise', 1, '个人遗失票据登记', '/medicare/lostnote', '个人遗失票据登记', '2FF091583C1A1657E053076519B94DB7', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('2FF091583C1A1657E053076519B94DB7', '0', '1', 'sienterprise', 6, '医疗管理', null, '医疗管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('315FFDF6299B1CB5E053076519B95136', '0', '1', 'sienterprise', 3, '垫付医疗费申报', '/medicare/expense', '垫付医疗费申报', '2FF091583C1A1657E053076519B94DB7', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('30E153BC0C1D7670E053076519B91246', '0', '1', 'siperson', 2, '医疗异地安置人员登记', '/buzz/treatment/medical/relocation', '医疗异地安置人员登记', '2FBADAF77E0C1209E053076519B9C9C8', 'ehrss-si-person');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('30EA2D806D190DF6E053076519B9D5FD', '0', '1', 'sienterprise', 4, '单位缴费通知单打印', '/search/payment/paymentNotice', '单位缴费通知单打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A466F0517E053DD81A8C02621', '0', '1', 'sllabour', 1, '劳动首页', '/home', '首页', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46700517E053DD81A8C02621', '0', '1', 'sllabour', 2, '合同管理', null, '合同管理', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46710517E053DD81A8C02621', '0', '1', 'sllabour', 1, '合同续变申报', '/contract', '合同续变申报', '25FB866A46700517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46720517E053DD81A8C02621', '0', '1', 'sllabour', 3, '工伤认定和劳动能力鉴定管理', null, '工伤认定和劳动能力鉴定管理', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46730517E053DD81A8C02621', '0', '1', 'sllabour', 1, '工伤事故报告', '/injuryReport', '工伤事故报告', '25FB866A46720517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46740517E053DD81A8C02621', '0', '1', 'sllabour', 2, '工伤认定申报', '/injuryCognizance', '工伤认定申报', '25FB866A46720517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46750517E053DD81A8C02621', '0', '1', 'sllabour', 3, '劳动能力鉴定申报', '/laAssessment', '劳动能力鉴定申报', '25FB866A46720517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46760517E053DD81A8C02621', '0', '1', 'sllabour', 5, '劳动保障监察管理', null, '劳动保障监察管理', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46770517E053DD81A8C02621', '0', '1', 'sllabour', 1, '书面审查申报', '/docInspection', '书面审查申报', '25FB866A46760517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46780517E053DD81A8C02621', '0', '1', 'sllabour', 8, '业务提交', null, '业务提交', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A46790517E053DD81A8C02621', '0', '1', 'sllabour', 1, '待提交业务管理', '/submit/pending', '待提交业务管理', '25FB866A46780517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A467A0517E053DD81A8C02621', '0', '1', 'sllabour', 9, '反馈结果', null, '反馈结果', null, 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('25FB866A467B0517E053DD81A8C02621', '0', '1', 'sllabour', 1, '反馈结果查询', '/review/reviewed', '反馈结果查询', '25FB866A467A0517E053DD81A8C02621', 'ehrss-sl-labour');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('323B057E96030A1DE053076519B9450F', '0', '1', 'sienterprise', 5, '退休申报审批表打印(区人社局/分中心)', '/search/pension/retire', '退休申报审批表打印', '2FF07558BDB815F2E053076519B976F9', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1063FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '单位管理', null, '单位管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402954', '0', '1', 'sienterprise', 4, '职工参保情况统计查询', '/search/charging/payment', '职工参保情况统计查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1203FE3E053DD81A8C01952', '0', '1', 'sienterprise', 9, '申请提交', null, '申请提交', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1203FE3E053DD81A8C01955', '0', '1', 'sienterprise', 1, '申报表打印', '/submit/submitapplyprint/salary', '申报表打印', '24503A24A1203FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1223FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '待提交申报管理', '/submit/pending', '待提交申报管理', '24503A24A1203FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1233FE3E053DD81A8C01952', '0', '1', 'sienterprise', 3, '单位有变动核定', '/unit/levy', '单位有变动核定', '24503A24A1203FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1233233E053DD81A8C01952', '0', '1', 'sienterprise', 4, '单位无变动核定', '/unit/nochangelevy', '单位无变动核定', '24503A24A1203FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1253FE3E053DD81A8C01952', '0', '1', 'sienterprise', 10, '审核结果', null, '审核结果', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1243FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '审核结果查询', '/review/reviewed', '审核结果查询', '24503A24A1253FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1243FE3E153DD81A8C01952', '0', '1', 'sienterprise', 2, '综合业务处理单打印', '/review/reviewedprint', '综合业务处理单打印', '24503A24A1253FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1053FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '企业首页', '/home', '首页', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1073FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '单位非敏感信息修改', '/unit/modify', '单位非敏感信息修改', '24503A24A1063FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1093FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '职工新增申报', '/employee/register', '职工新增申报', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10A3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '职工减少申报', '/employee/dimission', '职工减少申报', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10B3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 3, '职工非敏感信息修改', '/employee/modify', '职工非敏感信息修改', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10C3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 5, '职工缴费工资申报', '/employee/salary', '职工缴费工资申报', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10D3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 6, '职工核定基数补缴申报', '/employee/payoverdue', '职工核定基数补缴申报', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10D3FE3E153DD81A8C01952', '0', '1', 'sienterprise', 7, '职工调整基数补缴申报', '/employee/makeupbalance', '职工调整基数补缴申报', '24503A24A1083FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10E3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 4, '派遣管理缴费', null, '派遣缴费管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A10F3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '派遣人员新增申报', '/labour/register', '派遣人员新增申报', '24503A24A10E3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1103FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '派遣人员减少申报', '/labour/dimission', '派遣人员减少申报', '24503A24A10E3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1113FE3E053DD81A8C01952', '0', '1', 'sienterprise', 3, '派遣人员缴费工资申报', '/labour/salary', '派遣人员缴费工资申报', '24503A24A10E3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1123FE3E053DD81A8C01952', '0', '1', 'sienterprise', 4, '派遣人员核定基数补缴申报', '/labour/payoverdue', '派遣人员核定基数补缴申报', '24503A24A10E3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1123FE3E053D281A8C01952', '0', '1', 'sienterprise', 5, '派遣人员调整基数补缴申报', '/labour/makeupbalance', '派遣人员调整基数补缴申报', '24503A24A10E3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1133FE3E053DD81A8C01952', '0', '1', 'sienterprise', 5, '派遣单位查询', '/labour/unit', '派遣单位查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1143FE3E053DD81A8C01952', '0', '1', 'sienterprise', 6, '派遣职工花名册', '/labour/labourdetailinfo', '派遣职工花名册', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1153FE3E053DD81A8C01952', '0', '1', 'sienterprise', 5, '养老管理', null, '养老管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1193FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '离退休人员居住信息修改', '/pension/residentmodify', '离退休人员居住信息修改', '24503A24A1153FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11A3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '养老待遇审核申报', '/pension/retire', '养老待遇审核申报', '24503A24A1153FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1153FE3E053DD2148C01952', '0', '1', 'sienterprise', 7, '工伤管理', null, '工伤管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1163FE3E052DD81A8301952', '0', '1', 'sienterprise', 1, '工伤职工垫付费用申报', '/injure/advancefee', '工伤职工垫付费用申报', '24503A24A1153FE3E053DD2148C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1153FE3E053DD8148C01952', '0', '1', 'sienterprise', 8, '生育管理', null, '生育管理', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A1163FE3E053DD81A8301952', '0', '1', 'sienterprise', 1, '生育津贴申报', '/maternity/benefit', '生育津贴申报', '24503A24A1153FE3E053DD8148C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11B3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 11, '信息查询', null, '信息查询', null, 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11C3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 1, '单位信息查询', '/search/unitbasicinfo', '单位信息查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11D3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 2, '职工信息查询', '/search/empbasicinfo', '职工信息查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E3FE3E053DD81A8C01952', '0', '1', 'sienterprise', 3, '职工缴费查询', '/search/emppaidinfo', '职工缴费查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E3E053DD81A8C01952', '0', '1', 'sienterprise', 9, '职工垫付医疗费查询', '/search/medicalcosumeinfo', '职工垫付医疗费查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8C01952', '0', '1', 'sienterprise', 7, '工伤应付核定单查询', '/search/injurelevyform', '工伤应付核定单查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8501952', '0', '1', 'sienterprise', 10, '生育津贴查询', '/search/benefits', '生育津贴查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

insert into T_RESOURCE (ID, TYPE, AVAILABLE, DESCRIPT, ORDER_NUM, NAME, URL, TITLE, PARENT_ID, APP_ID)
values ('24503A24A11E34E5E053DD81A8402952', '0', '1', 'sienterprise', 8, '工伤待遇支付名册查询', '/search/injuretreatment', '工伤待遇支付名册查询', '24503A24A11B3FE3E053DD81A8C01952', 'ehrss-si-enterprise');

-- T_ROLE...

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('2A752EA4021C5D5AE053DD81A8C0F99C', '描述', 'ROLE_SIAGENT_USER');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('1E55FC0236E91026E053DD81A8C05946', '描述', '劳动就业系统管理员');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('1E55FC0236EA1026E053DD81A8C05946', '描述', '人事人才系统管理员');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('1E55FC0236EB1026E053DD81A8C05946', '这里是角色描述', '劳动就业企业用户');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('1E55FC0236EC1026E053DD81A8C05946', '这里是角色描述', '人事人才企业用户');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '社会保障企业非CA用户', 'ROLE_ENTERPRISE_USER');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '描述', 'ROLE_PERSON_USER');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('4028018c47dcd6be0147dcd6c8ae0000', '描述', '社会保障系统管理员');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('4028018c47ed92cd0147ed92d7810000', '社会保障企业CA用户', 'ROLE_ENTERPRISE_CAUSER');

insert into T_ROLE (ID, DESCRIPT, NAME)
values ('4028018c47ed92cd0147ed92d7820000', '行为分析UI', '行为分析');

--T_ROLE_RESOURCE...

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1163FE3E052DD81A8301952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1163FE3E053DD81A8301952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1163FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1193FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11A3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11B3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11C3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11D3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E5E053DD81A8402952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E5E053DD81A8402953');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E5E053DD81A8402954');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E5E053DD81A8501952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E34E5E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A11E3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1203FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1203FE3E053DD81A8C01955');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1223FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1233233E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1233FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1243FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1243FE3E153DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1253FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A466F0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46700517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46710517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46720517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46730517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46740517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46750517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46760517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46770517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46780517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A46790517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A467A0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '25FB866A467B0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '2F565E1C3A9C4439E053076519B92553');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '2FA5767B3C846320E053076519B96EAB');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '2FA57E590CA46342E053076519B908B8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '2FF07558BDB815F2E053076519B976F9');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '2FF091583C1A1657E053076519B94DB7');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '30EA2D806D190DF6E053076519B9D5FD');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '315FFDF6299B1CB5E053076519B95136');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '323B057E96030A1DE053076519B9450F');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '38217DE455EF5202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '38217DE455F05202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '38217DE455F15202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402362');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402850');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402851');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402852');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402853');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402854');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402855');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402856');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402858');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7820000', '24503A24A11E34E5E053DD81A8402859');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c48880d520148880d653f001e', '4028018c488830e801488830fd53001f');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c4888749601488874ac3e001e', '4028018c488830e801488830fd660020');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1053FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1063FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1073FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1083FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1093FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A10A3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A10B3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A10C3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A10D3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A10D3FE3E153DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1133FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1143FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1153FE3E053DD2148C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1153FE3E053DD8148C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1153FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1163FE3E052DD81A8301952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1163FE3E053DD81A8301952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1163FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1193FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11A3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11B3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11C3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11D3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E5E053DD81A8402952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E5E053DD81A8402953');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E5E053DD81A8402954');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E5E053DD81A8501952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E34E5E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A11E3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1203FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1203FE3E053DD81A8C01955');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1223FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1233233E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1233FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1243FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1243FE3E153DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '24503A24A1253FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A466F0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46700517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46710517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46720517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46730517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46740517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46750517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46760517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46770517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46780517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A46790517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A467A0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '25FB866A467B0517E053DD81A8C02621');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '2F565E1C3A9C4439E053076519B92553');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '2FA5767B3C846320E053076519B96EAB');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '2FA57E590CA46342E053076519B908B8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '2FF07558BDB815F2E053076519B976F9');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '2FF091583C1A1657E053076519B94DB7');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '30EA2D806D190DF6E053076519B9D5FD');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '315FFDF6299B1CB5E053076519B95136');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '323B057E96030A1DE053076519B9450F');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '38217DE455EF5202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '38217DE455F05202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('1F3A63BB06A818A1E053DD81A8C0D667', '38217DE455F15202E053013211BA8AA8');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09A5B5EE053DD81A8C01B21');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03033');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03034');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03035');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03036');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03037');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03038');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03039');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03040');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03041');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03042');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03043');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03044');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03045');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03046');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03047');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03048');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03049');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03050');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03051');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03052');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03053');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03054');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03055');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03056');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03057');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03058');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03059');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03060');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03061');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03062');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03063');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03064');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03080');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03081');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03082');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03083');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03084');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03085');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2230B0EC6BA306F0E053DD81A8C091D5', '233A5A85B09B5B5EE053DD81A8C03086');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2A752EA4021C5D5AE053DD81A8C0F99C', '265DD2ADFDB469EFE053DD81A8C04F19');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2A752EA4021C5D5AE053DD81A8C0F99C', '265DD2ADFDB569EFE053DD81A8C04F19');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('2A752EA4021C5D5AE053DD81A8C0F99C', '37D1BEC1CE1B04D8E053BA1201015A53');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97dddd0001');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97dde90002');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97ddf50003');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de020004');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de130005');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de190006');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de1f0007');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de240008');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de2b0009');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de31000a');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47dcd6be0147dcd6c8ae0000', '4028018c47ed97d00147ed97de37000b');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1053FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1063FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1073FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1083FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1093FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10A3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10B3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10C3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10D3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10D3FE3E153DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10E3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A10F3FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1103FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1113FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1123FE3E053D281A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1123FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1133FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1143FE3E053DD81A8C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1153FE3E053DD2148C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1153FE3E053DD8148C01952');

insert into T_ROLE_RESOURCE (ROLE_ID, RESOURCE_ID)
values ('4028018c47ed92cd0147ed92d7810000', '24503A24A1153FE3E053DD81A8C01952');

-- T_USER...

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('40280185538775320153877c210a0000', '1', '10001015', '1', '0', null, null, null, null, null, null, null, '网厅测试单位', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320002', '2', '3501830933', '1', '1', '01', '110101195404150073', null, '46010896440184@UserData.com', null, '18888888888', null, '范维智1', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, '20001010', '20160212');

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320004', '2', '3501830935', '1', '0', '01', '410101198010010136', null, '46010896440185@UserData.com', null, '12345678903', null, '范维智3', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320005', '2', '3501830936', '1', '0', '01', '120104198203060447', null, '46010896440186@UserData.com', null, '12345678904', null, '范维智4', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320006', '2', '3501830937', '1', '0', '01', '120110198205100278', null, '46010896440187@UserData.com', null, '12345678905', null, '范维智5', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320007', '2', '3501830938', '1', '0', '01', '120101195502180016', null, '46010896440188@UserData.com', null, '12345678906', null, '范维智6', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('4028018c4886e455014886e461320001', '2', '3501830932', '1', '1', '01', '320706195405220516', null, '46010896440183@UserData.com', null, '12345678901', null, '范维智', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('ff80808157c285bb0157c28bb3080000', '1', '10074942', '1', '0', null, null, null, null, null, null, null, 'test', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('ff80808157c2759a0157c27fe3420000', '2', '57443990f3de4624ac5ab6f5591ae48b', '1', '0', '01', '320706195505220516', null, null, null, '15140204966', null, '登录测试', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

insert into T_USER (ID, TYPE, ACCOUNT, ACTIVATED, REAL_NAME_AUTHED, ID_TYPE, ID_NUMBER, CAKEY, EMAIL, ACCOUNT_LOCKED_REASON, MOBILE, HEADIMGURL, NAME, PASSWORD, SALT, DESCRIPTION, ORG_ID, REGISTED_DATE, REAL_NAME_DATE)
values ('1', '0', 'admin', '1', '0', null, null, null, null, null, null, null, '系统管理员', '4QrcOUm6Wau+VuBX8g+IPg==', null, null, null, null, null);

--T_USER_COMPANY...

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('40280185538775320153877c210a0000', 87856601226);

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('40280185538775320153877c210a0000', 87856601227);

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('4028018c4886e455014886e461320001', 360028011855827);

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('4028018c4886e455014886e461320001', 360028011861946);

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('4028018c4886e455014886e461320001', 360028011950484);

insert into T_USER_COMPANY (USER_ID, COMPANY_ID)
values ('ff80808157c285bb0157c28bb3080000', 87856601228);
--T_USER_EXTEND...

insert into T_USER_EXTEND (USER_ID, OHWYAA_ACTIVED, MAIN_COMPANY_NUM, CA_OPER_TYPE, CA_OPER_TIME)
values ('40280185538775320153877c210a0000', '0', '10001015', '1', 20160812);

insert into T_USER_EXTEND (USER_ID, OHWYAA_ACTIVED, MAIN_COMPANY_NUM, CA_OPER_TYPE, CA_OPER_TIME)
values ('ff80808157c285bb0157c28bb3080000', '0', '10074942', '0', 20161014);

insert into T_USER_EXTEND (USER_ID, OHWYAA_ACTIVED, MAIN_COMPANY_NUM, CA_OPER_TYPE, CA_OPER_TIME)
values ('ff80808157c2759a0157c27fe3420000', '0', null, null, null);

--T_USER_PERSON...
 
set define off
insert into T_USER_PERSON (USER_ID, PERSON_ID)
values ('4028018c4886e455014886e461320001', 21004779622);

insert into T_USER_PERSON (USER_ID, PERSON_ID)
values ('ff80808157c2759a0157c27fe3420000', 21004779623);

--T_USER_ROLE...

insert into T_USER_ROLE (USER_ID, ROLE_ID)
values ('1', '4028018c47ed92cd0147ed92d7820000');

insert into T_USER_ROLE (USER_ID, ROLE_ID)
values ('40280185538775320153877c210a0000', '1F3A63BB06A818A1E053DD81A8C0D667');

insert into T_USER_ROLE (USER_ID, ROLE_ID)
values ('4028018c4886e455014886e461320001', '2230B0EC6BA306F0E053DD81A8C091D5');

insert into T_USER_ROLE (USER_ID, ROLE_ID)
values ('ff80808157c2759a0157c27fe3420000', '2230B0EC6BA306F0E053DD81A8C091D5');

insert into T_USER_ROLE (USER_ID, ROLE_ID)
values ('ff80808157c285bb0157c28bb3080000', '4028018c47ed92cd0147ed92d7810000');