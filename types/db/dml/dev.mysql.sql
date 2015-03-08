delete from SPORTTYPE;

insert into SPORTTYPE (type)
select 'MLB'
union all
select 'FANTASY';

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
select 108, "MIDDLEINFIELD", "FANTASY", "2B/SS"
union all
select 109, "CORNERINFIELD", "FANTASY", "1B/3B"
union all
select 110, "UTILITY", "FANTASY", "UTIL"
union all
select 111, "BENCH", "FANTASY", "Bench"
union all
select 112, "DISABLEDLIST", "FANTASY", "DL";

delete from TEAM;

insert into TEAM (teamId, teamName, teamType, teamCode)
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

insert into TEAM (teamName, teamCode, teamId, teamType)
select "Mark Loretta's Scars","MLS",1,"FANTASY"
union all
select "Ron Paul Revolution","GRAN",7,"FANTASY"
union all
select "Kershawshank Redemption","SHAW",12,"FANTASY"
union all
select "McGlade PlugIns","BMAC",6,"FANTASY"
union all
select "Bull Doziers","DOZE",3,"FANTASY"
union all
select "Crown Heights Riots","SIDO",4,"FANTASY"
union all
select "The Baghdad Daddy Bags","DBAG",9,"FANTASY"
union all
select "BSnaxx CrackerJaxx","SNXX",2,"FANTASY"
union all
select "Big Wheelie Style","HIV+",10,"FANTASY"
union all
select "Que? Manbearpuig","TACO",11,"FANTASY"
union all
select "Brooklyn No Sox","MAD",5,"FANTASY"
union all
select "Las Vegas Isotopes","LAZ",8,"FANTASY"
union all
select "Free Agent","FA",0,"FANTASY";

insert into PLAYERSTATUS (playerStatusCode, playerStatusName)
select 'A', 'ACTIVE'
union all
select 'I', 'INACTIVE'
union all
select 'DL', 'DISABLED LIST'
union all
select 'B', 'BENCH'
union all
select 'MIN', 'MINOR LEAGUES'
union all
select 'SUSP', 'SUSPENDED'
union all
select 'RST', 'RESTRICTED'
union all
select 'UNK', 'UNKNOWN'
union all
select 'FA', 'FREEAGENT';

insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("RUNS", true, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("RBI", true, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("HR", true, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("SB", true, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("OBP", true, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("WINS", false, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("SV", false, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("K", false, true);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("ERA", false, false);
insert into STANDINGSCATEGORY (standingsCategoryName, isHitting, isDescending)
values ("WHIP", false, false);

insert into money (teamId, season, moneyType, amount)
select 1, 2015, 'MAJORLEAGUEDRAFT', 257
union all
select 7, 2015, 'MAJORLEAGUEDRAFT', 260
union all
select 12, 2015, 'MAJORLEAGUEDRAFT', 337
union all
select 6, 2015, 'MAJORLEAGUEDRAFT', 186
union all
select 3, 2015, 'MAJORLEAGUEDRAFT', 265
union all
select 4, 2015, 'MAJORLEAGUEDRAFT', 267
union all
select 9, 2015, 'MAJORLEAGUEDRAFT', 260
union all
select 2, 2015, 'MAJORLEAGUEDRAFT', 332
union all
select 10, 2015, 'MAJORLEAGUEDRAFT', 264
union all
select 11, 2015, 'MAJORLEAGUEDRAFT', 178
union all
select 5, 2015, 'MAJORLEAGUEDRAFT', 287
union all
select 8, 2015, 'MAJORLEAGUEDRAFT', 264;