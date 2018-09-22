-- ----------------------------
-- Table structure for act_ext_hi_task 23423432111111111111111111111-- ----------------------------
DROP TABLE IF EXISTS `act_ext_hi_task`;
CREATE TABLE `act_ext_hi_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(64) DEFAULT NULL,
  `task_parent_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;


-- ----------------------------
-- View structure for act_id_group
-- ----------------------------
DROP VIEW IF EXISTS `act_id_group`;
CREATE ALGORITHM=UNDEFINED DEFINER=`summer`@`%` SQL SECURITY DEFINER VIEW `act_id_group` AS select `sys_role`.`id` AS `ID_`,1 AS `REV_`,`sys_role`.`name` AS `NAME_`,`sys_role`.`code` AS `TYPE_` from `sys_role` ;

-- ----------------------------
-- View structure for act_id_info
-- ----------------------------
DROP VIEW IF EXISTS `act_id_info`;
CREATE ALGORITHM=UNDEFINED DEFINER=`summer`@`%` SQL SECURITY DEFINER VIEW `act_id_info` AS select `sys_user`.`id` AS `ID_`,1 AS `REV_`,`sys_user`.`id` AS `USER_ID_`,'type' AS `TYPE_`,'key' AS `KEY_`,'values' AS `VALUE_`,`sys_user`.`password` AS `PASSWORD_`,'PARENT_ID' AS `PARENT_ID_` from `sys_user` ;

-- ----------------------------
-- View structure for act_id_membership
-- ----------------------------
DROP VIEW IF EXISTS `act_id_membership`;
CREATE ALGORITHM=UNDEFINED DEFINER=`summer`@`%` SQL SECURITY DEFINER VIEW `act_id_membership` AS select `sys_role_user`.`user_id` AS `USER_ID_`,`sys_role_user`.`role_id` AS `GROUP_ID_` from `sys_role_user` ;

-- ----------------------------
-- View structure for act_id_user
-- ----------------------------
DROP VIEW IF EXISTS `act_id_user`;
CREATE ALGORITHM=UNDEFINED DEFINER=`summer`@`%` SQL SECURITY DEFINER VIEW `act_id_user` AS select `sys_user`.`id` AS `ID_`,1 AS `REV_`,`sys_user`.`name` AS `FIRST_`,`sys_user`.`name` AS `LAST_`,`sys_user`.`phone` AS `EMAIL_`,`sys_user`.`password` AS `PWD_`,`sys_user`.`photo` AS `PICTURE_ID_` from `sys_user` ;
