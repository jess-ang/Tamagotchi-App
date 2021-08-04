
# TamagotchiApp - UABC (Nov - Dic 2020)
Diseño de interfaz y desarrollo lógico de aplicación móvil para Android. El juego consiste en que el usuario satisfaga diferentes necesidades de una mascota virtual seleccionada. Tecnologías utilizadas: Android Studio y Java.

### Iniciando la aplicación
Al iniciar la aplicación por primera vez, se le pide al usuario elegir una mascota y asignarle un nombre a esta. Al enviar la información, el siguiente paso es ingresar su nombre y correo electrónico.

![Choose pet image](https://github.com/jess-ang/Tamagotchi-App/blob/master/Images/choose_pet.png)

Una vez completada esta información se almacenará en la base de datos y la aplicación se actualiza, mostrando la pantalla principal con la mascota elegida. La siguiente vez que se abra la aplicación ya no será necesario completar esta información, pues solo es solicitada una vez.

### Atender las necesidades de la mascota

En la parte inferior se presentan distintos botones, que de acuerdo a la necesidad que presente la mascota, el usuario deberá presionar algún botón en específico para poder atenderla.

La mascota presenta 5 necesidades, el mensaje es actualizado cada que se presenta una nueva necesidad:

![Needs image](https://github.com/jess-ang/Tamagotchi-App/blob/master/Images/needs1.png) ![Needs fulfilled image](https://github.com/jess-ang/Tamagotchi-App/blob/master/Images/needs2.png)

Al presionar el botón indicado, el mensaje cambiará para indicar al usuario que la acción fue completada

### Guardar una imagen

El botón “Photo” le permite al usuario tomar una foto de su mascota, la imagen es descargada en su celular y se puede consultar en su galería de fotos.

### Perfil del usuario

Al hacer clic en el botón “My profile” el usuario puede acceder a otra pantalla donde se muestra su nombre y correo electrónico.

![Profile image](https://github.com/jess-ang/Tamagotchi-App/blob/master/Images/profile.png)

En esta misma sección puede actualizar sus datos llenando los campos que se encuentran en la parte inferior. Una vez que se envían los datos, la pantalla se vuelve a cargar y se muestran los nuevos datos.

### Enviar una notificación por correo electrónico

Al presionar el botón “Email me” el usuario puede enviar a su correo electrónico una notificación sobre el estado de su mascota. 

Después de elegir una aplicación de correos, esta se abrirá y los datos requeridos son llenados automáticamente, con el correo electrónico previamente proporcionado por el usuario y en el cuerpo del correo con el estado de su mascota. El usuario solamente deberá hacer clic en enviar y la acción estará completada.
