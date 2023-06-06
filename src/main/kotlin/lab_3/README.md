# Regex
***Регулярное выражение*** (сокращенное как regex или regexp; также называется рациональным выражением) - это **последовательность символов, определяющая шаблон поиска**.

[Шпаргалка по регулярным выражениям](https://medium.com/nuances-of-programming/шпаргалка-по-регулярным-выражениям-в-примерах-53820a5f3435)
		
## **Квантификаторы — * + ? и {}**
**abc*** - соответствует строке, в которой после **ab** следует 0 или более символов **c**.    
**abc+** - соответствует строке, в которой после **ab** следует один или более символов **c**.    
**abc?** - соответствует строке, в которой после **ab** следует 0 или один символ **c**.    
**abc{2}** - соответствует строке, в которой после **ab** следует 2 символа **c**.    
**abc{2,}** - соответствует строке, в которой после **ab** следует 2 или более символов **c**.    
**abc{2,5}** - соответствует строке, в которой после **ab** следует от 2 до 5 символов **c**.    
**a(bc)*** - соответствует строке, в которой после **ab** следует 0 или более последовательностей символов **bc**.    
**a(bc){2,5}** - соответствует строке, в которой после **ab** следует от 2 до 5 последовательностей. символов **bc**.    

## **Оператор ИЛИ — | или \[]**
**a(b|c)** соответствует строке, в которой после **a следует** **b или c**.    
**a\[bc]**  как и в предыдущем примере.    

## **Символьные классы — \d \w \s и .**    
**\\d** соответствует одному символу, который является цифрой.    
**\\w** соответствует слову (может состоять из букв, цифр и подчёркивания).    
**\\s** соответствует символу пробела (включая табуляцию и прерывание строки).    
**.** соответствует любому символу.    

# XPath
## Немного теории
***XPath*** - язык запросов к элементам XML.    
*Path - путь.*    

#### Выбор нод без указания имени:    
**\***- ноды с любым именем;    
**commet()** - комментарий;    
**text()** - текст, лежащий в коде;    
**node()** - ноды любого типа.    

#### Оси:    
**self:: (.)** - текущая нода;    
**child::** - дети;    
**descendant:: (//)** - все потомки;    
**parent:: (…)** -  родители;    
**ancestor::** - все предки;    
**attribute:: (@)** - атрибуты.    
> Как именно пользоваться не очень понятно!    

#### Инструменты:
```Kotlin
val parser =
    XPathFactory.newInstance() // промежуточный объект
        .newXPath()
```

`parser` - объект, имеющий функцию, которая может извлекать запрашиваемые данные.    
`XPathFactory` -     
`newInstance()` - получает новый экземпляр `XPathFactory`, используя объектную модель по умолчанию.    
`newXPath()` - возвращает новый `XPath()`, используя объектную модель.    

```Kotlin
val doc = 
	DocumentBuilderFactory.newInstance().
		newDocumentBuilder()
			.parse("data.xml")
```

`DocumentBuilderFactory` - определяет заводской API, который позволяет приложениям получить синтаксический анализ.    
`newInstance()` - получает новый экземпляр класса `DocumentBuilderFactory`.        
`newDocumentBuilder()` - создает новый экземпляр `DocumentBuilder`.    
`parse()` *(разбор)* - разбор XML-документа.    

#### Обработка
```Kotlin
 val result = parser
            .compile(query)
            .evaluate(doc, XPathConstants.NODESET) as NodeList
```
> *`query` - запрос.*    

`compile(query)` - компиляция запроса.    
`evaluate()` - вычисляет выражение XPath в указанном контексте и возвращает результат (выполнение). Возвращает либо один узел, либо коллекцию.    
`NodeList` - примитивная реализация списка, не имеет функции `map()`.    

Пример реализации функции:    
```Kotlin
fun <R> NodeList.map(transform: (Node) -> R): List<R> =
    ArrayList<R>().also {
        for (index in 0 until length)
            it.add(
                transform(
                    item(index)
                )
            )
    }

```

## Практические заметки    
    
```Kotlin
listOf(  
	"//DataSet/Series" to { node: Node ->  
		node.getText("Obs/Time") + "-" + node.getText("Obs/ObsValue/@value")  
	}  
).map { (query, cursorQueryStaff) ->  
	val result = parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList  
	result.map(cursorQueryStaff).also {  
	print(it)  
	}   
}
```

`node.getText("Obs/Time")` - отображает значение между тегами.    
Пример,  из `<generic:Time>2000</generic:Time>` выведет `2000`.    
`node.getText("Obs/ObsValue/@value")` - отображает значение атрибута `value` (он может с любым названием).    
Пример, из `<generic:ObsValue value="44,3"/>` выведет `44,3`.    

> В лабе в пути `generic` указывать не обязательно.    

`SeriesKey/Value[@concept='s_OKATO']`  - в этом случае атрибут @ указывает на раздел с атрубутом равным некоторому значению, а не само значение атрибута.    

## Примеры    

```Kotlin
listOf(  
	"//DataSet/Series" to { node: Node ->  
		node.getText("Obs/Time") + ": " +  
			node.getText("SeriesKey/Value[@concept='s_OKATO']/@value") 
	}  
).map { (query, cursorQueryStaff) ->  
	val result = parser.compile(query).evaluate(doc, XPathConstants.NODESET) as NodeList  
	result.map(cursorQueryStaff).also {  
		it.forEach {  
			println(it)  
		}  
	}  
}
```
Вывод:
```Kotlin
2000: 643
2000: 643
... // данные
2020: 643
2021: 643
2021: 643
2021: 643
2003: 45000000000
2003: 45000000000
2003: 45000000000
2003: 45000000000
... // данные
2020: 52401000000
2020: 52401000000
2021: 52401000000
2021: 52401000000
2021: 52401000000

```
# StAX
***StAX*** *(Streaming API for XML)* -  это интерфейс прикладного программирования (API) для чтения и записи документов XML, созданный сообществом разработчиков языка программирования Java.    
***Streaming - потоковый***    

> Один раз пробегается по всему документу и на выходе получаем всю нужную информацию. Потоковый парсер самый быстрый, но его сложнее программировать и он не может сохранять данные.    

#### Инструменты:    
###### // из лекции    
```Kotlin
fun reader() = XMLInputFactory
        .newInstance()
        .createXMLStreamReader(
            Reader::class.java.getResourceAsStream("data_part.xml")
        )
fun doc() = DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(
            Reader::class.java.getResourceAsStream("data_part.xml")
        )
```

`reader()` - потоковый источник информации, который попорядку считывает XML-файл и говорит, что происходит.    

`XMLInputFactory` определяет абстрактную реализацию фабрики для получения потоков.    
`XMLInputFactory.newInstance()` - создание фабрики `XMLInputFactory`.    
`createXMLStreamReader` - создание `createXMLStreamReader` *(средство чтения **потока**)*.    
Класс `XMLStreamReader` предоставляет итератор событий, который можно использовать для итерации по событиям, возникающим при анализе XML-документа.    
	**hasNext ()** – Используется для проверки, существуют ли другие события или нет.    

##### // используемые    
```Kotlin
val reader = XMLInputFactory.newInstance()  
	.createXMLEventReader(FileInputStream("data.xml"))
```

`reader` - потоковый источник информации, который по порядку считывает XML-файл и говорит, что происходит.    

`XMLInputFactory` определяет абстрактную реализацию фабрики для получения потоков.    
`XMLInputFactory.newInstance()` - создание фабрики `XMLInputFactory`.    

`createXMLEventReader` - создает новый `XMLEventReader`  *(cредство чтения **событий** XML)* из java.io.InputStream.    
	Параметры: `stream` – входной поток для чтения из;    
	Возвращается: экземпляр `XMLEventReader`;    
		`XMLEventReader`  - это интерфейс верхнего уровня для синтаксического анализа XML-событий. Он предоставляет возможность просмотреть следующее событие и возвращает информацию о конфигурации через интерфейс свойств.    
	Бросает: `XMLStreamException` – при возникновении ошибки.    

`FileInputStream` - создает `**FileInputStream**`, открывая соединение с фактическим файлом, названным по имени пути в файловой системе. Для представления этого файлового соединения создается новый объект `FileDescriptor`.    
Если именованный файл не существует, является каталогом, а не обычным файлом, или по какой-либо другой причине не может быть открыт для чтения, то генерируется **`FileNotFoundException`**.    
	Параметры: имя – зависящее от системы имя файла.    
	Бросает:     
		**`FileNotFoundException`** – если файл не существует, является каталогом, а не обычным файлом, или по какой-либо другой причине не может быть открыт для чтения.    
		`SecurityException` – если существует диспетчер безопасности и его метод checkRead запрещает доступ на чтение к файлу.    
*`FileInputStream` - поток ввода файла.*    

#### Заметки по фрагментам кода:

```Kotlin
// ...
while (reader.hasNext()) {  
	val xmlEvent = reader.nextEvent()  
	if (xmlEvent.isStartElement) {  
		val startElement = xmlEvent.asStartElement()  
		when (startElement.name.localPart) {  
			"Time" -> time1 = reader.elementText  
			"Value" -> {  
				val concept = startElement.getAttributeByName(QName("concept"))?.value  
				val value = startElement.getAttributeByName(QName("value"))?.value
				// ...
			}
	// ...
	}
// ...
```

> Событие в данном контексте это некий блок тегов <...> ... </...>.    

`.hasNext()` - возвращает значение true, если есть еще события синтаксического анализа, и значение false, если событий больше нет.    
`reader.hasNext()` - проверяет есть ли еще события (true - есть, false - нет).    

`.nextEvent()` - получает следующее XML-событие. Начальным событием является запуск документа. Возвращает следующее событие XML.    
`reader.nextEvent()` - следующее событие XML.    

`xmlEvent.isStartElement` - служебная функция для проверки того, является ли это событие начальным элементом.    
`.asStartElement()` - возвращает это событие, как событие начального элемента.    

`startElement.name.localPart` - по сути возвращает имя тега.    
	`.name` - получает название этого события, возвращает точное название этого события.    
	`.localPart` - получает локальную часть этого имени, возвращает локальную часть этого события.    

`reader.elementText` - отображает содержимое элемента, содержащего только текст. Т.е. само значение тега.     
Пример:     
```
<generic:Time>2020</generic:Time>
```
Результат:    
```
2020
```

`.getAttributeByName` - возвращает атрибут, соответствующий имени value или null.    
`QName` - конструктор имен, указывающий локальную часть (внутри тега).    
`startElement.getAttributeByName(QName("атрибут"))?.value` - возвращает значение указанного атрибута.    

*// нет в примере:*    
`.isEndElement` - служебная функция для проверки того, является ли это событие конечным элементом. Возвращает true, если событие является конечным элементом, false в противном случае.    

***==Главное:==     
a = `reader.elementText`  // значение тега    
b = `startElement.name.localPart` // имя тега    
c = `startElement.getAttributeByName(QName("атрибут"))?.value` // значение атрибута***    


# StAX и конечные автоматы    

***Конечный автомат*** - описание какой-то системы, которая имеет некоторые конечные точки.    
***Конечный автомат*** - это некоторая абстрактная модель, содержащая конечное число состояний чего-либо. Используется для представления и управления потоком выполнения каких-либо команд.    

Суть: в лабе конечные точки являются объектами класса перечисления, то есть по определению события к некоторому объекту обрабатываем данные. *(Объекты выступают в качестве идентификаторов)*    
