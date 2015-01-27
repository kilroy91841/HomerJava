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
select 1, "PITCHER", "MLB", "P"
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
select 11, "STARTINGPITCHER", "FANTASY", "SP"
union all
select 12, "RELIEFPITCHER", "FANTASY", "RP";

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

insert into PLAYER (playerName, positionId)
select 'Mike Trout', 8
union all
select 'Miguel Cabrera', 3
union all
select 'Andrew McCutchen', 8;

delete from PLAYERTOTEAM;

insert into PLAYERTOTEAM (playerId, gameDate, fantasyTeamId, mlbTeamId, fantasyPlayerStatusId, mlbPlayerStatusId)
select 1, '2015-01-01', 1, 108, 1, 1
union all
select 2, '2015-01-01', 1, 134, 1, 1
union all
select 3, '2015-01-01', 2, 116, 1, 1;
