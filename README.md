# App Tenderos

Brings small stores a bunch of services that will help them manage their business with features to control trades, payments and cellphone balance. 

## Table of contents

* [Client Details](#client-details)
* [Environment URLS](#environment-urls)
* [Da Team](#team)
* [Technology Stack](#technology-stack)
* [Management resources](#management-resources)
* [Setup the project](#setup-the-project)
* [Running the stack for development](#running-the-stack-for-development)
* [Stop the project](#stop-the-project)
* [Restoring the database](#restoring-the-database)
* [Debugging](#debugging)
* [Running specs](#running-specs)
* [Checking code for potential issues](#checking-code-for-potential-issues)


### Client Details

| Name               | Email             		| Role 					|
| ------------------ | ------------------------ | ---------------------	|
| Aldo Lares   		 | alares@bluepeople.com 	| Senior Sales Analyst 	|


### Environment URLS

* **Production** - [TBD](TBD)
* **Development** - [TBD](TBD)

### Da team

| Name          		   | Email             			| Role        |
| ------------------------ | -------------------------- | ----------- |
| Arturo Cantú Cisneros    | a0119641@gmail.com 		| Development |
| Esteban Arocha Ortuño    | estebanarocha1@gmail.com 	| Development |
| Samantha Solis Pascacio  | a01039412@itesm.mx 		| Development |
| Valentin Trujillo García | alexandro4v@gmail.com 		| Development |

### Technology Stack
| Technology     | Version      |
| -------------- | -------------|
| Android Studio | 3.6.1        |
| Kotlin	     | 1.3.0	    |
| Android	     | 5.0.0        |
| JitPack	     | 1.2.0        |
| Awesome QR     | 1.2.0        |
| Firebase Auth	 | 19.3.1		|

### Management tools

You should ask for access to this tools if you don't have it already:

* [Github repo](https://github.com/ProyectoIntegrador2018/tenderos)
* [Backlog](https://teams.microsoft.com/_#/school/tab::66092c4e-5ee6-4852-99d1-607f82abf948/Proyecto?threadId=19:242005db4c744d77bed8da3072cb3e82@thread.tacv2&ctx=channel)
* [Documentation](https://drive.google.com/drive/u/0/folders/1LIWhHVsdTVLpmetW2GNYXAYw_jIL3jgw)

## Development

### Changelog v0.4 - Closure
- Added The transaction history

### Changelog v0.3 - Cupons
- Added HU011 Receive coupon, and encrypt qr codes

### Changelog v0.2 - Authentication Update
- Added HU001 Login, Signup and Logout
- You can now create and access an accound using email and password

### Changelog v0.1 - Identification & Transactions Update 
- Added HU002 Display Identification QR functionality
- Added HU003 Confirm transactions

### Rules of Git

Using a branch for each use case and an extra branch for general development and testing the team will work on an specific part of the project. Each use case branch will be named after the use case code and the general branch will be named development. 


### Setup the project

For this project you will need to install Android studio along with the Android SDK from the API level 21, as optional downloads and for testing purposes download and install any other API level above 21 that you would require. This will be done directly from Android Studio in the Android SDK Manager, by ipressing on the SDK Manager button and ticking the required SDKs.   

After installing these please you can follow this simple steps:

1. Clone this repository into your local machine.

```bash
$ git clone https://github.com/ProyectoIntegrador2018/tenderos.git
```

2. Now you will need to setup a Virtual Device to run the application. To do this you will have to go to Devices > Open AVD Manager. 

3. Click on Create a New Virtual Device and choose a phone template from the list and click on next. 

4. Select a system image for your virtual device, click on download and then on Finish.
Be sure to use a virtual machine or device with google services (Google Play) to be able to run the app. This is very important as is a requirement for Firebase. 

You now have your setup ready to open and run the project. 


### Running the stack for Development

1. Open Android Studio and go to File > Open and find the Folder you just cloned.

2. Run the app by clicking in the play icon in Android Studio.

The project will now run in either a external device or in an emulator. 


### Stop the project

To stop the emulator you can click on the stop button on the navigation bar. 

### Debugging

To run the emulator with the debugging process you can click on the Debug App button. 
If the Debug window is not open, select View > Tool Windows > Debug. 

This will display the logs from the app. 


