USE `code_quality_index`;

DROP TABLE IF EXISTS `indicators`;

CREATE TABLE `indicators` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `category` enum('critical','major','minor','info','blocker') NOT NULL,
  `threshold_25` double DEFAULT '0',
  `threshold_50` double DEFAULT '0',
  `threshold_75` double DEFAULT '0',
  `threshold_90` double DEFAULT '0',
  `threshold_100` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

insert  into `indicators`(`id`,`name`,`category`) values 
(1,'Duplicated Code','blocker'),
(2,'Magic Numbers','blocker'),
(3,'Data Encapsulation','blocker'),
(4,'Overbooked File','critical'),
(5,'Insufficient Tests','critical'),
(6,'Informal Documentation','critical'),
(7,'To High Coupling','critical'),
(8,'God Construct','critical'),
(9,'Wrong Naming','critical'),
(10,'To Complex','major'),
(11,'Confusion Danger','major'),
(12,'Wrong Formatting','major'),
(13,'Object Placebo','minor'),
(14,'Wrong Comparison','minor'),
(15,'Risk Code','minor'),
(16,'Wrong Imports','minor'),
(17,'Wrong Declaration','minor'),
(18,'Dead Code','minor'),
(19,'Unfinished Code','info');

DROP TABLE IF EXISTS `projects`;

CREATE TABLE `projects` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `year` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rules`;

CREATE TABLE `rules` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `sonar_key` varchar(50) NOT NULL,
  `rule_text` varchar(256) DEFAULT NULL,
  `indicator` int(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contained_in_indicator` (`indicator`),
  CONSTRAINT `contained_in_indicator` FOREIGN KEY (`indicator`) REFERENCES `indicators` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

insert  into `rules`(`id`,`sonar_key`,`rule_text`,`indicator`) values 
(1,'common-java:DuplicatedBlocks','Source files should not have any duplicated blocks',1),
(2,'squid:S109','Magic numbers should not be used',2),
(3,'squid:ClassVariableVisibilityCheck','Class variable fields should not have public accessibility',3),
(4,'squid:S1996','Files should contain only one top-level class or interface each',4),
(5,'common-java:InsufficientLineCoverage','Lines should have sufficient coverage by unit tests',5),
(6,'squid:UndocumentedApi','Public types, methods and fields (API) should be documented with Javadoc',6),
(7,'squid:S1200','Classes should not be coupled to too many other classes (Single Responsibility Principle)',7),
(8,'squid:CycleBetweenPackages','Cycles between packages should be removed',7),
(9,'squid:S1448','Classes should not have too many methods',8),
(10,'squid:S00104','Files should not have too many lines',8),
(11,'squid:S138','Methods should not have too many lines',8),
(12,'squid:S2972','Inner classes should not have too many lines',8),
(13,'squid:S00118','Abstract class names should comply with a naming convention',9),
(14,'squid:S00101','Class names should comply with a naming convention',9),
(15,'squid:S00115','Constant names should comply with a naming convention',9),
(16,'squid:S00116','Field names should comply with a naming convention',9),
(17,'squid:S00114','Interface names should comply with a naming convention',9),
(18,'squid:S00117','Local variable and method parameter names should comply with a naming convention',9),
(19,'squid:S00100','Method names should comply with a naming convention',9),
(20,'squid:S00120','Package names should comply with a naming convention',9),
(21,'squid:S3008','Static non-final field names should comply with a naming convention',9),
(22,'squid:S00119','Type parameter names should comply with a naming convention',9),
(23,'squid:S00107','Methods should not have too many parameters',10),
(24,'squid:S134','Control flow statements \"if\", \"for\", \"while\", \"switch\" and \"try\" should not be nested too deeply',10),
(25,'squid:MethodCyclomaticComplexity','Methods should not be too complex',10),
(26,'squid:ClassCyclomaticComplexity','Classes should not be too complex',10),
(27,'squid:S2387','Child class fields should not shadow parent class fields',11),
(28,'squid:S2176','Class names should not shadow interfaces or superclasses',11),
(29,'squid:HiddenFieldCheck','Local variables should not shadow class fields',11),
(30,'squid:S1845','Methods and field names should not be the same or differ only by capitalization',11),
(31,'squid:S818','Literal suffixes should be upper case',11),
(32,'squid:RightCurlyBraceStartLineCheck','A close curly brace should be located at the beginning of a line',12),
(33,'squid:S00121','Control structures should use curly braces',12),
(34,'squid:S00103','Lines should not be too long',12),
(35,'squid:IndentationCheck','Source code should be indented consistently',12),
(36,'squid:S00122','Statements should be on separate lines',12),
(37,'squid:S00105','Tabulation characters should not be used',12),
(38,'squid:LeftCurlyBraceEndLineCheck','An open curly brace should be located at the end of a line',12),
(39,'squid:S2209','\"static\" members should be accessed statically',13),
(40,'squid:S1698','Objects should be compared with \"equals()\"',14),
(41,'squid:SwitchLastCaseIsDefaultCheck','\"switch\" statements should end with \"default\" clauses',15),
(42,'squid:S128','Switch cases should end with an unconditional \"break\" statement',15),
(43,'squid:UselessImportCheck','Useless imports should be removed',16),
(44,'squid:S2208','Wildcard imports should not be used',16),
(45,'squid:S1659','Multiple variables should not be declared on the same line',17),
(46,'squid:S1941','Variables should not be declared before they are relevant',17),
(47,'squid:S2039','Member variable visibility should be specified',17),
(48,'squid:S1068','Unused \"private\" fields should be removed',18),
(49,'squid:UnusedPrivateMethod','Unused \"private\" methods should be removed',18),
(50,'squid:UnusedProtectedMethod','Unused protected methods should be removed',18),
(51,'squid:S1065','Unused labels should be removed',18),
(52,'squid:S1481','Unused local variables should be removed',18),
(53,'squid:S1172','Unused method parameters should be removed',18),
(54,'squid:S2326','Unused type parameters should be removed',18),
(55,'squid:S1145','Useless \"if(true) {...}\" and \"if(false){...}\" blocks should be removed',18),
(56,'squid:S1135','\"TODO\" tags should be handled',19),
(57,'squid:CommentedOutCodeLine','Sections of code should not be \"commented out\"',19);

DROP TABLE IF EXISTS `violations`;

CREATE TABLE `violations` (
  `indicator` int(8) NOT NULL,
  `project` int(8) NOT NULL,
  `count` double NOT NULL,
  KEY `indicator` (`indicator`),
  KEY `project` (`project`),
  CONSTRAINT `violations_ibfk_1` FOREIGN KEY (`indicator`) REFERENCES `indicators` (`id`),
  CONSTRAINT `violations_ibfk_2` FOREIGN KEY (`project`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
