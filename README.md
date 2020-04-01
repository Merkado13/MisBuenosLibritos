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

## Intalación Máquina Virtual

Hospedaremos la máquina virtual sobre un host Ubuntu 16.04, para ello primeramente instalaremos en el sistema anfitrión [Vagrant](https://www.vagrantup.com/downloads.html) y [Virtual Box](https://www.virtualbox.org/wiki/Downloads)

Una vez tengamos los dos programas crearemos la máquina virtual, abrimos la consola y escribimos:

~~~
mkdir -p ~/vagrant/spring
cd ~/vagrant/spring
vagrant init ubuntu/trusty64
~~~

Con esto descagaremos una imagen ubuntu de 64 bits si no se encuentra en local y se generará un Vagrantfile en la carpeta creada. Si existen problemas con Virtual Box debido al Secure Boot puede ser que sea necesario firmar los módulos oportunos. [Cómo firmar módulos Virtual Box](https://slimbook.es/tutoriales/linux/364-firmando-modulo-virtualbox-en-secureboot-uefi-solucion-a-kernel-driver-not-installed-rc-1908)

Ya podremos hacer uso de la máquina virtual con:
~~~
vagrant up
~~~

## Compilación

Procedemos a continuación con la compilación En Eclipse STS de los proyectos de la aplicación y el servicio interno de manera análoga.

Primeramente, si no lo teníamos ya, necesitaremos un plugin de maven para poder generar el jar ejecutable, el siguiente nos valdrá:

~~~
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <goals>
                <goal>repackage</goal>
            </goals>
            <configuration>
                <classifier>spring-boot</classifier>
                <mainClass>
                  com.baeldung.executable.ExecutableMavenJar
                </mainClass>
            </configuration>
        </execution>
    </executions>
</plugin>
~~~

Este puglin deberá ir dentro de las etiquetas <plugins></puglins> localizadas dentro de <build></build>. Actualizamos el proyecto maven.

A continuación, click derecho sobre el proyecto > Run as > Maven Build...

Ponemos en el cuadro de texto goals, en la pestaña Main
~~~
clean package
~~~

Pulsamos sobre Apply y Run. El jar ejecutable se nos habrá generado en la carpeta target dentro del proyecto

Si hay problemas a la hora de hacer la build es posible que sean problemas con la versión de java instalada. En el sistema tiene que encontrarse el jdk8 no basta con el jre8.

## Despliegue

En este punto ya tendremos la máquina virtual y los jars ejecutables, el de la aplicación y el del servicio interno.

Los jars deben encontrarse en la carpeta compartida definida por defecto al levantar la MV.

Para desplegar la aplicación primero necesitaremos instalar el software necesario para que corra. Para ello, instalaremos java 8 y mysql server. Nos conectamos a la aplicacíon con:

~~~
vagrant up
vagrant ssh
~~~

Ahora mismo nos econtraremos en la máquina virtual, procedemos a descargar e instalar los componenetes:

~~~
sudo apt-get update
sudo apt-get install -y openjdk-8-jre
sudo apt-get install -y mysql-server
~~~

Si hay problemas con que no se encuentra el paquete para instalar java tendremos que indicarle el repositorio de dónde descargarlo explícitamente:

~~~
  sudo add-apt-repository ppa:openjdk-r/ppa
  sudo apt-get update
  sudo apt-get install -y openjdk-8-jre
~~~

Necesitaremos abrir tres terminales: una para la aplicación, otra para el servicio interno y otra para la bbdd. En todas nos metermos dentro de la VM.

Primeramente, en una nos logeamos en la bbdd.

~~~
mysql -u root -p
~~~

Finalmente, en las otras dos terminales ejecutamos los jars correspondientes con:

~~~
java -jar <localización del jar>
~~~

Si todo ha ido bien, con la base de datos levantada y las dos aplicaciones ejecutándose, deberíamos poder acceder a través de un navegador en el host con la dirección 192.168.33.10 por el puerto 8443, ya que está por https.

---

**Integrantes:**

* Marcos Agudo Alarcón: m.agudo.2016@alumnos.urjc.es.
* Celia Merino Valladolid: c.merinov.2016@alumnos.urjc.es.
* Andrea Rodríguez González: a.rodriguezgo.2016@alumnos.urjc.es.

[Github Aplicación](https://github.com/Merkado13/MisBuenosLibritos).
[Github Servicio Interno](https://github.com/Merkado13/SistemaInterno)
[Trello](https://trello.com/b/rQAO4Dcw/misbuenoslibritos).
