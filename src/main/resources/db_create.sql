DROP TABLE IF EXISTS USUARIO;
DROP TABLE IF EXISTS ALBUM;
DROP TABLE IF EXISTS ARCHIVO;
DROP TABLE IF EXISTS ALBUM_SHARE_INFORMATION;
DROP TABLE IF EXISTS FILE_SHARE_INFORMATION;
DROP TABLE IF EXISTS ALBUM_TAG;
DROP TABLE IF EXISTS FILE_TAG;
DROP TABLE IF EXISTS COMMENT;
DROP TABLE IF EXISTS LIKE_DISLIKE;
DROP TABLE IF EXISTS VOTED;

CREATE TABLE USUARIO
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        EMAIL VARCHAR(100) NOT NULL,
        PASSWORD VARCHAR(100) NOT NULL,
        CONSTRAINT PK_USUARIO PRIMARY KEY (ID),
        CONSTRAINT USUARIO_UNIQUE_EMAIL UNIQUE (EMAIL)
    );

CREATE TABLE LIKE_DISLIKE
	(
		ID INTEGER NOT NULL AUTO_INCREMENT,
		LIKES INTEGER,
		DISLIKES INTEGER,
		VERSION BIGINT,
		CONSTRAINT PK_LIKE_DISLIKE PRIMARY KEY (ID)
	);

CREATE TABLE VOTED
	(
		ID INTEGER NOT NULL AUTO_INCREMENT,
		LIKE_DISLIKE_ID INTEGER NOT NULL,
		USER_ID INTEGER NOT NULL,
		CONSTRAINT PK_VOTED PRIMARY KEY (ID),
		CONSTRAINT FK_VOTED_LIKE_DISLIKE FOREIGN KEY (LIKE_DISLIKE_ID) REFERENCES LIKE_DISLIKE (ID) ON DELETE CASCADE,
		CONSTRAINT FK_VOTED_USER_ID FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE
	);    

CREATE TABLE ALBUM
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        NAME VARCHAR(100) NOT NULL,
        USER_ID INTEGER NOT NULL,
        PRIVACY_LEVEL VARCHAR(10) CHECK PRIVACY_LEVEL IN ('PUBLIC', 'PRIVATE'), 
        LIKE_DISLIKE_ID INTEGER NOT NULL,
        CONSTRAINT PK_ALBUM PRIMARY KEY (ID),
        CONSTRAINT FK_ALBUM FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE,
        CONSTRAINT FK_LIKE_DISLIKE_ALBUM FOREIGN KEY (LIKE_DISLIKE_ID) REFERENCES LIKE_DISLIKE (ID) ON DELETE CASCADE,
        CONSTRAINT ALBUM_UNIQUE_NAME UNIQUE (NAME, USER_ID)
    );

CREATE TABLE ARCHIVO
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        NAME VARCHAR(100) NOT NULL,
        FILE BLOB(2147483647) NOT NULL,
        FILE_SMALL BLOB(2147483647) NOT NULL,
        ALBUM_ID INTEGER NOT NULL,
        PRIVACY_LEVEL VARCHAR(20) CHECK PRIVACY_LEVEL IN ('PUBLIC', 'PRIVATE', 'INHERIT_FROM_ALBUM'),
        LIKE_DISLIKE_ID INTEGER NOT NULL,
        CONSTRAINT PK_ARCHIVO PRIMARY KEY (ID),
        CONSTRAINT FK_LIKE_DISLIKE_ARCHIVO FOREIGN KEY (LIKE_DISLIKE_ID) REFERENCES LIKE_DISLIKE (ID) ON DELETE CASCADE,
        CONSTRAINT FK_USUARIO FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE
    );
    
CREATE TABLE COMMENT
	(
		ID INTEGER NOT NULL AUTO_INCREMENT,
		LIKE_DISLIKE_ID INTEGER NOT NULL,
		USER_ID INTEGER NOT NULL,
		DATE TIMESTAMP NOT NULL,
		TEXT VARCHAR(1000) NOT NULL,
		ALBUM_ID INTEGER,
		FILE_ID INTEGER,
		CONSTRAINT PK_COMMENT PRIMARY KEY (ID),
		CONSTRAINT FK_COMMENT_LIKE_DISLIKE_ID FOREIGN KEY (LIKE_DISLIKE_ID) REFERENCES LIKE_DISLIKE (ID) ON DELETE CASCADE,
		CONSTRAINT FK_COMMENT_USER_ID FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE,
		CONSTRAINT FK_COMMENT_ALBUM_ID FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE,
		CONSTRAINT FK_COMMENT_FILE_ID FOREIGN KEY (FILE_ID) REFERENCES ARCHIVO (ID) ON DELETE CASCADE
	);

CREATE TABLE ALBUM_SHARE_INFORMATION
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        ALBUM_ID INTEGER NOT NULL,
        USER_ID INTEGER NOT NULL,
        CONSTRAINT PK_ALBUM_SHARE_INFORMATION PRIMARY KEY (ID),
        CONSTRAINT FK_ALBUM_SHARE_ALBUM FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE,
        CONSTRAINT FK_ALBUM_SHARE_USUARIO FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE
    );
	
CREATE TABLE FILE_SHARE_INFORMATION
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        FILE_ID INTEGER NOT NULL,
        USER_ID INTEGER NOT NULL,
        CONSTRAINT PK_FILE_SHARE_INFORMATION PRIMARY KEY (ID),
        CONSTRAINT FK_FILE_SHARE_FILE FOREIGN KEY (FILE_ID) REFERENCES ARCHIVO (ID) ON DELETE CASCADE,
        CONSTRAINT FK_FILE_SHARE_USUARIO FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE
    );

CREATE TABLE ALBUM_TAG
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        ALBUM_ID INTEGER NOT NULL,
        TAG VARCHAR(100) NOT NULL, 
        CONSTRAINT PK_ALBUM_TAG PRIMARY KEY (ID),
        CONSTRAINT FK_ALBUM_TAG_ALBUM FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE
    );

CREATE TABLE FILE_TAG
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        FILE_ID INTEGER NOT NULL,
        TAG VARCHAR(100) NOT NULL, 
        CONSTRAINT PK_FILE_TAG PRIMARY KEY (ID),
        CONSTRAINT FK_FILE_TAG_FILE FOREIGN KEY (FILE_ID) REFERENCES ARCHIVO (ID) ON DELETE CASCADE
    );
    
