# Sistema de Gestión de Pedidos (UADE - Programación II)

Este proyecto implementa un sistema de gestión de pedidos para un restaurante, desarrollado en Java aplicando los Tipos de Datos Abstractos (TDA) vistos en la materia.
El sistema permite registrar pedidos, prepararlos, entregarlos y generar reportes estadísticos.

## Funcionalidades principales
# Ingreso y Clasificación de Pedidos (PriorityQueueADT)

Se ingresan los datos del cliente y el tipo de comida (Pizza, Empanadas o Hamburguesa).

Se asigna una prioridad (1 a 10) para determinar el orden de atención.

Los pedidos se almacenan en una Cola de Prioridad (ColaPrioridad).

# Preparación en Cocina (QueueADT)

Los pedidos se extraen de la cola de prioridad y se pasan a una Cola común (Cola) para ser procesados en cocina.

Se imprime el estado de cada pedido con su prioridad y estado actual.

Se crea una copia de las estructuras para evitar pérdida de datos.

# Entregas (StackADT"LIFO" + Grafo)

Los pedidos listos pasan a una Pila (Stack) donde se simula el orden de entrega (LIFO).

Se utilizan GRAFOS para representar las rutas de entrega entre la cocina y los domicilios precargados.

Se muestran las rutas calculadas y el tiempo estimado de entrega.

Al ser entregado, el pedido pasa al estado FINALIZADO.

# Reportes y Análisis (DictionaryADT + SetADT)

Se utiliza un Diccionario Simple (SimpleDictionary) para contar:

Cantidad de pedidos Pendientes y Finalizados.

Comidas más pedidas.

Se usa un Set (SetArray) para manejar las claves del diccionario.

Se muestra el total de pedidos registrados en el sistema.



