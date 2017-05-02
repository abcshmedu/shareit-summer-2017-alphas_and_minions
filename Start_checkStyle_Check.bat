if exist checker-output goto:check
mkdir checker-output

:check
		  ::Pfad zu checkstyle-6.11.2-all.jar				::Pfad zu checkstyle-config.xml			   ::Pfad zum Projekt								::Pfad der Ausgabedatei
java -jar "checkstyle\checkstyle-6.12.1-all.jar" -c 		"checkstyle-config.xml" 					".\src\main" > checker-output\checkStyle.txt

::(C) 2015, Elvira Hauer, Elvira.Hauer@gmx.net