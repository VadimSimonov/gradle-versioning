gc-math-common
----
Набор математических утилит

1. smoothing
---
Применяет сглаживание для двумерной функции методом медианного фильтра

пример использования для типа DoubleValueInUTC:
~~~
{
    List<? extends DoubleValueInUTC> array = ...; // исходные данные
    AbstractSmoothing smooth = new TimeSmoothing(array);
    smooth.applySmoothing();
    List<? extends DoubleValueInUTC> result = smooth.getValuesInUTC();
}
~~~


пример использования для аргумента в формате List<LocalDateTime> 
и для знечения в формате List<Double>:
~~~
{
    List<LocalDateTime> arguments = ...; // исходные данные по оси X
    List<Double> values = ...; // исходные данные по оси Y
    smooth.applySmoothing();
    List<LocalDatTime> smoothedArgumnts = smooth.getSmoothedArguments();
    List<Double> smoothedValues = smooth.getSmoothedValues();
}
~~~

2. approximation
---
Выполняет линейную аппроксимацию данных по двум точкам

пример использования в коде для типа double:
~~~
{
    // y = ax + b; 
    // известные значения: (x1, y1), (x2, y2) : тип double
    // по ним находятся коэффициенты уравнения
    // получаем значнние функции для аргуменита x: 
    
    y = new LineApproximation(x1, y1, x2, y2).apply(x);
}
~~~

пример использования в коде для типа LocalDateTime 
(или любого другого типа, расширяющего класс ChronoLocalDateTime<?>) по оси х:
~~~
{
    // здесь x1, x2 имеют тип LocalDateTime:
    x1 = new LocalDateTime.now();
    x2 = new LocalDateTime.now();
    y = new LineApproximation(x1, y1, x2, y2).apply(x);
}
~~~

