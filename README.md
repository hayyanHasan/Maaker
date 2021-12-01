# Maaker
This repo contains the code of <b>Maaker</b> which is a framework for detecting and defeating evasions in Android malware.

The repo contains the following branches:

1- <b>MEGDroid+</b>: A model driven based framework for generating evasion sources, to detected evasions, and various inputs to defeat these evasions. This branch contains two projects:

&emsp;&emsp;&emsp;&emsp;- <b> android.events.DG </b>: This project is an Eclipse plugin that is used to extract various information from the  sample source code using Model Driven Reverse Engineering (MDRE). Then it generates both Signatures Model (SM) and Production Model (PM) using ATL transformations.
               
&emsp;&emsp;&emsp;&emsp;- <b> org.eclipse.acceleo.module.EventsGenerating </b>: This project is an acceleo project that is used to generate actual artifacts from the PM.
               
2- <b>Adapter</b>: This branch contains the Java project that is used to read the actual artifacts generated from MEGDroid+. Moreover, This project performs both static and dynamic analyses.

3- <b>Json files</b>: This branch contains examples of the used Json files that will be read by the Xposed modules.

4- <b>apk</b>: This branch contains three Xposed modules that are installed in the Genymotion emulator using Xposed framework. These modules are as the following:

&emsp;&emsp;&emsp;&emsp;- <b>conditions.apk</b>: That is used to extract real time values of the most used methods in the condations, such as equals and StartsWith.
               
&emsp;&emsp;&emsp;&emsp;- <b>getvalues.apk</b>: That is used to extract real time values of the methods and fields defined by MEGDroid+.
               
&emsp;&emsp;&emsp;&emsp;-<b>setvalues.apk</b>: That is used to set the returned results of the methods and fileds defined by MEGDroid+ to new values also defined by MEGDroid+. 
               
<b> Note that in order to use the code you need to update all the used paths. </b>
               
