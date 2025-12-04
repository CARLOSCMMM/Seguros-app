# Proyecto: App de Seguros

## Introducción

**Seguros App** es una aplicación para Android desarrollada en Kotlin, diseñada para funcionar como un panel de control rápido para clientes de una compañía de seguros. La interfaz principal ofrece acceso directo a las funcionalidades más comunes que un asegurado podría necesitar en su día a día o en una emergencia.

La aplicación se centra en la seguridad y accesibilidad del cliente, permitiendo al usuario realizar acciones clave con un solo toque, como hacer una llamada de emergencia, consultar la web de la compañía, localizar oficinas, configurar una alarma rápida o incluso disfrutar de un pequeño juego de chistes.

---

## Arquitectura y Componentes

La aplicación utiliza una arquitectura basada en `Activities` para cada pantalla y `ViewBinding` para una interacción segura con los elementos de la interfaz. Los datos persistentes, como el número de teléfono, se gestionan a través de `SharedPreferences`.

### Archivos de Código Fuente (`.kt`)

#### `MainActivity.kt`
Es la pantalla de inicio y el centro neurálgico de la aplicación. 

- **`onCreate()`**: Método principal que se ejecuta al iniciar la `Activity`. 
    - **Función**: Inicializa el `layout` (`activity_main`), obtiene una instancia de `SharedPreferences` para acceder a los datos guardados y configura los `listeners` de todos los botones de la interfaz.
- **`btnLlamada.setOnClickListener`**: 
    - **Función**: Inicia la `LlamadaActivity`. Primero, lee el número de teléfono guardado en `SharedPreferences`. Si no hay ninguno, usa un valor por defecto. Luego, pasa este número a la `LlamadaActivity` a través de un `Intent.putExtra()`.
- **`btnUrl.setOnClickListener`**: 
    - **Función**: Abre el navegador web predeterminado del dispositivo con la página de MAPFRE.
- **`btnAlarma.setOnClickListener`**:
    - **Función**: Inicia un temporizador utilizando la aplicación de reloj del sistema.
- **`btnMaps.setOnClickListener`**:
    - **Función**: Abre Google Maps para mostrar la ubicación de una oficina de MAPFRE en Jaén.
- **`btnConfig.setOnClickListener`**:
    - **Función**: Inicia la `ConfiguracionActivity`, permitiendo al usuario navegar a la pantalla de ajustes.
- **`btnJuegoChistes.setOnClickListener`**:
    - **Función**: Inicia la `DadosActivity` para que el usuario pueda jugar al juego de adivinar el resultado de los dados.

#### `LlamadaActivity.kt`
Esta `Activity` se encarga de gestionar y realizar la llamada de emergencia.

- **`onCreate()`**:
    - **Función**: Prepara la `Activity` para la llamada. Recibe el número de teléfono enviado desde `MainActivity`, lo muestra en un `TextView` y configura el `ActivityResultLauncher` para gestionar la respuesta a la solicitud de permisos.
- **`checkPermissionAndCall()`**: 
    - **Función**: Orquesta el proceso de llamada, verificando los permisos antes de proceder.
- **`makeCall()`**: 
    - **Función**: Realiza la llamada telefónica usando un `Intent` con la acción `ACTION_CALL`.

#### `ConfiguracionActivity.kt`
Permite al usuario personalizar el número de teléfono para la llamada de emergencia.

- **`onCreate()`**: 
    - **Función**: Carga la interfaz y recupera el número de teléfono actualmente guardado.
- **`btnGuardar.setOnClickListener`**: 
    - **Función**: Guarda el número de teléfono modificado en `SharedPreferences`.

#### `DadosActivity.kt`
Esta `Activity` contiene un juego donde el usuario debe adivinar el resultado de la suma de tres dados.

- **`onCreate()`**: 
    - **Función**: Inicializa la vista y los eventos, principalmente el listener del botón para lanzar los dados.
- **`initEvent()`**:
    - **Función**: Configura el `setOnClickListener` para el botón de lanzar. Comprueba que el usuario haya introducido un número válido (entre 3 y 18) antes de iniciar el juego.
- **`game()` y `sheduleRun()`**:
    - **Función**: Organizan la lógica del juego. Se utiliza un `ScheduledExecutorService` para simular el lanzamiento de los dados 5 veces (una por segundo) y mostrar el resultado final tras 7 segundos.
- **`throwDadoInTime()`**:
    - **Función**: Simula el lanzamiento de tres dados generando números aleatorios y actualiza las imágenes correspondientes en la interfaz.
- **`selectView()`**:
    - **Función**: Asigna la imagen correcta (`R.drawable.dado1`, `R.drawable.dado2`, etc.) a cada `ImageView` según el resultado del dado.
- **`viewResult()`**:
    - **Función**: Muestra la suma total. Si el usuario ha acertado el número, inicia la `ResultActivity` pasándole el resultado. Si no, muestra un `Toast` indicando el fallo.

#### `ResultActivity.kt`
Esta `Activity` se muestra cuando el usuario gana el juego de los dados. Su función es contar un chiste.

- **`onCreate()`**: 
    - **Función**: Inicializa la vista, configura el motor de `TextToSpeech` y recibe el número ganador desde la `DadosActivity`. Selecciona un chiste, lo muestra en pantalla y lo lee en voz alta.
- **`configureTextToSpeech()`**:
    - **Función**: Prepara el servicio `TextToSpeech` para poder verbalizar el texto.
- **`speakMeDescription()`**:
    - **Función**: Utiliza el motor `TextToSpeech` para leer en voz alta el chiste.
- **`getChisteForNumber()`**:
    - **Función**: Contiene una lista de chistes y devuelve uno diferente según el número resultante del juego.
- **`onDestroy()`**:
    - **Función**: Libera los recursos del motor `TextToSpeech` de forma segura cuando la actividad se destruye.

### Archivos de Layout (`.xml`)

#### `activity_main.xml`
- **Función**: Define la estructura visual de la pantalla principal con una cuadrícula de `ImageButton`.

#### `activity_llamada.xml`
- **Función**: Interfaz simple para confirmar e iniciar la llamada de emergencia.

#### `activity_configuracion.xml`
- **Función**: Pantalla para que el usuario introduzca y guarde el número de teléfono.

#### `activity_dados.xml`
- **Función**: Define la interfaz para el juego de los dados, incluyendo un campo para introducir un número, `ImageView` para los tres dados y el botón de lanzamiento.

#### `activity_result.xml`
- **Función**: Pantalla final que muestra el chiste al usuario que ha ganado el juego. Contiene un `TextView` para el chiste y un botón para repetirlo.

---

## ️ Configuración y Manifiesto
- **`build.gradle.kts`**: 
    - **Función**: Define las dependencias del proyecto y la versión mínima de Android.
- **`AndroidManifest.xml`**:
    - **Función**: Declara las `Activities` de la aplicación y solicita los permisos necesarios como `CALL_PHONE` (para llamadas) y `SET_ALARM` (para el temporizador).
