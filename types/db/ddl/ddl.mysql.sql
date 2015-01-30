DROP DATABASE IF EXISTS HOMERATTHEBAT;
CREATE DATABASE HOMERATTHEBAT;

USE HOMERATTHEBAT;

CREATE TABLE PLAYERSTATUS (
    playerStatusId int PRIMARY KEY,
    playerStatusName VARCHAR(20) NOT NULL,
    playerStatusCode VARCHAR(5) NOT NULL
);

CREATE TABLE POSITION (
    positionId int PRIMARY KEY,
    positionName VARCHAR(20) NOT NULL,
    positionType VARCHAR(10) NOT NULL,
    positionCode VARCHAR(10) NOT NULL
);

CREATE TABLE TEAM (
    teamId INT PRIMARY KEY,
    teamName VARCHAR(50) NOT NULL,
    teamType VARCHAR(10) NOT NULL,
    teamCode VARCHAR(10) NOT NULL
);

CREATE TABLE PLAYER (
    playerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    playerName VARCHAR(50) NOT NULL,
    primaryPositionId INT,
    FOREIGN KEY (primaryPositionId) REFERENCES POSITION (positionId)
);

CREATE TABLE PLAYERTOTEAM (
    playerId BIGINT,
    gameDate DATE NOT NULL,
    fantasyTeamId INT,
    mlbTeamId INT,
    fantasyPlayerStatusId INT,
    mlbPlayerStatusId INT,
    fantasyPositionId INT,
    PRIMARY KEY (playerId, gameDate),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
    FOREIGN KEY (fantasyTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (mlbTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (fantasyPlayerStatusId) REFERENCES PLAYERSTATUS (playerStatusId),
    FOREIGN KEY (mlbPlayerStatusId) REFERENCES PLAYERSTATUS (playerStatusId),
    FOREIGN KEY (fantasyPositionId) REFERENCES POSITION (positionId)
);

CREATE TABLE PLAYERHISTORY (
    playerId BIGINT NOT NULL,
    season INT NOT NULL,
    salary INT NOT NULL,
    keeperSeason INT DEFAULT 0,
    minorLeaguer BOOLEAN,
    draftTeamId INT,
    keeperTeamId INT,
    PRIMARY KEY (playerId, season),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
    FOREIGN KEY (draftTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (keeperTeamId) REFERENCES TEAM (teamId)
);