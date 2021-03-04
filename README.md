# Тестовое задание для Prime Aspect

Написать консольную утилиту, получающую на вход несколько .csv файлов (разделитель значений - ';').
Первая строка каждого - заголовки значений. Результат работы - набор файлов с названиями соответствующими заголовкам и содержимым - уникальными в рамках всей задачи значениями.

* Требования: 
разбор файлов должен производиться многопоточно, реализация должна использовать исключительно jdk (любой версии).

* Пример входных данных: 

  input1.csv:

      id;version;path;
      0;1;/hello/уточка;
      1;2;/hello/лошадка;
      2;2;/hello/собачка;
      
  input2.csv:
  
      id;name;sex;
      0;ричард;м;
      1;жорж;м;
      2;мария;ж;
      3;пьер;м;

* Пример результата 

  id:
  
      0;1;2;3;
      
  version:

      1;2;
      
  path:

      /hello/уточка;/hello/лошадка;/hello/собачка;
      
  name:
  
      ричард;жорж;мария;пьер;
      
  sex:
  
      м;ж;

**Названия файлов с относительным путем передаются в качестве аргументов командной строки**