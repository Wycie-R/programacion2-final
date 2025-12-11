# 📦 Control de Stock de Depósito - App Android

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![Java](https://img.shields.io/badge/Language-Java-orange?logo=java)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-blue)
![Database](https://img.shields.io/badge/Database-Room-lightgrey)

Aplicación nativa de Android desarrollada para la gestión de inventario en depósito. Permite realizar operaciones CRUD completas, capturar evidencia fotográfica y sincronizar datos con un servidor remoto mediante API REST.

[cite_start]Proyecto Final para la materia **Programación II** de la **Universidad del Norte (UniNorte)**, Año 2025[cite: 1, 5, 8].

---

## 🚀 Características Principales

De acuerdo a la rúbrica de evaluación, la aplicación implementa:

* [cite_start]**🏗 Arquitectura MVVM:** Separación limpia de código usando *Model-View-ViewModel* para garantizar mantenibilidad y testeo[cite: 12].
* [cite_start]**💾 Base de Datos Local (Room):** Persistencia de datos offline utilizando SQLite abstraído con la librería Room (Entidades y DAO)[cite: 12].
* [cite_start]**🌐 Sincronización Cloud (Retrofit):** Envío automático de datos a un servidor remoto (Webhook.site) tras cada creación o modificación[cite: 12].
* [cite_start]**📷 Integración de Cámara:** Captura de fotos utilizando `ActivityResultLauncher` y almacenamiento local seguro mediante `FileProvider`[cite: 12].
* [cite_start]**📝 Sistema de Logs:** Registro interno de eventos (Creación, Modificación, Errores de Red) visualizable dentro de la app[cite: 12].
* [cite_start]**✏️ CRUD Completo:** Funcionalidades para Agregar, Listar, Editar y Eliminar productos[cite: 13, 14].
* [cite_start]**🎨 UI/UX Moderna:** Uso de `ConstraintLayout`, `RecyclerView`, `CardView` y librería `Glide` para manejo eficiente de imágenes.

---

## 🛠 Tecnologías y Librerías

* **Lenguaje:** Java 17
* **Entorno:** Android Studio Koala | Build con Kotlin DSL
* **Core:**
    * `androidx.lifecycle` (ViewModel & LiveData)
    * `androidx.room` (Base de datos)
* **Red:**
    * `Retrofit2` (Cliente HTTP)
    * `GSON` (Parseo JSON)
* **Multimedia:**
    * `Glide` (Carga y cacheo de imágenes)
* **Vista:**
    * ViewBinding
    * Material Design Components

---

## ⚙️ Configuración del Proyecto

### Requisitos Previos
* Android Studio Ladybug o superior.
* JDK 17 configurado en el IDE.
* Dispositivo o Emulador con Android 7.0 (API 24) o superior.

### Instalación
1.  Clonar el repositorio:
    ```bash
    git clone [https://github.com/TU_USUARIO/Programacion2-Final.git](https://github.com/TU_USUARIO/Programacion2-Final.git)
    ```
2.  Abrir en Android Studio y esperar la sincronización de Gradle.
3.  **Configuración de API:**
    * El proyecto utiliza [Webhook.site](https://webhook.site) para simular el backend.
    * Para probar la sincronización, actualice la constante `BASE_URL` en `RetrofitClient.java` con su propia URL de prueba.

---
