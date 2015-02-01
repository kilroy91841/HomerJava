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
    mlbPlayerId BIGINT NULL,
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

CREATE TABLE MONEY (
    moneyId BIGINT AUTO_INCREMENT PRIMARY KEY,
    teamId INT NOT NULL,
    season INT NOT NULL,
    moneyType VARCHAR(20) NOT NULL,
    amount INT NOT NULL,
    UNIQUE KEY (teamId, season, moneyType),
    FOREIGN KEY (teamId) REFERENCES TEAM (teamId)
);

CREATE TABLE MINORLEAGUEDRAFTPICK (
    minorLeagueDraftPickId BIGINT AUTO_INCREMENT PRIMARY KEY,
    originalTeamId INT NOT NULL,
    season INT NOT NULL,
    round INT NOT NULL,
    owningTeamId INT,
    overall INT,
    playerId BIGINT,
    deadline TIMESTAMP NULL,
    skipped BOOLEAN,
    UNIQUE KEY (originalTeamId, season, round),
    FOREIGN KEY (originalTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (owningTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId)
);

CREATE TABLE TRADE (
    tradeId INT,
    proposingTeamId INT NOT NULL,
    proposedToTeamId INT NOT NULL,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deadline TIMESTAMP NULL,
    tradeStatus VARCHAR(20) NOT NULL,
    PRIMARY KEY (tradeId),
    FOREIGN KEY (proposingTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (proposingTeamId) REFERENCES TEAM (teamId)
);

CREATE TABLE TRADEASSET (
    tradeId INT,
    teamId INT NOT NULL,
    assetId BIGINT,
    assetType VARCHAR(30),
    PRIMARY KEY (tradeId, assetId, assetType),
    FOREIGN KEY (teamId) REFERENCES TEAM (teamId)
);

CREATE TABLE VULTURE (
	vultureId INT AUTO_INCREMENT PRIMARY KEY,
	vulturingTeamId INT NOT NULL,
	offendingTeamId INT NOT NULL,
	playerId BIGINT NOT NULL,
	createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	deadline TIMESTAMP DEFAULT 0,
	vultureStatus VARCHAR(10) NOT NULL,
	FOREIGN KEY (vulturingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (offendingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (playerId) REFERENCES PLAYER (playerId)
);

CREATE TABLE FREEAGENTAUCTION (
	freeAgentAuctionId INT AUTO_INCREMENT PRIMARY KEY,
	requestingTeamId INT NOT NULL,
	playerId BIGINT NOT NULL,
	createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modifiedDate TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
	deadline TIMESTAMP DEFAULT 0,
	status VARCHAR(10) NOT NULL,
	FOREIGN KEY (requestingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (playerId) REFERENCES PLAYER (playerId)
);