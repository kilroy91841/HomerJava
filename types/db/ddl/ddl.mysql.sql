CREATE TABLE SPORTTYPE (
    type VARCHAR(7) PRIMARY KEY
);

CREATE TABLE PLAYERSTATUS (
    playerStatusCode VARCHAR(10) PRIMARY KEY,
    playerStatusName VARCHAR(20) NOT NULL
);

CREATE TABLE POSITION (
    positionId int PRIMARY KEY,
    positionName VARCHAR(20) NOT NULL,
    positionType VARCHAR(10) NOT NULL,
    positionCode VARCHAR(10) NOT NULL,
    FOREIGN KEY (positionType) REFERENCES SPORTTYPE (type)
);

CREATE TABLE TEAM (
    teamId INT PRIMARY KEY,
    teamName VARCHAR(50) NOT NULL,
    teamType VARCHAR(10) NOT NULL,
    teamCode VARCHAR(10) NOT NULL,
    FOREIGN KEY (teamType) REFERENCES SPORTTYPE (type)
);

CREATE TABLE PLAYER (
    playerId BIGINT AUTO_INCREMENT PRIMARY KEY,
    playerName VARCHAR(50) NOT NULL,
    firstName VARCHAR(25),
    lastName VARCHAR(25),
    nameLastFirst VARCHAR(50),
    primaryPositionId INT,
    mlbPlayerId BIGINT NULL UNIQUE,
    espnPlayerId BIGINT NULL UNIQUE,
    espnPlayerName VARCHAR(50),
    FOREIGN KEY (primaryPositionId) REFERENCES POSITION (positionId)
);

CREATE TABLE PLAYERTOTEAM (
    playerId BIGINT NOT NULL,
    gameDate DATE NOT NULL,
    fantasyTeamId INT,
    mlbTeamId INT,
    fantasyPlayerStatusCode VARCHAR(10),
    mlbPlayerStatusCode VARCHAR(10),
    fantasyPositionId INT,
    PRIMARY KEY (playerId, gameDate),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
    FOREIGN KEY (fantasyTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (mlbTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (fantasyPlayerStatusCode) REFERENCES PLAYERSTATUS (playerStatusCode),
    FOREIGN KEY (mlbPlayerStatusCode) REFERENCES PLAYERSTATUS (playerStatusCode),
    FOREIGN KEY (fantasyPositionId) REFERENCES POSITION (positionId)
);

CREATE TABLE PLAYERHISTORY (
    playerId BIGINT NOT NULL,
    season INT NOT NULL,
    salary INT NOT NULL DEFAULT 0,
    keeperSeason INT DEFAULT 0,
    draftTeamId INT,
    keeperTeamId INT,
    minorLeaguer BOOLEAN,
    rookieStatus BOOLEAN,
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
    owningTeamId INT NOT NULL,
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
    tradeId INT AUTO_INCREMENT PRIMARY KEY,
    proposingTeamId INT NOT NULL,
    proposedToTeamId INT NOT NULL,
    createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deadline TIMESTAMP NULL,
    tradeStatus VARCHAR(20) NOT NULL,
    FOREIGN KEY (proposingTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (proposingTeamId) REFERENCES TEAM (teamId)
);

CREATE TABLE TRADEASSET (
    tradeAssetId BIGINT AUTO_INCREMENT PRIMARY KEY,
    tradeId INT,
    teamId INT NOT NULL,
    playerId BIGINT,
    moneyId BIGINT,
    minorLeagueDraftPickId BIGINT,
    FOREIGN KEY (tradeId) REFERENCES TRADE (tradeId),
    FOREIGN KEY (teamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
    FOREIGN KEY (moneyId) REFERENCES MONEY (moneyId),
    FOREIGN KEY (minorLeagueDraftPickId) REFERENCES MINORLEAGUEDRAFTPICK (minorLeagueDraftPickId)
);

CREATE TABLE VULTURE (
	vultureId INT AUTO_INCREMENT PRIMARY KEY,
	vulturingTeamId INT NOT NULL,
	offendingTeamId INT NOT NULL,
	playerId BIGINT NOT NULL,
	droppingPlayerId BIGINT NULL,
	createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	deadline TIMESTAMP DEFAULT 0,
	vultureStatus VARCHAR(10) NOT NULL,
	FOREIGN KEY (vulturingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (offendingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
	FOREIGN KEY (droppingPlayerId) REFERENCES PLAYER (playerId)
);

CREATE TABLE FREEAGENTAUCTION (
	freeAgentAuctionId INT AUTO_INCREMENT PRIMARY KEY,
	requestingTeamId INT NOT NULL,
	playerId BIGINT NOT NULL,
	createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	modifiedDate TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
	deadline TIMESTAMP NULL,
	status VARCHAR(10) NOT NULL,
	FOREIGN KEY (requestingTeamId) REFERENCES TEAM (teamId),
	FOREIGN KEY (playerId) REFERENCES PLAYER (playerId)
);

CREATE TABLE MLBGAME (
    gameId BIGINT PRIMARY KEY,
    homeTeamId INT NOT NULL,
    awayTeamId INT NOT NULL,
    gameDate DATE,
    awayScore INT,
    homeScore INT,
    awayProbablePitcherId BIGINT,
    homeProbablePitcherId BIGINT,
    gameTime TIMESTAMP(3),
    status VARCHAR(20),
    inning VARCHAR(20),
    inningState VARCHAR(20),
    gamedayUrl VARCHAR(100),
    amPm VARCHAR(10),
    FOREIGN KEY (homeTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (awayTeamId) REFERENCES TEAM (teamId),
    FOREIGN KEY (homeProbablePitcherId) REFERENCES PLAYER (mlbPlayerId),
    FOREIGN KEY (awayProbablePitcherId) REFERENCES PLAYER (mlbPlayerId)
);

CREATE TABLE MLBSTATS (
    statsId BIGINT AUTO_INCREMENT PRIMARY KEY,
    playerId BIGINT NOT NULL,
    mlbPlayerId BIGINT NOT NULL,
    gameId BIGINT NOT NULL,
    ab INT,
    ao INT,
    avg DOUBLE,
    bb INT,
    cs INT,
    d INT,
    gameDate DATE,
    gameType VARCHAR(5),
    go INT,
    goAo DOUBLE,
    h INT,
    h2b INT,
    h3b INT,
    hbp INT,
    homeAway VARCHAR(2),
    hr INT,
    ibb INT,
    lob INT,
    obp DOUBLE,
    opp VARCHAR(5),
    oppScore INT,
    oppTeamDisplayFull VARCHAR(30),
    oppTeamDisplayShort VARCHAR(15),
    oppTeamId INT,
    ops DOUBLE,
    r INT,
    rbi INT,
    sac INT,
    sb INT,
    sf INT,
    slg DOUBLE,
    so INT,
    t INT,
    tb INT,
    w INT,
    sv INT,
    er INT,
    hb INT,
    ip DOUBLE,
    teamResult VARCHAR(10),
    teamScore INT,
    UNIQUE KEY (playerId, gameId),
    FOREIGN KEY (playerId) REFERENCES PLAYER (playerId),
    FOREIGN KEY (mlbPlayerId) REFERENCES PLAYER (mlbPlayerId),
    FOREIGN KEY (gameId) REFERENCES MLBGAME (gameId)
);

CREATE TABLE ESPNTRANSACTION (
    transactionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    playerName VARCHAR(50) NOT NULL,
    teamId INT NOT NULL,
    move VARCHAR(10) NOT NULL,
    time TIMESTAMP NOT NULL,
    nodeText VARCHAR(150) NOT NULL,
    UNIQUE (nodeText, time)
);