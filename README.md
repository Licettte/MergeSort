# MergeSort

Программ MergeSort предназначена для слияние нескольких построчно отсортированных файлов в один.
Для запуска требуется установленная JDK 11 версии. Файлы читаются одновременно, наименьшее (наибольшее)
значение записывается в итоговый файл. При этом ошибочные значения (имеющие неправильный формат
или неотсортированные) пропускаются. Строки с пробелами считаются ошибочными.

`Параметры программы задаются при запуске через аргументы командной строки, по порядку:`
1. Режим сортировки (-a или -d) - возрастающий или убывающий, необязательный, по умолчанию сортируем по возрастанию.
2. Тип данных (-s или -i) - строка или целое число, обязательный. 
3. Имя выходного файла, обязательное.
4. Остальные параметры – имена входных файлов, не менее одного.


`Инструкция по применению:`

1. Перейти в директорию, где находится файл MergeSort.java
2. Файл компилируется командой javac MergeSort.java
3. Сортировка запускается командой java MergeSort [options] outputFile [inputFiles]
4. Подсказка вызывается командой java MergeSort --help


`Примеры запуска из командной строки для Windows:`

```cli
java MergeSort -a -i out.txt in.txt (для целых чисел по возрастанию).
java MergeSort -s out.txt in1.txt in2.txt in3.txt (для строк по возрастанию).
java MergeSort -d -s out.txt in1.txt in2.txt (для строк по убыванию).
```
