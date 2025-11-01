# Proyecto: App de Seguros

## Introducción

**Seguros App** es una aplicación para Android desarrollada en Kotlin, diseñada para funcionar como un panel de control rápido para clientes de una compañía de seguros. La interfaz principal ofrece acceso directo a las funcionalidades más comunes que un asegurado podría necesitar en su día a día o en una emergencia.

La aplicación se centra en la seguridad y accesibilidad del cliente, permitiendo al usuario realizar acciones clave con un solo toque, como hacer una llamada de emergencia, consultar la web de la compañía, localizar oficinas o configurar una alarma rápida.

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
    - **Función**: Abre el navegador web predeterminado del dispositivo con la página de MAPFRE. Lo hace creando un `Intent` con la acción `ACTION_VIEW` y un `Uri` que contiene la URL.
- **`btnAlarma.setOnClickListener`**:
    - **Función**: Inicia un temporizador utilizando la aplicación de reloj del sistema.
        1.  Crea un `Intent` con la acción `AlarmClock.ACTION_SET_TIMER` para configurar un temporizador.
        2.  Añade datos extra al `Intent`: un mensaje ("Alarma de Seguros") con `EXTRA_MESSAGE`, una duración de 120 segundos con `EXTRA_LENGTH`, y `EXTRA_SKIP_UI` en `false` para mostrar la interfaz del reloj.
        3.  Verifica si hay alguna aplicación en el dispositivo que pueda manejar este `Intent`.
        4.  Si se encuentra una aplicación, la inicia y muestra un `Toast` confirmando que el temporizador fue programado. Si no, muestra un mensaje de error.
- **`btnMaps.setOnClickListener`**:
    - **Función**: Abre Google Maps para mostrar la ubicación de una oficina de MAPFRE en Jaén. Construye un `Uri` con el formato `geo:` utilizando variables para la latitud, longitud y etiqueta, lo que facilita futuras modificaciones. El `Intent` se configura para abrirse específicamente con la aplicación de Google Maps (`mapIntent.setPackage(...)`).
- **`btnConfig.setOnClickListener`**:
    - **Función**: Inicia la `ConfiguracionActivity`, permitiendo al usuario navegar a la pantalla de ajustes.

#### `LlamadaActivity.kt`
Esta `Activity` se encarga de gestionar y realizar la llamada de emergencia.

- **`onCreate()`**:
    - **Función**: Prepara la `Activity` para la llamada. Recibe el número de teléfono enviado desde `MainActivity`, lo muestra en un `TextView` y configura el `ActivityResultLauncher` para gestionar la respuesta a la solicitud de permisos.
- **`checkPermissionAndCall()`**: 
    - **Función**: Orquesta el proceso de llamada. Primero, comprueba si hay un número válido. Luego, verifica si la app tiene el permiso `CALL_PHONE` usando `ContextCompat`. Si el permiso está concedido, llama a `makeCall()`. Si no, lanza la petición de permiso al usuario.
- **`makeCall()`**: 
    - **Función**: Es el método que finalmente realiza la llamada. Crea un `Intent` con la acción `ACTION_CALL` y un `Uri` con el formato `tel:`, que el sistema operativo interpreta como una orden para iniciar una llamada telefónica.

#### `ConfiguracionActivity.kt`
Permite al usuario personalizar el número de teléfono para la llamada de emergencia.

- **`onCreate()`**: 
    - **Función**: Carga la interfaz y recupera el número de teléfono actualmente guardado en `SharedPreferences` para mostrarlo en un `EditText`, de modo que el usuario pueda verlo o modificarlo.
- **`btnGuardar.setOnClickListener`**: 
    - **Función**: Captura el texto del `EditText` y lo guarda de forma persistente en `SharedPreferences` usando `edit().putString().apply()`. Tras guardar, finaliza la `Activity` y vuelve a la pantalla anterior.

### Archivos de Layout (`.xml`)

#### `activity_main.xml`
- **Función**: Define la estructura visual de la pantalla principal.
- **Diseño**: Utiliza un `ConstraintLayout` para organizar una cuadrícula de 5 `ImageButton` con sus correspondientes `TextView` a modo de etiquetas. Las vistas están interconectadas con restricciones (`constraints`) para garantizar una correcta visualización en diferentes densidades y tamaños de pantalla.

#### `activity_llamada.xml`
- **Función**: Define la interfaz de la pantalla de llamada.
- **Diseño**: Un `ConstraintLayout` simple que centra vertical y horizontalmente los elementos principales: un `TextView` (`txtPhone`) para mostrar el número, un `Button` para iniciar la llamada y un `ImageView` para volver a la configuración.

#### `activity_configuracion.xml`
- **Función**: Define la pantalla donde el usuario introduce el número de teléfono.
- **Diseño**: Un `ConstraintLayout` que organiza verticalmente un `TextView` (etiqueta), un `EditText` (campo de entrada) y un `Button` (para guardar). El `EditText` está configurado con `android:inputType="phone"` para facilitar la entrada de números.

---

## ️ Configuración y Manifiesto
- **`build.gradle.kts`**: 
    - **Función**: Define las dependencias del proyecto,se utiliza para la que versión minima de android sea la 21 para ver como actúa en los permisos de la llamada.
- **`AndoidManifest.xml`**:
    - **Función**: Define los permisos de alarma y de llamada.