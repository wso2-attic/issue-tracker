--<!--
-- ~ Copyright (c) 2005-2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
-- ~
-- ~ Licensed under the Apache License, Version 2.0 (the "License");
-- ~ you may not use this file except in compliance with the License.
-- ~ You may obtain a copy of the License at
-- ~
-- ~      http://www.apache.org/licenses/LICENSE-2.0
-- ~
-- ~ Unless required by applicable law or agreed to in writing, software
-- ~ distributed under the License is distributed on an "AS IS" BASIS,
-- ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- ~ See the License for the specific language governing permissions and
-- ~ limitations under the License.
--

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `issueTrackerDb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
use issueTrackerDb;
-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Project`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS PROJECT (
    PROJECT_ID          INTEGER NOT NULL AUTO_INCREMENT,
    PROJECT_NAME        VARCHAR(200) NULL,
    OWNER               VARCHAR(100) NULL,
    DESCRIPTION         TEXT         NULL,
    ORGANIZATION_ID     INTEGER DEFAULT 0,
    PROJECT_KEY         VARCHAR(100) NOT NULL,
    CONSTRAINT PK_PROJECT PRIMARY KEY (PROJECT_ID),
    UNIQUE(PROJECT_KEY, ORGANIZATION_ID)
)  ENGINE=INNODB;

-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Version`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS VERSION (
    VERSION_ID          INTEGER NOT NULL AUTO_INCREMENT,
    VERSION             VARCHAR(200) NULL,
    PROJECT_ID          INT NOT NULL,
    CONSTRAINT PK_VERSION PRIMARY KEY (VERSION_ID),
    UNIQUE(VERSION, PROJECT_ID)
)  ENGINE=INNODB;
ALTER TABLE VERSION ADD CONSTRAINT VERSION_FK_BY_PROJECT_ID FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (PROJECT_ID);
CREATE INDEX VERSION_IND_BY_PROJECT_ID USING HASH ON VERSION(PROJECT_ID);


-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Issue`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS ISSUE (
    ISSUE_ID            INTEGER NOT NULL AUTO_INCREMENT,
    PKEY                VARCHAR(100),
    PROJECT_ID          INT NOT NULL,
    SUMMARY             VARCHAR (255) NOT NULL,
    DESCRIPTION         TEXT NULL,
    ISSUE_TYPE          VARCHAR(100) NULL,
    PRIORITY            VARCHAR(100) NULL,
    OWNER               VARCHAR(100) NULL,
    STATUS              VARCHAR(100) NULL,
    ASSIGNEE            VARCHAR(100) NULL,
    VERSION_ID          INT NULL,
    CREATED_TIME        TIMESTAMP NULL,
    UPDATED_TIME        TIMESTAMP NULL,
    SEVERITY            VARCHAR(45) NULL,
    CONSTRAINT PK_ISSUE PRIMARY KEY (ISSUE_ID),
    ORGANIZATION_ID     INTEGER DEFAULT 0,
    UNIQUE (PKEY, ORGANIZATION_ID),
    UNIQUE (SUMMARY, ORGANIZATION_ID)
)  ENGINE=INNODB;
ALTER TABLE ISSUE ADD CONSTRAINT ISSUE_FK_BY_PROJECT_ID FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT (PROJECT_ID);
CREATE INDEX ISSUE_IND_BY_PROJECT_ID USING HASH ON ISSUE (PROJECT_ID);
ALTER TABLE ISSUE ADD CONSTRAINT ISSUE_FK_BY_VERSION_ID FOREIGN KEY (VERSION_ID) REFERENCES VERSION(VERSION_ID);
CREATE INDEX ISSUE_IND_BY_VERSION_ID USING HASH ON ISSUE (VERSION_ID);

-- -----------------------------------------------------
-- Table `issueTrackerDb`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS COMMENT (
    ID                  INTEGER NOT NULL AUTO_INCREMENT,
    DESCRIPTION 	TEXT NULL,
    CREATED_TIME        TIMESTAMP NULL,
    UPDATED_TIME        TIMESTAMP NULL,
    CREATOR             VARCHAR(100) NULL,
    ISSUE_ID            INT NOT NULL,
    ORGANIZATION_ID     INTEGER DEFAULT 0,
    CONSTRAINT PK_COMMENT PRIMARY KEY (ID)
)  ENGINE INNODB;
ALTER TABLE COMMENT ADD CONSTRAINT COMMENT_FK_BY_ISSUE_ID FOREIGN KEY (ISSUE_ID) REFERENCES ISSUE (ISSUE_ID);
CREATE INDEX COMMENT_IND_BY_ISSUE_ID USING HASH ON COMMENT(ISSUE_ID);
  
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
