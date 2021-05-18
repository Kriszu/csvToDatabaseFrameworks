# csvToDatabaseFrameworks

Prośba o podmienie ścieżki folderu gdzie będą uploadowane pliki w:
TestController:27
ParserService:31


Przykładowe logi:
2021-05-18 22:19:36 INFO  ParserService:114 - trying to save person from csv to db
2021-05-18 22:19:36 INFO  ParserService:46 - Saved person to database: Person{firstName='Stefania', surname='Testowy', age='33', phoneNumber='600700800'}
2021-05-18 22:19:36 INFO  ParserService:46 - Saved person to database: Person{firstName='Maria', surname='Ziółko', age='22', phoneNumber='555666777'}
2021-05-18 22:19:36 INFO  ParserService:46 - Saved person to database: Person{firstName='Jolanta', surname='Magia', age='21', phoneNumber='666000111'}
2021-05-18 22:19:36 INFO  ParserService:46 - Saved person to database: Person{firstName='Marian', surname='Kowalewski', age='71', phoneNumber='670540120'}
2021-05-18 22:19:36 INFO  ParserService:46 - Saved person to database: Person{firstName='Zygmunt', surname='Stefanowicz', age='30', phoneNumber='null'}
2021-05-18 22:19:36 INFO  ParserService:52 - Saved person to database: Person{firstName='Ernest', surname='Gąbka', age='30', phoneNumber='null'}
2021-05-18 22:19:36 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Elżbieta', surname='Żółw', age='33', phoneNumber='670540120'}
2021-05-18 22:19:44 INFO  ParserService:114 - trying to save person from csv to db
2021-05-18 22:19:44 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Stefania', surname='Testowy', age='33', phoneNumber='600700800'}
2021-05-18 22:19:44 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Maria', surname='Ziółko', age='22', phoneNumber='555666777'}
2021-05-18 22:19:44 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Jolanta', surname='Magia', age='21', phoneNumber='666000111'}
2021-05-18 22:19:44 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Marian', surname='Kowalewski', age='71', phoneNumber='670540120'}
2021-05-18 22:19:44 INFO  ParserService:46 - Saved person to database: Person{firstName='Zygmunt', surname='Stefanowicz', age='30', phoneNumber='null'}
2021-05-18 22:19:44 INFO  ParserService:52 - Saved person to database: Person{firstName='Ernest', surname='Gąbka', age='30', phoneNumber='null'}
2021-05-18 22:19:44 INFO  ParserService:48 - Person didn't save due to phone already existPerson{firstName='Elżbieta', surname='Żółw', age='33', phoneNumber='670540120'}
2021-05-18 22:19:50 INFO  ParserService:41 - Person{firstName='Stefania', surname='Testowy', age='33', phoneNumber='600700800'} deleted
2021-05-18 22:20:02 INFO  ParserService:61 - There is: 7 users
2021-05-18 22:20:14 INFO  ParserService:68 - There is: [Person{firstName='Jolanta', surname='Magia', age='21', phoneNumber='666000111'}]with surname: Magia
2021-05-18 22:20:27 INFO  ParserService:85 - There is: [Person{firstName='Jolanta', surname='Magia', age='21', phoneNumber='666000111'}, Person{firstName='Maria', surname='Ziółko', age='22', phoneNumber='555666777'}, Person{firstName='Zygmunt', surname='Stefanowicz', age='30', phoneNumber='null'}, Person{firstName='Ernest', surname='Gąbka', age='30', phoneNumber='null'}, Person{firstName='Zygmunt', surname='Stefanowicz', age='30', phoneNumber='null'}]
2021-05-18 22:20:33 INFO  ParserService:95 - Returned oldest person with number: Person{firstName='Marian', surname='Kowalewski', age='71', phoneNumber='670540120'}
2021-05-18 22:20:49 INFO  ParserService:52 - All users were deleted
