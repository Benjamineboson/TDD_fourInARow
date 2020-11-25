# TDD_fourInARow
TDD project

1.1 Inledning
I den här uppgiften skall ni använda er av den kunskap och lärdom som införskaffats i
inlämningsuppgifterna 1 till 3 för att skapa ett enkelt spel.
Projektet genomförs i grupper om två-tre personer och bedrivas medelst Test-Driven Development.

1.2 Beskrivning
Konstruera brädspelet Fyra i rad på den form som finns att köpa i spelaffärer och kan se ut enligt
nedanstående bild;
Om ni känner er osäkra på hur spelet går till kan titta här:
https://www.elevspel.se/amnen/hjarngympa/fyra-i-rad.html. Men kom ihåg att ni spelar mot en AI
på den sidan, det ska vi inte göra i vår variant. Där skall två personer omväxlande spela mot varandra
istället.
För att underlätta konstruktionen kan ni använda er av Console (text) för att visa rader och
kolumner. Exempelvis så kan ovanstående bild omformas till nedanstående (givet att röd är X och
gul O);
Figur 1- Brädspel köpt i en affär

1.3 Funktionsbeskrivning (krav)
Programmet har följande tillägg till vad
1. Programmet ska följa spelreglerna för Fyra i rad (se hänvisningen till elevespelssidan ovan).
2. Programmet ska avgöra och berätta när en spelare har vunnit en omgång.
3. Programmet skall slumpa fram vem som startar.
4. Programmet skall spela in alla drag så att man i efterhand kan återskapa förloppet för vidare
analys över hur man spelade.
5. Det ska gå att spela upp en ”omgång”
6. Man ska kunna välja hur många omgångar som ska spelas. När en spelare har vunnit
tillräckligt många omgångar ska programmet skriva ut vem som vann.