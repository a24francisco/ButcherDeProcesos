# PSP-Proyecto UD1: Butcher de procesos

## 1- Descripción del proyecto
Implementar un simulador simplificado de un batcher: trabajaremos en un único equipo (no en un cluster real) pero simularemos un conjunto de cores y memoria total, como si fuera un mini-cluster.
Cada trabajo tendrá requisitos de CPU, memoria y tiempo de ejecución, y el batcher deberá decidir qué trabajos ejecutar en paralelo, cuáles poner en cola, y cómo mostrar al usuario el estado de todos ellos

## 2- Arquitectura del sistema
La distribución en paquetes y clases es la siguiente: 
### 2.1 Main
 - ButcherProcesos: clase principal. Carga los jobs , permite elegir el planificador y lanza los procesos. Permite visualizar los procesos.
 - WorkerMain: proceso hijo. Simula el trabajo de cada proceso y muestra informacion cada 500ms.
### 2.2 Job
 - Job: POJO de cada trabajo ademas del método isValid().
 - State: enum con los diferentes estados del job.
 - Resources: Maneja los recursos.
 - Workload: Maneja la carga de trabajo.
### 2.3 Computer
 - Computer: Representa el hardware gestionando los cores y la memoria total.
### 2.4 Manager
 - JobManager: gestiona las colas de trabajo y los cambios de estado
### 2.5 Planification
 - Scheduler: interfaz de los planificadores
 - FCFS: Planificador de FCFS que implementa Scheduler.
 - RR: Planificador de RR que implementa Scheduler.



## 3-Requisitos
- Sistema operativo compatible con java
