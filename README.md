# MisBuenosLibritos

Se trata de una aplicación de catalogación de lecturas en la que los usuarios pueden crear y gestionar su propia colección de libros, ver la puntuación que éstos tienen o los libros escritos por cada autor.

* **Funcionalidad pública:** Buscar libros y autores.
* **Funcionalidad privada:** Crear y gestionar colecciónes de libros en diferentes listas, añadir libros a las diferentes colecciónes y puntuarlos. Los autores y editoriales además podrán incluir nuevos libros a la base de datos.

---

## Entidades y sus relaciones: Libro, Usuario, Autor, Editorial y Colección

## Esquema Entidad-Relación
![alt text](/ImgsREADME/esquema_entidad_relacion.png "relaciones BBDD")

## Relaciones descriptivas
![alt text](/ImgsREADME/relaciones_nuevo.png "relaciones descriptivas")

## UML
![alt text](/ImgsREADME/uml.png "uml")


## Diagrama de clases 
![alt text](/ImgsREADME/diagrama_clases.png "diagrama clases")

---

## Páginas

**Página de Bienvenida**
Página de inicio, se muestran todas las colecciones del usuario y todos los libros disponibles.

![alt text](/ImgsREADME/pantallazosV2/home.png "home")

**Página de Inicio de Sesión**
Página de inicio de sesión, se deben introducir el nombre de usuario y la contraseña de un usuario registrado para poder acceder a las páginas privadas. 

![alt text](/ImgsREADME/pantallazosV2/login.png "inicio sesión")

**Página de Registro de un Nuevo Usuario**
Página de registro de nuevos usuarios, en función del rol elegido, se mostrarán unos campos que se deben rellenar u otros. 

![alt text](/ImgsREADME/pantallazosV2/crearUsuario.png "registro usuarios")

**Mis Colecciones**
Presenta todas las colecciones del usuario, en está página también se pueden crear nuevas colecciones de libros.

![alt text](/ImgsREADME/pantallazosV2/colecciones.png "miscolecciones")

**Página de Colección**
Muestra todos los libros que se encuentran en la lista.

![alt text](/ImgsREADME/pantallazosV2/coleccionConcreta.png "conleccionconcreta")

**Editar Colección**
En está página se puede modificar el nombre o la descripción de la collección, así como eliminar ciertos libros o la colección entera.

![alt text](/ImgsREADME/pantallazosV2/editarColeccion.png "editar coleccion")

**Búsqueda**
Muestra el resultado de la búsqueda tanto para libros como para autores y editoriales.

![alt text](/ImgsREADME/pantallazosV2/buscador.png "buscador")

**Mi Perfil**
Muestra las colecciones del usuario y sus datos personales, desde aquí también se puede acceder a "Mis Colecciones".

![alt text](/ImgsREADME/pantallazosV2/perfil_usuario.png "miperfil")

**Mi Perfil de Autor**
Muestra el perfil de un usuario con el Rol de Autor, muestra sus datos, sus colecciones y además un enlace para añadir un nuevo libro que haya publicado.

![alt text](/ImgsREADME/pantallazosV2/perfil.png "perfilautor")

**Mi Perfil de Editorial**
Al igual que el perfil de autor pero con datos respectivos y más específicos de una editorial.

![alt text](/ImgsREADME/pantallazosV2/perfil_editorial.png "perfileditorial")

**Página de Libro**
Muestra toda la información disponible de un libro en concreto: título, ISBN, Autor, Editorial, Estado (por leer, leyendo, leido). Adicionalmente en la misma página puede agregarse a cualquier colección creada por el usuario.

![alt text](/ImgsREADME/pantallazosV2/book.png "book")

**Añadir Libro**
Presenta un formulario a rellenar por el Autor o la Editorial para añadir un libro que hayan publicado. Se muestran todos los géneros y los tags relacionados con el libro.

![alt text](/ImgsREADME/pantallazosV2/addBook.png "addbook")


**Perfil de Autor/Edtorial**
Muestra el perfil de un autor/editorial si se es un usuario normal. En esta página se muestran tanto los libros publicados como otras colecciones creadas.

![alt text](/ImgsREADME/pantallazosV2/usuario.png "perfil usuario")

## Diagrama de Navegación
![alt text](/ImgsREADME/navegacion.png "diagrama navegacion")

## Funcionalidades servicio interno: Envío de correos electrónicos a usuarios

---

**Integrantes:**

* Marcos Agudo Alarcón: m.agudo.2016@alumnos.urjc.es.
* Celia Merino Valladolid: c.merinov.2016@alumnos.urjc.es.
* Andrea Rodríguez González: a.rodriguezgo.2016@alumnos.urjc.es.

[Github](https://github.com/Merkado13/MisBuenosLibritos).
[Trello](https://trello.com/b/rQAO4Dcw/misbuenoslibritos).
