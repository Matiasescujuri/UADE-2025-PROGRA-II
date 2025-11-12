#  Sistema de Gestión de Pedidos (UADE - Programación II)

Este proyecto implementa un **sistema de gestión de pedidos** para un restaurante, desarrollado en **Java** aplicando los **Tipos de Datos Abstractos (TDA)** vistos en la materia.  
El sistema permite registrar pedidos, prepararlos, entregarlos y generar reportes estadísticos.

---

##  Funcionalidades principales

### 1 Ingreso y Clasificación de Pedidos
- Se ingresan los datos del cliente y el tipo de comida (Pizza, Empanadas o Hamburguesa).
- Se asigna una **prioridad** (1 a 10) para determinar el orden de atención.
- Los pedidos se almacenan en una **Cola de Prioridad (ColaPrioridad)**.

### 2 Preparación en Cocina(QueueADT)
- Los pedidos se extraen de la cola de prioridad y se pasan a una **Cola común (Cola)** para ser procesados en cocina.
- Se imprime el estado de cada pedido con su prioridad y estado actual.
- Se crea una **copia** de las estructuras para evitar pérdida de datos.

### 3 Entregas(LIFO)
- Los pedidos listos pasan a una **Pila (Stack)** donde se simula el orden de entrega (LIFO).
- Se muestran las rutas de entrega simuladas (domicilios fijos precargados).
- Al ser entregado, el pedido pasa al estado **FINALIZADO**.

### 4 Reportes y Análisis(SimpleDictionary)
- Se utiliza un **Diccionario Simple (SimpleDictionary)** para contar:
    - Cantidad de pedidos *Pendientes* y *Finalizados*.
    - Comidas más pedidas.
- Se muestra el total de pedidos registrados en el sistema.

---

##  Estructura del Proyecto

**Paquete principal:** `org`  
Contiene las siguientes clases:

| Clase | Descripción |
|-------|--------------|
| `Main` | Clase principal con menú y control de flujo general |
| `IngresoPedidos` | Maneja el registro y clasificación de pedidos |
| `Cocina` | Controla la preparación de platos en cocina |
| `Entregas` | Simula las entregas y actualiza el estado de los pedidos |
| `ReportesYAnalisis` | Genera estadísticas con estructuras de diccionario |
| `Utilidades` | Funciones auxiliares para copiar y limpiar estructuras |
| `Pedido` | Representa un pedido con datos y estado |
| `estadoPedido` | Enum que define los estados: `PENDIENTE` y `FINALIZADO` |

---

##  TDA Utilizados

| TDA | Implementación | Uso en el sistema |
|-----|----------------|------------------|
| `QueueADT` | `Cola` | Preparación de platos |
| `PriorityQueueADT` | `ColaPrioridad` | Clasificación de pedidos según prioridad |
| `StackADT` | `Pila` | Control de entregas |
| `SetADT` | `SetArray` | Manejo de claves en diccionarios |        
| `DictionaryADT` | `SimpleDictionary` | Estadísticas y conteos de reportes |