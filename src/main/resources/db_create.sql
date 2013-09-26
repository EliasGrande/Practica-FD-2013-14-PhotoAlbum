CREATE TABLE IF NOT EXISTS USUARIO
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        EMAIL VARCHAR(100) NOT NULL,
        PASSWORD VARCHAR(100) NOT NULL,
        CONSTRAINT PK_USUARIO PRIMARY KEY (ID),
        CONSTRAINT USUARIO_UNIQUE_EMAIL UNIQUE (EMAIL)
    );
	

CREATE TABLE IF NOT EXISTS ALBUM
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        NAME VARCHAR(100) NOT NULL,
        USER_ID INTEGER NOT NULL,
        CONSTRAINT PK_ALBUM PRIMARY KEY (ID),
        CONSTRAINT FK_ALBUM FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE,
        CONSTRAINT ALBUM_UNIQUE_NAME UNIQUE (NAME, USER_ID)
    );
	
CREATE TABLE IF NOT EXISTS ARCHIVO
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        NAME VARCHAR(100) NOT NULL,
        FILE BLOB(2147483647) NOT NULL,
        FILE_SMALL BLOB(2147483647) NOT NULL,
        ALBUM_ID INTEGER NOT NULL,
        CONSTRAINT PK_ARCHIVO PRIMARY KEY (ID),
        CONSTRAINT FK_USUARIO FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE
    );
	
CREATE TABLE IF NOT EXISTS SHARE_INFORMATION
    (
        ID INTEGER NOT NULL AUTO_INCREMENT,
        ALBUM_ID INTEGER NOT NULL,
        USER_ID INTEGER NOT NULL,
        CONSTRAINT PK_SHARE_INFORMATION PRIMARY KEY (ID),
        CONSTRAINT FK_SHARE_ALBUM FOREIGN KEY (ALBUM_ID) REFERENCES ALBUM (ID) ON DELETE CASCADE,
        CONSTRAINT FK_SHARE_USUARIO FOREIGN KEY (USER_ID) REFERENCES USUARIO (ID) ON DELETE CASCADE
    );
