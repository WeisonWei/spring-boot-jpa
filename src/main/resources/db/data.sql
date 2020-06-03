/** -- t_user --**/
insert into t_user (id,name,sex,age,birth_day) values ( 1,'张三',1,20,"2019-09-18");
insert into t_user (id,name,sex,age,birth_day) values ( 2,'李四',2,28,"2019-09-19");

/** -- t_account --**/
SELECT * FROM "t_account";
insert into "t_account" ("id","user_id","account_type","trans_type","amount","balance") values ( 1,123,2,1,1,0);
insert into "t_account" ("id","user_id","account_type","trans_type","amount","balance") values ( 2,123,2,1,1,1);
insert into "t_account" ("id","user_id","account_type","trans_type","amount","balance") values ( 3,123,2,1,1,2);
