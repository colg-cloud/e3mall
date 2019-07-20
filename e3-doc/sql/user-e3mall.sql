-- 删除用户及权限
DROP USER 'e3mall';

-- 创建用户
CREATE USER 'e3mall' IDENTIFIED BY '123456';

-- 为用户授权
GRANT ALL ON e3mall.* TO 'e3mall';

-- 刷新权限
FLUSH PRIVILEGES;

-- 查看host、用户、密码
SELECT HOST, USER, authentication_string FROM mysql.user;