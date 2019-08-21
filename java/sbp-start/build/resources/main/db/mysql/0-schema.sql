CREATE
    database wsat DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

use wsat;

-- MySQL dump 10.13  Distrib 5.7.23, for osx10.14 (x86_64)
 --
 -- Host: 180.100.74.29    Database: cscan
 -- ------------------------------------------------------
 -- Server version	5.6.43
 /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;

/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;

/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;

/*!40101 SET NAMES utf8 */;

/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;

/*!40103 SET TIME_ZONE='+00:00' */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
 -- Table structure for table `asset`
 --
 DROP
    TABLE
        IF EXISTS ` asset `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` asset_id ` VARCHAR(40) DEFAULT NULL,
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` asset_source ` enum(
                'user',
                'system'
            ) DEFAULT 'user' COMMENT '资产来源',
            ` asset_name ` VARCHAR(100) DEFAULT NULL,
            ` asset_ip ` VARCHAR(64) DEFAULT NULL,
            ` asset_url ` VARCHAR(1024) DEFAULT NULL COMMENT '资产地址，可能是域名/URL',
            ` root_domain ` VARCHAR(128) DEFAULT NULL COMMENT '根域名',
            ` DOMAIN ` VARCHAR(128) DEFAULT NULL COMMENT '域名',
            ` owner ` VARCHAR(100) DEFAULT NULL,
            ` owner_email ` VARCHAR(100) DEFAULT NULL,
            ` asset_security_index ` FLOAT DEFAULT '100',
            ` is_managed ` tinyint(11) unsigned DEFAULT '0' COMMENT '是否设置了安全托管，0 - 未托管 1 - 已托管',
            ` last_scan_id ` VARCHAR(40) DEFAULT NULL,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` uk_url `(
                ` asset_url `(191)
            )
                USING BTREE,
            KEY ` last_scan_id `(
                ` last_scan_id `
            ),
            KEY ` asset_id `(
                ` asset_id `
            ),
            KEY ` idx_ip `(
                ` asset_ip `
            )
                USING BTREE,
            KEY ` idx_name `(
                ` asset_name `
            )
                USING BTREE,
            CONSTRAINT ` scan ` FOREIGN KEY(
                ` last_scan_id `
            ) REFERENCES ` scan `(
                ` scan_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL
        ) ENGINE = InnoDB AUTO_INCREMENT = 88736 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_component`
 --
 DROP
    TABLE
        IF EXISTS ` asset_component `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_component `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` asset_id ` VARCHAR(40) DEFAULT NULL,
            ` c_id ` VARCHAR(40) DEFAULT NULL COMMENT '组件 ID',
            ` c_name ` VARCHAR(64) DEFAULT NULL COMMENT '组件名，当组件不是系统中支持扫描的组件时（在 components 中存在），c_id 置空，这里存放组件名',
            ` ac_desc ` VARCHAR(100) DEFAULT NULL,
            ` ac_properties ` text,
            ` SOURCE ` enum(
                'user',
                'system'
            ) DEFAULT NULL COMMENT '组件的扫描来源',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` asset_id `(
                ` asset_id `
            ),
            KEY ` c_id `(
                ` c_id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 1845145 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_crawler`
 --
 DROP
    TABLE
        IF EXISTS ` asset_crawler `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_crawler `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT,
            ` asset_id ` VARCHAR(40) DEFAULT NULL,
            ` RESULT ` text,
            ` created_at ` datetime DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` datetime DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` uk_asset_id `(
                ` asset_id `
            )
                USING BTREE,
            CONSTRAINT ` fk_asset_id ` FOREIGN KEY(
                ` asset_id `
            ) REFERENCES ` asset `(
                ` asset_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL
        ) ENGINE = InnoDB AUTO_INCREMENT = 52442 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_draft`
 --
 DROP
    TABLE
        IF EXISTS ` asset_draft `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_draft `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT,
            ` asset_draft_id ` VARCHAR(40) DEFAULT NULL,
            ` ip ` VARCHAR(40) DEFAULT NULL,
            ` port ` INT(11) DEFAULT NULL,
            ` service ` VARCHAR(40) DEFAULT NULL,
            ` protocol ` VARCHAR(40) DEFAULT NULL,
            ` title ` VARCHAR(200) DEFAULT NULL,
            ` footprint ` text COMMENT '指纹',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` UNIQUE `(
                ` asset_draft_id `
            )
                USING BTREE,
            UNIQUE KEY ` uk_ip_port `(
                ` ip `,
                ` port `
            )
                USING BTREE,
            KEY ` idx_ip `(
                ` ip `
            )
                USING BTREE
        ) ENGINE = InnoDB AUTO_INCREMENT = 7704050 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_group`
 --
 DROP
    TABLE
        IF EXISTS ` asset_group `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_group `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内置主键',
            ` asset_group_id ` VARCHAR(40) DEFAULT NULL COMMENT '资产组编号',
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` asset_group_name ` VARCHAR(100) DEFAULT NULL COMMENT '资产组名称',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            ` asset_group_department_id ` VARCHAR(40) DEFAULT NULL,
            ` asset_group_department_name ` VARCHAR(100) DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 271 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_group_mapping`
 --
 DROP
    TABLE
        IF EXISTS ` asset_group_mapping `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_group_mapping `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内置主键',
            ` asset_id ` VARCHAR(40) DEFAULT NULL COMMENT '关联资产编号',
            ` asset_group_id ` VARCHAR(40) DEFAULT NULL COMMENT '关联资产组编号',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` UNIQUE `(
                ` asset_id `,
                ` asset_group_id `
            )
                USING BTREE
        ) ENGINE = InnoDB AUTO_INCREMENT = 141668 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_url_path`
 --
 DROP
    TABLE
        IF EXISTS ` asset_url_path `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_url_path `(
            ` _id ` INT(11) NOT NULL AUTO_INCREMENT,
            ` asset_url_path_id ` VARCHAR(40) NOT NULL COMMENT 'SHA1(path) 作为其值',
            ` PATH ` VARCHAR(500) DEFAULT NULL,
            ` DOMAIN ` VARCHAR(1024) NOT NULL,
            ` scheme ` VARCHAR(50) DEFAULT 'http' COMMENT 'http/https/...',
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` ind_asset_url_path `(
                ` PATH `(191)
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 874980 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_vuln`
 --
 DROP
    TABLE
        IF EXISTS ` asset_vuln `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_vuln `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` av_id ` VARCHAR(40) DEFAULT NULL,
            ` asset_id ` VARCHAR(40) DEFAULT NULL,
            ` c_id ` VARCHAR(49) DEFAULT NULL,
            ` scan_id ` VARCHAR(40) DEFAULT NULL,
            ` task_id ` VARCHAR(40) DEFAULT NULL,
            ` poc_id ` VARCHAR(40) DEFAULT NULL,
            ` strategy_id ` VARCHAR(40) DEFAULT NULL COMMENT '关联的策略编号',
            ` vuln_id ` VARCHAR(40) DEFAULT NULL,
            ` detail ` text,
            ` is_false_alarm ` INT(11) DEFAULT '0',
            ` is_patch ` INT(11) DEFAULT '0',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00',
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` _asset_vuln_uniq `(
                ` asset_id `,
                ` vuln_id `,
                ` scan_id `
            ),
            KEY ` asset_id `(
                ` asset_id `
            ),
            KEY ` av_id `(
                ` av_id `
            )
                USING BTREE,
            KEY ` c_id `(
                ` c_id `
            ),
            KEY ` scan_id `(
                ` scan_id `
            ),
            KEY ` task_id `(
                ` task_id `
            ),
            KEY ` poc_id `(
                ` poc_id `
            ),
            KEY ` vuln_id `(
                ` vuln_id `
            ),
            CONSTRAINT ` asset_id ` FOREIGN KEY(
                ` asset_id `
            ) REFERENCES ` asset `(
                ` asset_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL,
                    CONSTRAINT ` c_id ` FOREIGN KEY(
                        ` c_id `
                    ) REFERENCES ` component `(
                        ` c_id `
                    ) ON
                    DELETE
                    SET
                        NULL ON
                        UPDATE
                        SET
                            NULL,
                            CONSTRAINT ` fk_asset_vuln_vuln ` FOREIGN KEY(
                                ` vuln_id `
                            ) REFERENCES ` vuln `(
                                ` vuln_id `
                            ) ON
                            DELETE
                                NO ACTION ON
                                UPDATE
                                    NO ACTION,
                                    CONSTRAINT ` poc_id ` FOREIGN KEY(
                                        ` poc_id `
                                    ) REFERENCES ` poc `(
                                        ` poc_id `
                                    ) ON
                                    DELETE
                                    SET
                                        NULL ON
                                        UPDATE
                                        SET
                                            NULL,
                                            CONSTRAINT ` scan_id ` FOREIGN KEY(
                                                ` scan_id `
                                            ) REFERENCES ` scan `(
                                                ` scan_id `
                                            ) ON
                                            DELETE
                                            SET
                                                NULL ON
                                                UPDATE
                                                SET
                                                    NULL
        ) ENGINE = InnoDB AUTO_INCREMENT = 24689 DEFAULT CHARSET = utf8mb4 COMMENT = '资产漏洞统计表';

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `asset_vuln_history`
 --
 DROP
    TABLE
        IF EXISTS ` asset_vuln_history `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` asset_vuln_history `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` av_id ` VARCHAR(40) DEFAULT NULL,
            ` asset_id ` VARCHAR(40) DEFAULT NULL,
            ` c_id ` VARCHAR(49) DEFAULT NULL,
            ` scan_id ` VARCHAR(40) DEFAULT NULL,
            ` task_id ` VARCHAR(40) DEFAULT NULL,
            ` poc_id ` VARCHAR(40) DEFAULT NULL,
            ` strategy_id ` VARCHAR(40) DEFAULT NULL COMMENT '关联的策略编号',
            ` vuln_id ` VARCHAR(40) DEFAULT NULL,
            ` detail ` text,
            ` is_false_alarm ` INT(11) DEFAULT '0',
            ` is_patch ` INT(11) DEFAULT '0',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` _asset_vuln_uniq `(
                ` asset_id `,
                ` vuln_id `,
                ` scan_id `
            ),
            KEY ` asset_id `(
                ` asset_id `
            ),
            KEY ` av_id `(
                ` av_id `
            )
                USING BTREE,
            KEY ` c_id `(
                ` c_id `
            ),
            KEY ` scan_id `(
                ` scan_id `
            ),
            KEY ` task_id `(
                ` task_id `
            ),
            KEY ` poc_id `(
                ` poc_id `
            ),
            KEY ` vuln_id `(
                ` vuln_id `
            ),
            CONSTRAINT ` asset_vuln_history_ibfk_1 ` FOREIGN KEY(
                ` asset_id `
            ) REFERENCES ` asset `(
                ` asset_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL,
                    CONSTRAINT ` asset_vuln_history_ibfk_2 ` FOREIGN KEY(
                        ` c_id `
                    ) REFERENCES ` component `(
                        ` c_id `
                    ) ON
                    DELETE
                    SET
                        NULL ON
                        UPDATE
                        SET
                            NULL,
                            CONSTRAINT ` asset_vuln_history_ibfk_3 ` FOREIGN KEY(
                                ` vuln_id `
                            ) REFERENCES ` vuln `(
                                ` vuln_id `
                            ) ON
                            DELETE
                                NO ACTION ON
                                UPDATE
                                    NO ACTION,
                                    CONSTRAINT ` asset_vuln_history_ibfk_4 ` FOREIGN KEY(
                                        ` poc_id `
                                    ) REFERENCES ` poc `(
                                        ` poc_id `
                                    ) ON
                                    DELETE
                                    SET
                                        NULL ON
                                        UPDATE
                                        SET
                                            NULL,
                                            CONSTRAINT ` asset_vuln_history_ibfk_5 ` FOREIGN KEY(
                                                ` scan_id `
                                            ) REFERENCES ` scan `(
                                                ` scan_id `
                                            ) ON
                                            DELETE
                                            SET
                                                NULL ON
                                                UPDATE
                                                SET
                                                    NULL
        ) ENGINE = InnoDB AUTO_INCREMENT = 28203 DEFAULT CHARSET = utf8mb4 COMMENT = '资产漏洞统计表';

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `component`
 --
 DROP
    TABLE
        IF EXISTS ` component `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` component `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` c_id ` VARCHAR(40) DEFAULT NULL COMMENT '组件编号',
            ` c_name ` VARCHAR(64) DEFAULT NULL,
            ` c_first ` VARCHAR(10) DEFAULT NULL,
            ` c_type ` enum(
                'cms',
                'os',
                'middleware',
                'database',
                'device',
                'others',
                'service',
                'service_provider'
            ) DEFAULT NULL,
            ` DESC ` text,
            ` producer ` VARCHAR(100) DEFAULT NULL,
            ` properties ` text,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` c_id `(
                ` c_id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 8221 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `component_strategy`
 --
 DROP
    TABLE
        IF EXISTS ` component_strategy `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` component_strategy `(
            ` _id ` INT(11) NOT NULL AUTO_INCREMENT,
            ` strategry_id ` VARCHAR(64) NOT NULL COMMENT '策略 ID',
            ` c_id ` VARCHAR(64) NOT NULL,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            )
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '扫描/执行策略';

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `config`
 --
 DROP
    TABLE
        IF EXISTS ` config `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` config `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT,
            ` asset_discovery ` text,
            PRIMARY KEY(
                ` _id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `job`
 --
 DROP
    TABLE
        IF EXISTS ` job `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` job `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT,
            ` job_id ` VARCHAR(40) DEFAULT NULL,
            ` job_type ` enum('asset-discovery') DEFAULT NULL,
            ` container_id ` VARCHAR(64) DEFAULT NULL,
            ` job_status ` enum(
                'WAITING',
                'RUNNING',
                'FINISHED',
                'FAILED',
                'CANCELED'
            ) DEFAULT 'WAITING',
            ` job_result ` text,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            ` exec_option ` text COMMENT '执行参数，JSON Array<string>',
            ` scheduled_at ` datetime DEFAULT CURRENT_TIMESTAMP,
            PRIMARY KEY(
                ` _id `
            )
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `poc`
 --
 DROP
    TABLE
        IF EXISTS ` poc `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` poc `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` poc_id ` VARCHAR(40) DEFAULT NULL,
            ` vuln_id ` VARCHAR(40) DEFAULT NULL,
            ` poc_name ` VARCHAR(200) DEFAULT NULL,
            ` author ` VARCHAR(50) DEFAULT NULL,
            ` create_time ` DATE DEFAULT NULL,
            ` image_name ` VARCHAR(200) DEFAULT NULL,
            ` args ` text,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` poc_id `(
                ` poc_id `
            ),
            KEY ` vuln_id `(
                ` vuln_id `
            ),
            CONSTRAINT ` fk_poc_vuln ` FOREIGN KEY(
                ` vuln_id `
            ) REFERENCES ` vuln `(
                ` vuln_id `
            ) ON
            DELETE
                NO ACTION ON
                UPDATE
                    NO ACTION
        ) ENGINE = InnoDB AUTO_INCREMENT = 26497 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `scan`
 --
 DROP
    TABLE
        IF EXISTS ` scan `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` scan `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` scan_id ` VARCHAR(40) DEFAULT NULL,
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` scan_name ` VARCHAR(100) DEFAULT NULL,
            ` scan_type ` enum(
                'basic',
                'directional',
                'manual',
                'intelligent',
                'synth'
            ) DEFAULT NULL,
            ` scan_source ` enum(
                'user',
                'trusteeship'
            ) DEFAULT 'user' COMMENT '扫描的创建来源',
            ` scan_config ` text COMMENT '扫描的额外配置，JSON 格式',
            ` asset_ids ` text,
            ` asset_group_ids ` text COMMENT '资产组列表',
            ` c_ids ` text,
            ` poc_ids ` text COMMENT 'List<poc_id>',
            ` strategy_ids ` text COMMENT 'Map<asset_id, List<strategy_id>>',
            ` trusteeship_id ` VARCHAR(40) DEFAULT NULL COMMENT '关联的托管编号，当存在托管时候，不显示在扫描列表中',
            ` task_num ` INT(11) DEFAULT '0',
            ` scan_status ` enum(
                'WAITING',
                'RUNNING',
                'FINISHED',
                'FAILED',
                'CANCELED',
                'SUSPENDED'
            ) DEFAULT 'WAITING',
            ` scan_progress ` FLOAT(
                10,
                5
            ) DEFAULT '0.00000',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` scan_id `(
                ` scan_id `
            ),
            KEY ` trusteeship_id `(
                ` trusteeship_id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 924 DEFAULT CHARSET = utf8mb4 COMMENT = '父扫描 ID';

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `strategy`
 --
 DROP
    TABLE
        IF EXISTS ` strategy `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` strategy `(
            ` _id ` INT(11) NOT NULL AUTO_INCREMENT,
            ` strategy_id ` VARCHAR(64) NOT NULL COMMENT '策略 ID',
            ` strategy_name ` VARCHAR(100) NOT NULL,
            ` author ` VARCHAR(32) DEFAULT NULL,
            ` DESC ` text COMMENT '策略描述',
            ` image_name ` VARCHAR(200) DEFAULT NULL,
            ` create_time ` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `,
                ` created_at `,
                ` create_time `
            ),
            UNIQUE KEY ` unq_strategy_strategy_id `(
                ` strategy_id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 20 DEFAULT CHARSET = utf8mb4 COMMENT = '扫描/执行策略';

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `task`
 --
 DROP
    TABLE
        IF EXISTS ` task `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` task `(
            ` _id ` INT(11) NOT NULL AUTO_INCREMENT,
            ` owner_id ` VARCHAR(40) DEFAULT NULL,
            ` task_id ` VARCHAR(40) DEFAULT NULL,
            ` kind ` enum(
                'scan',
                'job'
            ) DEFAULT NULL,
            ` TYPE ` VARCHAR(40) DEFAULT NULL,
            ` status ` enum(
                'WAITING',
                'EXECUTING',
                'EXECUTED',
                'FAILED',
                'EXPIRED',
                'FINISHED'
            ) DEFAULT 'WAITING',
            ` retry_num ` INT(11) DEFAULT '0',
            ` expire_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON
            UPDATE
                CURRENT_TIMESTAMP,
                PRIMARY KEY(
                    ` _id `
                ),
                KEY ` _0 `(
                    ` owner_id `
                ),
                KEY ` _1 `(
                    ` status `
                ),
                KEY ` _2 `(
                    ` expire_at `
                ),
                KEY ` _3 `(
                    ` kind `
                ),
                KEY ` _4 `(
                    ` TYPE `
                )
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `terminal`
 --
 DROP
    TABLE
        IF EXISTS ` terminal `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` terminal `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` terminal_id ` VARCHAR(40) DEFAULT NULL,
            ` terminal_name ` VARCHAR(100) DEFAULT NULL,
            ` terminal_organization ` VARCHAR(100) DEFAULT NULL,
            ` max_worker_num ` INT(11) DEFAULT '8',
            ` max_retry_num ` INT(11) DEFAULT '3',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            )
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `trusteeship`
 --
 DROP
    TABLE
        IF EXISTS ` trusteeship `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` trusteeship `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内置主键',
            ` trusteeship_id ` VARCHAR(40) DEFAULT NULL COMMENT '托管的编号',
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` trusteeship_name ` VARCHAR(40) DEFAULT NULL COMMENT '托管名称',
            ` asset_ids ` text COMMENT '关联的资产编号，JSONArray',
            ` asset_group_ids ` text COMMENT '关联的资产组编号，JSONArray',
            ` scan_types ` VARCHAR(100) DEFAULT '' COMMENT '支持的扫描类型，JSONArray',
            ` scan_period ` enum(
                'day',
                'week',
                'month'
            ) DEFAULT 'day' COMMENT '扫描周期',
            ` vuln_level ` tinyint(4) DEFAULT '3' COMMENT '漏洞等级 1 - 低危 2 - 中危 3 - 高危',
            ` is_http_available ` tinyint(4) DEFAULT '0' COMMENT 'HTTP 服务是否可用， 0 - 不关注 1 - 关注',
            ` is_ping_available ` tinyint(4) DEFAULT '0' COMMENT 'PING 服务是否可用， 0 - 不关注 1 - 关注',
            ` notification ` tinyint(4) DEFAULT '1' COMMENT '1 - 短信 2- 邮件 3 - 系统提示',
            ` phone ` VARCHAR(15) DEFAULT NULL,
            ` email ` VARCHAR(20) DEFAULT NULL,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` UNIQUE `(
                ` trusteeship_id `
            )
                USING BTREE
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `user`
 --
 DROP
    TABLE
        IF EXISTS ` USER `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` USER `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` username ` VARCHAR(32) DEFAULT NULL,
            ` password ` VARCHAR(64) DEFAULT NULL,
            ` user_role ` enum(
                'master',
                'developer',
                'reporter',
                'guest'
            ) DEFAULT 'developer',
            ` tenant_id ` VARCHAR(40) DEFAULT NULL COMMENT '租户 ID',
            ` salt ` VARCHAR(64) DEFAULT NULL,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` user_id `(
                ` user_id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 22 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `user_log`
 --
 DROP
    TABLE
        IF EXISTS ` user_log `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` user_log `(
            ` id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '内置主键',
            ` user_id ` VARCHAR(40) DEFAULT NULL COMMENT '用户编号',
            ` ip ` VARCHAR(40) DEFAULT NULL COMMENT '用户来源',
            ` resource_id ` VARCHAR(40) DEFAULT NULL COMMENT '操作的资源编号',
            ` resource_type ` VARCHAR(20) DEFAULT NULL COMMENT '资源类型',
            ` resource_name ` VARCHAR(200) DEFAULT NULL,
            ` ACTION ` VARCHAR(20) DEFAULT NULL COMMENT '操作类型',
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` id `
            )
        ) ENGINE = InnoDB AUTO_INCREMENT = 93 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `user_token`
 --
 DROP
    TABLE
        IF EXISTS ` user_token `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` user_token `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT,
            ` user_id ` VARCHAR(40) DEFAULT NULL,
            ` token ` VARCHAR(40) DEFAULT NULL,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            KEY ` user_id `(
                ` user_id `
            ),
            CONSTRAINT ` user_token ` FOREIGN KEY(
                ` user_id `
            ) REFERENCES ` USER `(
                ` user_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL
        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

--
 -- Table structure for table `vuln`
 --
 DROP
    TABLE
        IF EXISTS ` vuln `;

/*!40101 SET @saved_cs_client     = @@character_set_client */;

/*!40101 SET character_set_client = utf8 */;

CREATE
    TABLE
        ` vuln `(
            ` _id ` INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '_ID，内部自增编号',
            ` vuln_id ` VARCHAR(40) DEFAULT NULL,
            ` vuln_name ` VARCHAR(100) DEFAULT NULL,
            ` vuln_type ` INT(11) DEFAULT NULL,
            ` c_id ` VARCHAR(40) DEFAULT NULL,
            ` c_version ` VARCHAR(200) DEFAULT NULL,
            ` cnvd_id ` VARCHAR(15) DEFAULT NULL,
            ` cve_id ` VARCHAR(128) DEFAULT NULL,
            ` disclosure_date ` DATE DEFAULT NULL COMMENT '披露时间',
            ` submit_time ` DATE DEFAULT NULL,
            ` LEVEL ` INT(11) DEFAULT '0',
            ` SOURCE ` text,
            ` detail ` text COMMENT '漏洞介绍',
            ` exploit ` text COMMENT '漏洞发现/利用详情 HTML',
            ` analysis ` text,
            ` created_at ` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
            ` updated_at ` TIMESTAMP NULL DEFAULT NULL,
            ` deleted_at ` TIMESTAMP NULL DEFAULT NULL,
            PRIMARY KEY(
                ` _id `
            ),
            UNIQUE KEY ` uniq_vuln_id `(
                ` vuln_id `
            ),
            KEY ` c_id `(
                ` c_id `
            ),
            CONSTRAINT ` 组件 ` FOREIGN KEY(
                ` c_id `
            ) REFERENCES ` component `(
                ` c_id `
            ) ON
            DELETE
            SET
                NULL ON
                UPDATE
                SET
                    NULL
        ) ENGINE = InnoDB AUTO_INCREMENT = 25705 DEFAULT CHARSET = utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;

/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;

/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-14 17:39:22

