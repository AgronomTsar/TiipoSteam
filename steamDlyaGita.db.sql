BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Game" (
	"gameId"	Integer,
	"name"	Text NOT NULL,
	"description"	Text NOT NULL,
	"price"	Text NOT NULL,
	PRIMARY KEY("gameId")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"fname"	text NOT NULL,
	"lname"	text NOT NULL,
	"nickname"	text NOT NULL,
	"birthday"	text NOT NULL,
	"password"	text NOT NULL,
	"email"	text NOT NULL,
	"role"	text NOT NULL,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "User_Game" (
	"UserGameId"	INTEGER,
	"userId"	INTEGER,
	"gameId"	INTEGER,
	FOREIGN KEY("userId") REFERENCES "User"("id"),
	FOREIGN KEY("gameId") REFERENCES "Game"("gameId"),
	PRIMARY KEY("UserGameId")
);
INSERT INTO "Game" VALUES (1,'spaceRangers','horosho ','1000');
INSERT INTO "Game" VALUES (2,'foxhole','toshe horosho','1500');
INSERT INTO "Game" VALUES (3,'hoi4','10 divisiy iz 10','5000');
INSERT INTO "Game" VALUES (4,'civilization6','vpered legioni','5500');
INSERT INTO "Game" VALUES (25,'tf2','yeeeehhhhaaaaa','5000');
INSERT INTO "User" VALUES (1,'Sanya','Ganjubas','Sanya',X'aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007cf0b0a78','$2a$10$SvPa0vPFUCyskf4Y1m1qcOJnllo/syncTNElYLq9moq2HlCu.h.Fy','Senya@mail.ru','user');
INSERT INTO "User" VALUES (2,'sergey','krivosheev','administrator',X'aced00057372000d6a6176612e74696d652e536572955d84ba1b2248b20c00007870770703000007cf0a0c78','$2a$10$ZoFJ7wXj7LzmD1F4xLB6ZO1pQTXWPLplf0i.X/DoOauyrU.SrczVW','SergeyKrivosheev@mail.ru','Admin');
INSERT INTO "User_Game" VALUES (1,1,1);
INSERT INTO "User_Game" VALUES (2,1,2);
INSERT INTO "User_Game" VALUES (3,1,3);
INSERT INTO "User_Game" VALUES (4,2,4);
INSERT INTO "User_Game" VALUES (10,1,4);
COMMIT;
