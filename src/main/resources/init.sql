INSERT INTO role (id, name,display_name) VALUES (1, 'ROLE_ROOT','超级管理员');
INSERT INTO role (id, name,display_name) VALUES (2, 'ROLE_ADMIN','管理员');
INSERT INTO role (id, name,display_name) VALUES (3, 'ROLE_USER','用户');
#
INSERT INTO user (id, username, password, email,avatar,hot_value,update_time) VALUES (1, 'root', '123456', 'root@mijack.cn','http://localhost:8080/favicon.ico',0,'2017-09-26 00:00:00');
INSERT INTO user (id, username, password, email,avatar,hot_value,update_time) VALUES (2, 'admin', '123456', 'admin@mijack.cn','http://localhost:8080/favicon.ico',0,'2017-09-26 00:00:00');
INSERT INTO user (id, username, password, email,avatar,hot_value,update_time) VALUES (3, 'mijack', '123456', 'mijack@mijack.cn','http://localhost:8080/favicon.ico',0,'2017-09-26 00:00:00');
#
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);
#
INSERT INTO category (category.id, category.`name`) VALUES (1, "算法");
INSERT INTO category (category.id, category.`name`) VALUES (2, "架构");
INSERT INTO category (category.id, category.`name`) VALUES (3, "移动");
INSERT INTO category (category.id, category.`name`) VALUES (4, "前端");
INSERT INTO category (category.id, category.`name`) VALUES (5, "后端");
INSERT INTO category (category.id, category.`name`) VALUES (6, "机器学习");
INSERT INTO category (category.id, category.`name`) VALUES (7, "产品");
INSERT INTO category (category.id, category.`name`) VALUES (8, "设计");
INSERT INTO category (category.id, category.`name`) VALUES (9, "工具");
INSERT INTO category (category.id, category.`name`) VALUES (10, "安全");