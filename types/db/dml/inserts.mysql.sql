USE HOMERATTHEBAT;

delete from PLAYERSTATUS;

insert into PLAYERSTATUS (playerStatusId, playerStatusName, playerStatusCode)
select 1,'ACTIVE','A'
union all
select 2,'DL','DL'
union all
select 3,'BENCH','B';

delete from POSITION;

insert into POSITION (positionId, positionName, positionType, positionCode)
select 1, "STARTINGPITCHER", "MLB", "P"
union all
select 2, "CATCHER", "MLB", "C"
union all
select 3, "FIRSTBASE", "MLB", "1B"
union all
select 4, "SECONDBASE", "MLB", "2B"
union all
select 5, "THIRDBASE", "MLB", "3B"
union all
select 6, "SHORTSTOP", "MLB", "SS"
union all
select 7, "LEFTFIELD", "MLB", "LF"
union all
select 8, "CENTERFIELD", "MLB", "CF"
union all
select 9, "RIGHTFIELD", "MLB", "RF"
union all
select 10, "DESIGNATEDHITTER", "MLB", "DH"
union all
select 11, "RELIEFPITCHER", "MLB", "RP"
union all
select 101, "PITCHER", "FANTASY", "P"
union all
select 102, "CATCHER", "FANTASY", "C"
union all
select 103, "FIRSTBASE", "FANTASY", "1B"
union all
select 104, "SECONDBASE", "FANTASY", "2B"
union all
select 105, "THIRDBASE", "FANTASY", "3B"
union all
select 106, "SHORTSTOP", "FANTASY", "SS"
union all
select 107, "OUTFIELD", "FANTASY", "OF"
union all
select 108, "MIDDLEINFIELD", "FANTASY", "MI"
union all
select 109, "CORNERINFIELD", "FANTASY", "CI"
union all
select 110, "UTILITY", "FANTASY", "U";

delete from TEAM;

insert into TEAM (teamId, teamName, teamType, teamCode)
select 1, "Mark Loretta\'s Scars", "FANTASY", "MLS"
union all
select 2, "BSnaxx Cracker Jaxx", "FANTASY", "SNAXX"
union all
select 108, "Los Angeles Angels", "MLB", "LAA"
union all
select 109, "Arizona Diamondbacks", "MLB", "ARI"
union all
select 144, "Atlanta Braves", "MLB", "ATL"
union all
select 110, "Baltimore Orioles", "MLB", "BAL"
union all
select 111, "Boston Red Sox", "MLB", "BOS"
union all
select 112, "Chicago Cubs", "MLB", "CHC"
union all
select 113, "Cincinnati Reds", "MLB", "CIN"
union all
select 114, "Cleveland Indians", "MLB", "CLE"
union all
select 115, "Colorado Rockies", "MLB", "COL"
union all
select 145, "Chicago White Sox", "MLB", "CWS"
union all
select 116, "Detroit Tigers", "MLB", "DET"
union all
select 117, "Houston Astros", "MLB", "HOU"
union all
select 118, "Kansas City Royals", "MLB", "KC"
union all
select 119, "Los Angeles Dodgers", "MLB", "LAD"
union all
select 146, "Miami Marlins", "MLB", "MIA"
union all
select 158, "Milwaukee Brewers", "MLB", "MIL"
union all
select 142, "Minnesota Twins", "MLB", "MIN"
union all
select 121, "New York Mets", "MLB", "NYM"
union all
select 147, "New York Yankees", "MLB", "NYY"
union all
select 133, "Oakland Athletics", "MLB", "OAK"
union all
select 143, "Philadelphia Phillies", "MLB", "PHI"
union all
select 134, "Pittsburgh Pirates", "MLB", "PIT"
union all
select 135, "San Diego Padres", "MLB", "SD"
union all
select 136, "Seattle Mariners", "MLB", "SEA"
union all
select 137, "San Francisco Giants", "MLB", "SF"
union all
select 138, "St. Louis Cardinals", "MLB", "STL"
union all
select 139, "Tampa Bay Rays", "MLB", "TB"
union all
select 140, "Texas Rangers", "MLB", "TEX"
union all
select 141, "Toronto Blue Jays", "MLB", "TOR"
union all
select 120, "Washington Nationals", "MLB", "WSH"
;

delete from PLAYER;

insert into PLAYER (playerName, primaryPositionId, mlbPlayerId)
select 'Mike Trout', 8, 545361
union all
select 'Miguel Cabrera', 3, null
union all
select 'Andrew McCutchen', 8, null
union all
select 'Brian McCann', 2, null
union all
select 'Wilin Rosario', 2, null
union all
select 'Chris Carter', 3, null
union all
select 'Neil Walker', 4, null
union all
select 'Aramis Ramirez', 5, null
union all
select 'Erick Aybar', 6, null
union all
select 'Wilmer Flores', 6, null
union all
select 'Adrian Gonzalez', 3, null
union all
select 'Charlie Blackmon', 8, null
union all
select 'Lorenzo Cain', 8, null
union all
select 'Brett Gardner', 7, null
union all
select 'Carlos Gomez', 8, null
union all
select 'Gregory Polanco', 9, null
union all
select 'David Ortiz', 10, null
union all
select 'Josh Collmenter', 1, null
union all
select 'Miguel Gonzalez', 1, null
union all
select 'Felix Hernandez', 1, null
union all
select 'Craig Kimbrel', 11, null
union all
select 'Collin McHugh', 1, null
union all
select 'Chris Sale', 1, null
union all
select 'Marcus Stroman', 1, null
union all
select 'Jacob deGrom', 1, null
union all
select 'Garrett Richards', 1, null;

