SELECT * FROM tb_user;				-- 用户表
SELECT * FROM tb_content;			-- 内容表
SELECT * FROM tb_content_category;		-- 内容分类表
SELECT * FROM tb_item ORDER BY created DESC;	-- 商品表
SELECT * FROM tb_item_desc;			-- 商品详情
SELECT * FROM tb_item_cat;			-- 商品分类
SELECT * FROM tb_item_param;			-- 商品规格模版表
SELECT * FROM tb_item_param_item;		-- 商品规格参数表
DELETE FROM tb_item WHERE STATUS = 3;

SHOW FULL COLUMNS FROM tb_item;
SHOW FULL COLUMNS FROM tb_item_cat;
SHOW FULL COLUMNS FROM tb_content;
SHOW FULL COLUMNS FROM tb_content_category;
SHOW FULL COLUMNS FROM tb_item_param;
SHOW FULL COLUMNS FROM tb_item_param_item;

-- EasyUI tree
-- 树控件数据格式化
-- 每个节点都具备以下属性：
-- 
-- id：节点ID，对加载远程数据很重要。
-- text：显示节点文本。
-- state：节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
-- checked：表示该节点是否被选中。
-- attributes: 被添加到节点的自定义属性。
-- children: 一个节点数组声明了若干节点。
-- 商品类目 tree
SELECT
    ic.id, ic.name `text`, IF(ic.is_parent = 1, 'closed', 'open') state
FROM
    tb_item_cat ic
WHERE ic.parent_id = 0 AND ic.status = 1
ORDER BY ic.sort_order;

-- 内容分类 tree
SELECT
    cc.id, cc.name `text`, IF(cc.is_parent = 1, 'closed', 'open') state
FROM
    tb_content_category cc
WHERE cc.parent_id = 30 AND cc.status = 1
ORDER BY cc.sort_order;

-- 商品规格参数列表
SELECT
    ip.id, ip.item_cat_id, ip.param_data, ip.created, ip.updated,
    ic.name `item_cat_name`
FROM
    tb_item_param ip
    INNER JOIN tb_item_cat ic ON ic.id = ip.item_cat_id
WHERE ic.status = 1
ORDER BY ip.updated DESC LIMIT 30;

SELECT
    ip.*,
    ic.*
FROM
    tb_item_param ip
    INNER JOIN tb_item_cat ic ON ic.id = ip.item_cat_id
WHERE ic.status = 1
ORDER BY ip.updated DESC LIMIT 30;

SELECT
    cc.*
FROM
    tb_content_category cc
WHERE cc.parent_id = 30 AND cc.status = 2;

SELECT * FROM tb_content_category cc WHERE cc.id = 30



SELECT tca.*
FROM tb_content_category tca
WHERE tca.parent_id = 0 AND tca.status = 1;

SELECT
    c.*
FROM
    tb_content c
WHERE c.category_id = 90;

DELETE FROM tb_content
WHERE category_id = ?
 id IN ()

SELECT ic.id, ic.name `text`, IF(ic.is_parent = 1, 'closed', 'open') state FROM tb_item_cat ic WHERE ic.parent_id = 0 AND ic.status = 1 ORDER BY ic.sort_order;

-- 获取主键
SELECT LAST_INSERT_ID();

SELECT
    c.*
FROM
    tb_content c
WHERE c.category_id = 101;

-- 查询商品
SELECT ti.id, ti.title, ti.sell_point, ti.price, ti.image, tbc.name category_name
FROM tb_item ti
LEFT JOIN tb_item_cat tbc ON tbc.id = ti.cid
WHERE ti.status = 1;

        
SHOW INDEX FROM tb_item_param;
SHOW INDEX FROM tb_item_cat;

SHOW CREATE TABLE tb_item_param;
        
-- 添加索引
DROP INDEX idx_item_cat_id ON tb_item_param;

ALTER TABLE tb_item_param ADD INDEX idx_item_cat_id(item_cat_id);