# processing4netbeans
netbeans plugin to support processing.org API

http://processing.org is a flexible software sketchbook and a language for learning how to code within the context of the visual arts.

As it is based on Java, it is also possible to use processing as an external library of a full Java project. 

What is the advantage of doing so? We all love playing with processing API which is simple, well designed and efficient in the audio/visual digital art domain, but sometimes, we would want to benefit from Java language and its Object Oriented approach when the project *sketches* (name of processing.org programs) start to grow. 

Netbeans (http://netbeans.org) is a great Open Source IDE, and this plugin aims at making easier the way to work with processing within your java code, allowing to: 
- create a ready-to-run java processing project from a provided project template including processing libs and main class
- add processing.org contributions libraries thanks to an included downloader/installer
- benefit from code completion, debugger, profiler and other usual tools from the netbeans IDE

Netbeans 8.2 and Netbeans 9 are supported, download the dedicated nbm file.

## Download

You can retrieve the latest compiled version of the plugin [here](https://github.com/fujeyla/processing4netbeans/tree/master/nbm "processing4netbeans nbm"). Choose either *org-netbeans-modules-java-processingproject-nb8.nbm* or *org-netbeans-modules-java-processingproject-nb9.nbm*, depending on your IDE version.

The plugin is also available on the http://plugins.netbeans.org/ website.

## What this plugin is not...

It's not a Processing language IDE support plugin: you don't code using the Processing language, you code using Java and the Processing libs. Feel comfortable: Sketches can easily be translated from Processing language to full Java.

## Supported platforms

This plugin should work on Windows, MacOS and Linux, but I've tested it only on Windows and MacOS. 

# Getting started

First create a new Netbeans project and select the Processing Category, you will find the processing java template.

![Creating a java processing project](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/newProcessingNetbeansProject.png "Creating a java processing project")

Then you need to set your project's name

![Setting up Netbeans Processing Project](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/namingNetbeansProcessingProject.png "Setting up Netbeans Processing Project")

The following java project structure is created (see project explorer on the left), including a *data* folder for usual processing assets (images, sounds,...) and a *contribs* folder ready to embed external processing.org contributions libraries. The java main class is also created. Finally, all the necessary processing core libs are already there, included as jar files in the project classpath.

You can already run the created project.

![Java Processing Project created structure](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/createdNetbeansProcessingProjectStructure.png "Java Processing Project created structure")

Then, if you want to add some processing.org contributions (*contribs*) libraries, right click on the *contribs* folder in the project explorer, and click on the action: *"Add contribs lib..."*

![Add contribs lib](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/addNetbeansProcessingContrib.png "Add contribs lib")

This opens the processing contribs dialog, letting you choose libraries you want to install and use in your project. The dialog lists all the official libraries. You can filter the libraries entries.

![Add contribs dialog](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/addNetbeansProcessingContribDialog.png "Add contriobs dialog")

For each library you want to install, click on the lower right *"Download and install"* button of the dialog; this will download the archive of the library in the *contribs* folder. Once you have downloaded all the necessary libs for your project, close the dialog by clicking on the cross at the upper right corner of it. This finalizes the libs installation, by unzipping libs archives (with all associated examples,...) in the *contribs* folder and adding the associated *.jar* files in the project classpath.

![Processing Libs added to classpath](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/addedNetbeansContribAndLibraryWithExamples.png "Processing Libs added to classpath")

You're now able to work with processing.org libs within your netbeans java project!

# Adding objects with delegated rendering

The plugin allows you to create some classes with a template sticking to the processing rendering rules, for example, if you want to create objects with their own rendering to be rendered within the main sketch... let's see how,

![Create a delegated rendering object](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/createProcessingRenderingClass.PNG "Create a delegated rendering object")

This creates a class within the main project, a class with the following template:

![Delegated rendering class](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/processingDelegateRendering.PNG "Delegated rendering class")

Such a class can then be used in you main sketch:

![Delegated rendering usage](https://github.com/fujeyla/processing4netbeans/blob/master/web/images/delegatedRenderingUsage.png "Delegated rendering usage")

For now, that's all about this processing plugin for java projects in netbeans, wishing you good processing sketches!