delete from PLAYERTOTEAM;

insert into PLAYERTOTEAM (playerId, gameDate, fantasyTeamId, mlbTeamId, fantasyPlayerStatusId, mlbPlayerStatusId, fantasyPositionId)
select 1, '2015-01-01', 1, 108, 1, 1, 107
union all
select 2, '2015-01-01', 1, 134, 1, 1, 103
union all
select 3, '2015-01-01', 2, 116, 1, 1, 107
union all
select 1, '2015-01-02', 1, 108, 1, 1, 107
union all
select 4, '2015-01-03', 1, 147, 1, 1, 102
union all
select 5, '2015-01-03', 1, 147, 1, 1, 102
union all
select 6, '2015-01-03', 1, 147, 1, 1, 103
union all
select 7, '2015-01-03', 1, 147, 1, 1, 104
union all
select 8, '2015-01-03', 1, 147, 1, 1, 105
union all
select 9, '2015-01-03', 1, 147, 1, 1, 106
union all
select 10, '2015-01-03', 1, 147, 1, 1, 108
union all
select 11, '2015-01-03', 1, 147, 1, 1, 109
union all
select 12, '2015-01-03', 1, 147, 1, 1, 107
union all
select 13, '2015-01-03', 1, 147, 1, 1, 107
union all
select 14, '2015-01-03', 1, 147, 1, 1, 107
union all
select 15, '2015-01-03', 1, 147, 1, 1, 107
union all
select 16, '2015-01-03', 1, 147, 1, 1, 107
union all
select 17, '2015-01-03', 1, 147, 1, 1, 110
union all
select 18, '2015-01-03', 1, 147, 1, 1, 101
union all
select 19, '2015-01-03', 1, 147, 1, 1, 101
union all
select 20, '2015-01-03', 1, 147, 1, 1, 101
union all
select 21, '2015-01-03', 1, 147, 1, 1, 101
union all
select 22, '2015-01-03', 1, 147, 1, 1, 101
union all
select 23, '2015-01-03', 1, 147, 1, 1, 101
union all
select 24, '2015-01-03', 1, 147, 1, 1, 101
union all
select 25, '2015-01-03', 1, 147, 1, 1, 101
union all
select 26, '2015-01-03', 1, 147, 1, 1, 101;

delete from PLAYERHISTORY;

insert into PLAYERHISTORY (playerId, season, salary, keeperSeason, minorLeaguer, draftTeamId, keeperTeamId)
select 1, 2012, 0, 0, 0, 1, null
union all
select 1, 2013, 3, 1, 0, null, 1
union all
select 1, 2014, 6, 2, 0, null, 1
union all
select 1, 2015, 9, 3, 0, null, 1;

delete from MONEY;

insert into MONEY (teamId, season, moneyType, amount)
select 1, 2015, "MAJORLEAGUEDRAFT", 260
union all
select 1, 2016, "MAJORLEAGUEDRAFT", 263
union all
select 1, 2015, "FREEAGENTAUCTION", 100;

delete from MINORLEAGUEDRAFTPICK;

insert into MINORLEAGUEDRAFTPICK (originalTeamId, season, round, owningTeamId, overall, playerId, deadline, skipped)
select 1, 2014, 1, 1, 7, 1, '2014-01-01 12:00:00', false
union all
select 1, 2014, 2, 2, 9, 1, '2014-01-02 12:00:00', false
union all
select 1, 2015, 1, 1, null, null, null, false
union all
select 2, 2015, 1, 2, null, null, null, false
union all
select 1, 2015, 2, 1, null, null, null, false;

delete from TRADE;

insert into TRADE (proposingTeamId, proposedToTeamId, createdDate, deadline, tradeStatus)
select 1, 2, '2015-01-31 13:30:00', '2015-2-6 13:30:00', 'PROPOSED';

delete from TRADEASSET;

insert into TRADEASSET (tradeId, teamId, assetId, assetType)
select 1, 1, 1, "PLAYER"
union all
select 1, 1, 2, "PLAYER"
union all
select 1, 2, 4, "MINORLEAGUEDRAFTPICK";

delete from VULTURE;

insert into VULTURE (vulturingTeamId, offendingTeamId, playerId, deadline, vultureStatus)
select 2, 1, 1, '2015-02-03 16:00:00', "ACTIVE";

delete from FREEAGENTAUCTION;

insert into FREEAGENTAUCTION (requestingTeamId, playerId, status, deadline, createdDate)
select 1, 2, 'ACTIVE', '2015-02-02 00:00:00', '2015-02-01 00:00:00';