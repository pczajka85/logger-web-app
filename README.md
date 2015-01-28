# logger-web-app

Aplikacja służąca do zbierania i przeglądania logów. 

Przed zbudowaniem projektu należy ustawić zmienną pl.cyfrowypolsat.job.FileDownloader.MAIN_LOG_DIR, która będzie wskazywać miejsce na serwerze, na które będą ściągane logi z serwerów zewnętrznych.

Hasła w tabeli developer są haszowane za pomocą algorytmu md5.

Konfiguracja:

1. Po zalogowaniu do aplikacji wchodzimy w zakładkę 'Konfiguracja'

2. Dodajemy dane dostępowe FTP do aplikacji, której logi chcemy zbierać, a potem przeglądać.

3. 'Ścieżki logów' - Ustawiamy ścieżki do katalogów logów, np : logs, node1, node2, node3

4. 'Nazwy Logów' - Ustawiamy nazwy logów za pomocą wyrażenia regularnego, np: .*catalina.*, .*icok.*

Tak skonfigurowana aplikacja będzie pobierać codziennie minutę po północy pliki logów z dnia poprzedniego.